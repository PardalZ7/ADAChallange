package br.com.ada.challange.domain.entities;

import br.com.ada.challange.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Movie")
@Table(name = "tb_movies")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
public class MovieEntity extends BaseEntity {

    private String name;
    private BigDecimal score;

    @Builder
    public MovieEntity(Long id, Boolean enable, LocalDateTime createdAt, LocalDateTime updatedAt, String name,
                       BigDecimal score) {
        super(id, enable, createdAt, updatedAt);
        this.name = name;
        this.score = score == null ? BigDecimal.ZERO : score;
    }

}
