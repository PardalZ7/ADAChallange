package br.com.ada.challange.repositories;

import br.com.ada.challange.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query(value = " SELECT u FROM Users u WHERE u.enable=true ")
    Page<UserEntity> findAllEnable(Pageable pageable);

    @Query(value = " SELECT u FROM Users u WHERE u.enable=true ORDER BY u.rankingPoints DESC ")
    List<UserEntity> getRanking();

}
