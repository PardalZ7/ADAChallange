package br.com.ada.challange.services.impl;

import br.com.ada.challange.domain.dto.UserDTO;
import br.com.ada.challange.domain.entities.UserEntity;
import br.com.ada.challange.repositories.UserRepository;
import br.com.ada.challange.services.exceptions.DataIntegrityViolationException;
import br.com.ada.challange.services.exceptions.ObjectNotFoundException;
import br.com.ada.challange.services.interfaces.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ModelMapper skipNullMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO create(UserDTO userDTO) {

        if (userDTO.getEnable() == null)
            userDTO.setEnable(true);
        userDTO.setPass(passwordEncoder.encode(userDTO.getPass()));

        if (!checkEmail(userDTO.getId(), userDTO.getEmail()))
            throw new DataIntegrityViolationException("Email already registered");
        return this.mapper.map(this.repository.save(this.mapper.map(userDTO, UserEntity.class)), UserDTO.class);
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<UserEntity> user = repository.findById(id);
        if (!user.isPresent())
            throw new ObjectNotFoundException("User not found");
        return this.mapper.map(user.get(), UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(UserDTO userDTO) {
        Optional<UserEntity> user = this.repository.findByEmail(userDTO.getEmail());
        return this.mapper.map(user.get(), UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll(Pageable pageable, Boolean showAll) {
        if (showAll)
            return this.repository.findAll(pageable).stream().map(x -> mapper.map(x, UserDTO.class)).toList();
        else
            return this.repository.findAllEnable(pageable).stream().map(x -> mapper.map(x, UserDTO.class)).toList();
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        UserDTO userOnDB = this.findById(userDTO.getId());
        if (!checkEmail(userDTO.getId(), userDTO.getEmail()))
            throw new DataIntegrityViolationException("Email already registered");

        if (userDTO.getPass() != null)
            userDTO.setPass(passwordEncoder.encode(userDTO.getPass()));
        this.skipNullMapper.map(userDTO, userOnDB);

        return this.mapper.map(this.repository.save(this.mapper.map(userOnDB, UserEntity.class)),
                UserDTO.class);

    }

    @Override
    public void deleteById(Long id) {
        UserDTO user = this.findById(id);
        user.setEnable(false);
        this.repository.save(this.mapper.map(user, UserEntity.class));
    }

    public boolean checkEmail(Long userId, String email) {
        Optional<UserEntity> user = this.repository.findByEmail(email);
        if (user.isPresent() && !user.get().getId().equals(userId))
            return false;
        return true;
    }

}
