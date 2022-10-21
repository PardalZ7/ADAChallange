package br.com.ada.challange.domain.entities;

import br.com.ada.challange.domain.base.BaseEntity;
import br.com.ada.challange.domain.enums.UserRole;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(name = "Users")
@Table(name = "tb_users")
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String email;

    private String pass;
    private UserRole role;
    private String permissions;
    private BigDecimal rankingPoints;
    private Integer wrongAswers;
    private Integer rigthAnswers;
    private Integer quizzCount;

    @Builder

    public UserEntity(Long id, Boolean enable, LocalDateTime createdAt, LocalDateTime updatedAt, String name,
                      String email, String pass, UserRole role, String permissions, BigDecimal rankingPoints,
                      Integer wrongAswers, Integer rigthAnswers, Integer quizzCount) {
        super(id, enable, createdAt, updatedAt);
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.role = role;
        this.permissions = permissions;
        this.rankingPoints = rankingPoints != null ? rankingPoints : BigDecimal.ZERO;
        this.wrongAswers = wrongAswers != null ? wrongAswers : 0;
        this.rigthAnswers = rigthAnswers != null ? rigthAnswers : 0;
        this.quizzCount = quizzCount != null ? quizzCount : 0;
    }
}
