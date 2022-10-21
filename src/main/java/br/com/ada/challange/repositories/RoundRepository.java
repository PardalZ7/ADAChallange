package br.com.ada.challange.repositories;

import br.com.ada.challange.domain.entities.MatchEntity;
import br.com.ada.challange.domain.entities.MovieEntity;
import br.com.ada.challange.domain.entities.RoundEntity;
import br.com.ada.challange.domain.entities.UserEntity;
import br.com.ada.challange.domain.enums.MatchStatus;
import br.com.ada.challange.domain.enums.RoundStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<RoundEntity, Long> {

    @Query(value = " SELECT r FROM Rounds r WHERE r.match = ?1 AND r.status = ?2")
    Optional<RoundEntity> findByMatch(MatchEntity match, RoundStatus status);

    @Query(value = " SELECT rd FROM Rounds rd WHERE rd.match = ?2  " +
                    " AND (rd.movieA = ?1 OR rd.movieB = ?1 ) " )
    List<RoundEntity> findPairsWith(MovieEntity movieA, MatchEntity match);

}
