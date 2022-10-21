package br.com.ada.challange.services.impl;

import br.com.ada.challange.clients.ClientOMBDFeing;
import br.com.ada.challange.config.OMDBProperties;
import br.com.ada.challange.domain.dto.*;
import br.com.ada.challange.domain.entities.MatchEntity;
import br.com.ada.challange.domain.entities.MovieEntity;
import br.com.ada.challange.domain.entities.RoundEntity;
import br.com.ada.challange.domain.entities.UserEntity;
import br.com.ada.challange.domain.enums.MatchStatus;
import br.com.ada.challange.domain.enums.QuizResponse;
import br.com.ada.challange.domain.enums.RoundStatus;
import br.com.ada.challange.repositories.MatchRepository;
import br.com.ada.challange.repositories.MovieRepository;
import br.com.ada.challange.repositories.RoundRepository;
import br.com.ada.challange.repositories.UserRepository;
import br.com.ada.challange.services.exceptions.DataIntegrityViolationException;
import br.com.ada.challange.services.exceptions.NotEnougthMoviesException;
import br.com.ada.challange.services.interfaces.MatchServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchServiceInterface {

    private static final Integer MAX_WRONG_ANSWERS = 3;

    @Autowired
    private ClientOMBDFeing ombdFeing;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public NextRoundResponseDTO next(UserDTO user) {

        Optional<MatchEntity> optMatch = matchRepository.findByUserAndStatus(mapper.map(user, UserEntity.class),
                MatchStatus.OPEN);
        if (optMatch.isPresent()) {

            Optional<RoundEntity> optRound = roundRepository.findByMatch(optMatch.get(), RoundStatus.OPEN);
            if (optRound.isPresent()) {
                return NextRoundResponseDTO.builder().movieA(optRound.get().getMovieA().getName())
                        .movieB(optRound.get().getMovieB().getName())
                        .build();
            }

        }

        RoundEntity round = this.createRound(optMatch.isPresent() ? optMatch.get() : this.createMatch(user));
        return NextRoundResponseDTO.builder().movieA(round.getMovieA().getName()).movieB(round.getMovieB().getName())
                .build();

    }

    @Override
    public MatchDTO answer(UserDTO userDto, QuizResponse response) {

        UserEntity user = userRepository.getById(userDto.getId());
        if (user == null)
            throw new DataIntegrityViolationException("USER not found");

        Optional<MatchEntity> optMatch = matchRepository.findByUserAndStatus(user, MatchStatus.OPEN);
        if (optMatch.isEmpty() || optMatch.get().getStatus().equals(MatchStatus.CLOSED))
            throw new DataIntegrityViolationException("MATCH not found or closed");
        MatchEntity match = optMatch.get();

        Optional<RoundEntity> optRound = roundRepository.findByMatch(match, RoundStatus.OPEN);
        if (optRound.isEmpty() || optRound.get().getStatus().equals(RoundStatus.CLOSED))
            throw new DataIntegrityViolationException("ROUND not found or closed");
        RoundEntity round = optRound.get();

        boolean rigthAnswer =
                (response.equals(QuizResponse.A) && (round.getMovieA().getScore().compareTo(round.getMovieB().getScore()) >= 0)) ||
                (response.equals(QuizResponse.B) && (round.getMovieB().getScore().compareTo(round.getMovieA().getScore()) >= 0));

        round.setRigthAnswer(rigthAnswer);
        round.setStatus(RoundStatus.CLOSED);
        roundRepository.save(round);

        if (rigthAnswer) {
            match.setRigthAnswers(match.getRigthAnswers() + 1);
            user.setRigthAnswers(user.getRigthAnswers() + 1);
        } else {
            match.setWrongAnswers(match.getWrongAnswers() + 1);
            user.setWrongAswers(user.getWrongAswers() + 1);
        }
        match.setStatus(match.getWrongAnswers() >= MAX_WRONG_ANSWERS ? MatchStatus.CLOSED : MatchStatus.OPEN);

        if (match.getStatus().equals(MatchStatus.CLOSED)) {
            user.setQuizzCount(user.getQuizzCount() + 1);

            double score = (double)user.getRigthAnswers() / (double)(user.getRigthAnswers() + user.getWrongAswers());
            user.setRankingPoints(new BigDecimal(score * user.getQuizzCount()));
        }

        userRepository.save(user);
        return mapper.map(matchRepository.save(match), MatchDTO.class);

    }

    @Override
    public List<RankingDTO> ranking() {

        return userRepository.getRanking().stream()
                .map(usr -> RankingDTO.builder().name(usr.getName()).score(usr.getRankingPoints()).build())
                .toList();

    }

    @Override
    public List<MatchDTO> matchesByUser(Long userId) {

        Optional<UserEntity> userDTO = userRepository.findById(userId);

        if (userDTO.isEmpty())
            throw new DataIntegrityViolationException("USER not found");

        return matchRepository.findByUser(mapper.map(mapper.map(userDTO.get(), UserDTO.class), UserEntity.class)).stream()
                .map(m -> mapper.map(m, MatchDTO.class)).toList();

    }

    private MatchEntity createMatch(UserDTO user) {

        MatchEntity match = MatchEntity.builder().enable(true).rigthAnswers(0).wrongAnswers(0).status(MatchStatus.OPEN)
                .user(mapper.map(user, UserEntity.class)).build();
        return matchRepository.save(match);

    }

    private RoundEntity createRound(MatchEntity match) {

        RoundEntity round = tryToGetMovies(match);
        while (round == null) {
            createMovies();
            round = tryToGetMovies(match);
        }

        return roundRepository.save(round);

    }

    private RoundEntity tryToGetMovies(MatchEntity match) {

        MovieEntity movieA = null;
        MovieEntity movieB = null;

        List<MovieEntity> allMovies = movieRepository.findRandomMovie();
        int index = 0;

        while ((movieA == null || movieB == null) && (index < allMovies.size())) {

            movieA = allMovies.get(index++);
            long movieAId = movieA.getId();

            Set<Long> rounds = roundRepository.findPairsWith(movieA, match).stream()
                    .map(rd -> rd.getMovieA().getId().equals(movieAId) ? rd.getMovieB().getId() : rd.getMovieA().getId())
                    .collect(Collectors.toSet());


            for (MovieEntity mv : allMovies) {

                if (mv.getId().equals(movieAId) || rounds.contains(mv.getId()))
                    continue;

                movieB = mv;
                break;

            }

        }

        if (movieB == null)
            return null;

        //// TODO: 21/10/2022 Para facilitar os testes a resposta A é sempre a correta.
        RoundEntity round = null;
        if (movieA.getScore().compareTo(movieB.getScore()) >= 0)
            round = RoundEntity.builder().enable(true).match(match).movieA(movieA)
                    .movieB(movieB).rigthAnswer(null).status(RoundStatus.OPEN).build();
        else
            round = RoundEntity.builder().enable(true).match(match).movieA(movieB)
                    .movieB(movieA).rigthAnswer(null).status(RoundStatus.OPEN).build();

        //// TODO: 21/10/2022 Deveria ser dessa maneira
//        RoundEntity round = RoundEntity.builder().enable(true).match(match).movieA(movieA)
//                .movieB(movieB).rigthAnswer(null).status(RoundStatus.OPEN).build();

        return round;

    }

    private void createMovies() {

        String filePath = "src/main/resources/movies.txt";
        try {

            List<String> movies = Files.lines(Path.of(filePath)).map(line -> line.split(",")[5]).toList();
            HashSet<String> addMovies = new HashSet<>();
            int errors = 0;

            while (addMovies.size() < (OMDBProperties.MAX_REQUESTS + errors)) {

                String movieName = movies.get((int) (Math.random() * (movies.size() - 1)));
                while (addMovies.contains(movieName))
                    movieName = movies.get((int) (Math.random() * (movies.size() - 1)));

                try {

                    OMDBMovieDTO omdbMovie = ombdFeing.getFilm(movieName, OMDBProperties.KEY);

                    if ((omdbMovie.getImdbRating() == null) || (omdbMovie.getImdbVotes() == null))
                        throw new Exception("");

                    movieRepository.save(MovieEntity.builder().name(movieName)
                            .score(omdbMovie.getImdbRating().multiply(omdbMovie.getImdbVotesAsBigDecimal()))
                            .enable(true).build());
                    System.out.println(omdbMovie);



                } catch (Exception e) {
                    errors++;
                    System.out.println("Error on get movie info: " + movieName);
                    System.out.println(e.getMessage());
                } finally {
                    addMovies.add(movieName);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
