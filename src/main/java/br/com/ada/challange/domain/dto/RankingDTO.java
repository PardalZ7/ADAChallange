package br.com.ada.challange.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class RankingDTO {

    private String name;
    private BigDecimal score;

    @Builder
    public RankingDTO(String name, BigDecimal score) {
        this.name = name;
        this.score = score;
    }
}
