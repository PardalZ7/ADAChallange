package br.com.ada.challange.domain.dto;

import br.com.ada.challange.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO extends BaseDTO {

    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pass;
    private String role;
    private String permissions;
    private Long rankingPoints;

    @Builder
    public UserDTO(Long id, Boolean enable, String name, String email, String pass, String role,
                   String permissions, Long rankingPoints) {
        super(id, enable);
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.role = role;
        this.permissions = permissions;
        this.rankingPoints = rankingPoints;
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

}
