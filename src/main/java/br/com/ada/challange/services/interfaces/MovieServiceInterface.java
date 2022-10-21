package br.com.ada.challange.services.interfaces;

import br.com.ada.challange.domain.dto.MovieDTO;
import br.com.ada.challange.domain.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieServiceInterface {

    MovieDTO findById(Long id);
    List<MovieDTO> findAll(Pageable pageable, Boolean showAll);
    MovieDTO create(MovieDTO userDto);
    MovieDTO update(MovieDTO userDto);
    void deleteById(Long id);

}
