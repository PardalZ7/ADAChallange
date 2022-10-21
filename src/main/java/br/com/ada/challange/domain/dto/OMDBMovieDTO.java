package br.com.ada.challange.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OMDBMovieDTO {

    private String title;
    private BigDecimal imdbRating;
    private String imdbVotes;

    @Builder

    public OMDBMovieDTO(String title, BigDecimal imdbRating, String imdbVotes) {
        this.title = title;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
    }

    public BigDecimal getImdbVotesAsBigDecimal() {

        String value = imdbVotes.replace(",", "").replace(".", "");
        return new BigDecimal(value);

    }
}
