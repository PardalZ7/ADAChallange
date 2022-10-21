package br.com.ada.challange.domain.dto;

import br.com.ada.challange.domain.base.BaseDTO;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MovieDTO extends BaseDTO {

    private String name;
    private BigDecimal score;

    @Builder
    public MovieDTO(Long id, Boolean enable, String name, BigDecimal score) {
        super(id, enable);
        this.name = name;
        this.score = score;
    }
}
