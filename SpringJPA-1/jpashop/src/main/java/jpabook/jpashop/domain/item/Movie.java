package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M")  // DB에 저장할 때 구분할 수 있는 값
@Getter @Setter
public class Movie extends Item {

    private String director;
    private String actor;

}
