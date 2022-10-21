package br.com.ada.challange.repositories;

import br.com.ada.challange.clients.ClientOMBDFeing;
import br.com.ada.challange.config.OMDBProperties;
import br.com.ada.challange.domain.dto.OMDBMovieDTO;
import br.com.ada.challange.domain.entities.MovieEntity;
import br.com.ada.challange.domain.entities.UserEntity;
import br.com.ada.challange.domain.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    @Autowired
    private ClientOMBDFeing ombdFeing;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        createUsers();
        createMovies();

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

    private void createUsers() {

        this.userRepository.deleteAll();

        UserEntity user = UserEntity.builder().name("user").email("user@email.com").pass(passwordEncoder.encode("user123"))
                .role(UserRole.USER).permissions("").enable(true).build();

        UserEntity admin = UserEntity.builder().name("admin").email("admin@email.com").pass(passwordEncoder.encode("admin123"))
                .role(UserRole.ADMIN).permissions("").enable(true).build();

        List<UserEntity> users = Arrays.asList(user, admin);
        this.userRepository.saveAll(users);

    }
}
