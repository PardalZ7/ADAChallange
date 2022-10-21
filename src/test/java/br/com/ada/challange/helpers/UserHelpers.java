package br.com.ada.challange.helpers;

import br.com.ada.challange.domain.dto.UserDTO;

public class UserHelpers {

    public static UserDTO getUserAdmin01() {

        return UserDTO.builder().id(1L).name("ADMIN 01").email("ADMIN 01").pass("PASS 01")
                .permissions("").enable(true).role("ADMIN").rankingPoints(1000L).build();

    }

    public static UserDTO getUser01() {

        return UserDTO.builder().id(2L).name("USER 02").email("USER 02").pass("PASS 02")
                .permissions("").enable(true).role("USER").rankingPoints(200L).build();

    }

}
