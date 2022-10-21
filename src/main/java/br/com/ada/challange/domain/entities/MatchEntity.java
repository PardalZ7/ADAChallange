package br.com.ada.challange.domain.entities;

import br.com.ada.challange.domain.base.BaseEntity;
import br.com.ada.challange.domain.enums.MatchStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "Matches")
@Table(name = "tb_matches")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
public class MatchEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    private Integer wrongAnswers;
    private Integer rigthAnswers;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Builder
    public MatchEntity(Long id, Boolean enable, LocalDateTime createdAt, LocalDateTime updatedAt, UserEntity user,
                       Integer wrongAnswers, Integer rigthAnswers, MatchStatus status) {
        super(id, enable, createdAt, updatedAt);
        this.user = user;
        this.wrongAnswers = wrongAnswers == null ? 0 : wrongAnswers;
        this.rigthAnswers = rigthAnswers == null ? 0 : rigthAnswers;
        this.status = status;
    }
}
