package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable  // 내장 타입
/*
값 타입은 Immutable 해야 하므로, Setter를 제공하지 않는다.
생성할 때만 값이 설정된다. 변경 불가능
 */
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    /*
    JPA가 생성할 때 리플랙션, 프록시 기술을 사용해야 할 때가 많은데 기본 생성자가 없으면 불가능하다.
    public으로 사용하면 사람들이 사용할 가능성이 발생하므로 protected로 설정하면 JPA 스펙 상 만들어둔거구나 생각할 수 있다.
    주석을 달아놓는 것도 좋은 방법이다.
     */
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
