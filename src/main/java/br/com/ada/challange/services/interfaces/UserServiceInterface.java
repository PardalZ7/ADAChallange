package br.com.ada.challange.services.interfaces;

import br.com.ada.challange.domain.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserServiceInterface {

    UserDTO findById(Long id);
    UserDTO findByEmail(UserDTO userDTO);
    List<UserDTO> findAll(Pageable pageable, Boolean showAll);
    UserDTO create(UserDTO userDto);
    UserDTO update(UserDTO userDto);
    void deleteById(Long id);

}
