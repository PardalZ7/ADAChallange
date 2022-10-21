package br.com.ada.challange.domain.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class NextRoundResponseDTO {

    private String movieA;
    private String movieB;

    @Builder
    public NextRoundResponseDTO(String movieA, String movieB) {
        this.movieA = movieA;
        this.movieB = movieB;
    }
}
