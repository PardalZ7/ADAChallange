package br.com.ada.challange.repositories;

import br.com.ada.challange.domain.entities.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query(value = " SELECT m FROM Movie m WHERE m.enable = true ")
    Page<MovieEntity> findAllEnable(Pageable pageable);

    @Query(value = " SELECT m FROM Movie m ORDER BY RAND() ")
    List<MovieEntity> findRandomMovie();

}
