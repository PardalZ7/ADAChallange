package br.com.ada.challange.domain.entities;

import br.com.ada.challange.domain.base.BaseEntity;
import br.com.ada.challange.domain.enums.RoundStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Rounds")
@Table(name = "tb_rounds")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RoundEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "matchId")
    private MatchEntity match;

    @ManyToOne
    @JoinColumn(name = "movieAId")
    private MovieEntity movieA;

    @ManyToOne
    @JoinColumn(name = "movieBId")
    private MovieEntity movieB;

    private Boolean rigthAnswer;

    @Enumerated(EnumType.STRING)
    private RoundStatus status;

    @Builder
    public RoundEntity(Long id, Boolean enable, LocalDateTime createdAt, LocalDateTime updatedAt, MatchEntity match,
                       MovieEntity movieA, MovieEntity movieB, Boolean rigthAnswer, RoundStatus status) {
        super(id, enable, createdAt, updatedAt);
        this.match = match;
        this.movieA = movieA;
        this.movieB = movieB;
        this.rigthAnswer = rigthAnswer;
        this.status = status;
    }
}
