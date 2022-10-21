package br.com.ada.challange.domain.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LoginDTO {

    private String email;
    private String password;

}
