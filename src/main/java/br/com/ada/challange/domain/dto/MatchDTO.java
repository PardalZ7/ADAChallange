package br.com.ada.challange.domain.dto;

import br.com.ada.challange.domain.base.BaseDTO;
import br.com.ada.challange.domain.enums.MatchStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MatchDTO extends BaseDTO {

    private Integer wrongAnswers;
    private Integer rigthAnswers;
    private String status;

    @Builder
    public MatchDTO(Long id, Boolean enable, Integer wrongAnswers, Integer rigthAnswers, String status) {
        super(id, enable);
        this.wrongAnswers = wrongAnswers;
        this.rigthAnswers = rigthAnswers;
        this.status = status;
    }
}
