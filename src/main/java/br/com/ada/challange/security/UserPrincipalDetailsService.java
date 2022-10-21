package br.com.ada.challange.security;

import br.com.ada.challange.domain.dto.UserDTO;
import br.com.ada.challange.domain.entities.UserEntity;
import br.com.ada.challange.repositories.UserRepository;
import br.com.ada.challange.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private ModelMapper mapper;

    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserEntity> user = this.userRepository.findByEmail(s);

        if (user.isEmpty())
            throw new ObjectNotFoundException("User not found");

        UserPrincipal userPrincipal = new UserPrincipal(mapper.map(user.get(), UserDTO.class));
        return userPrincipal;

    }
}
