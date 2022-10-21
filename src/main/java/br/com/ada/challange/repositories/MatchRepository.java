package br.com.ada.challange.repositories;

import br.com.ada.challange.domain.dto.MatchDTO;
import br.com.ada.challange.domain.entities.MatchEntity;
import br.com.ada.challange.domain.entities.RoundEntity;
import br.com.ada.challange.domain.entities.UserEntity;
import br.com.ada.challange.domain.enums.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

    @Query(value = " SELECT m FROM Matches m WHERE m.user = ?1 AND m.status = ?2")
    Optional<MatchEntity> findByUserAndStatus(UserEntity user, MatchStatus status);

    @Query(value = " SELECT m FROM Matches m WHERE m.user = ?1 ")
    List<MatchEntity> findByUser(UserEntity user);

}
