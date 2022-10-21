package br.com.ada.challange.utils;

import br.com.ada.challange.config.JwtProperties;
import br.com.ada.challange.domain.dto.UserDTO;
import br.com.ada.challange.domain.entities.UserEntity;
import br.com.ada.challange.repositories.UserRepository;
import br.com.ada.challange.services.exceptions.ObjectNotFoundException;
import com.auth0.jwt.JWT;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
@Scope("singleton")
public class SecurityUtils {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    private String getEmailFromToken(String token) {
        return JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }

    public Optional<UserDTO> getUserFromToken(String token) {

        String email = getEmailFromToken(this.getTokenValue(token));

        if (email == null)
            return Optional.empty();

        Optional<UserEntity> user = repository.findByEmail(email);

        if (user.isEmpty())
            throw new ObjectNotFoundException("User not found");

        return Optional.of(mapper.map(user.get(), UserDTO.class));

    }

    public String getTokenValue(String token) {

        if (token.startsWith(JwtProperties.TOKEN_PREFIX))
            token = token.replace(JwtProperties.TOKEN_PREFIX, "");

        return token;

    }

}
