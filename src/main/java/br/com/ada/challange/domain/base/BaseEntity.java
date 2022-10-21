package br.com.ada.challange.domain.base;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EqualsAndHashCode
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "enable", columnDefinition = "boolean default true", nullable = false)
    protected Boolean enable = true;

    @Column(name = "createdAt", updatable = false, nullable = false)
    @CreationTimestamp
    protected LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    @UpdateTimestamp
    protected LocalDateTime updatedAt;

}

