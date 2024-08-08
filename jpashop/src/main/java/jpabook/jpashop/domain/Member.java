package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded  // 내장 타입
    private Address address;

    @OneToMany(mappedBy = "member")  // Orders 테이블의 member 필드에 의해 매핑된거야
    private List<Order> orders = new ArrayList<>();
}
