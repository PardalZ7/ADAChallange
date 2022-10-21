package br.com.ada.challange.domain.base;

import lombok.*;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EqualsAndHashCode
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTO {

    protected Long id;
    protected Boolean enable;

}
