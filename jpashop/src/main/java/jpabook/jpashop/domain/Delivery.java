package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    /*
    EnumType.ORDINAL : 숫자로 들어가는데, 중간에 다른 상태가 들어가게 되면 숫자가 밀리게 되므로 절대 사용하지 말기
    EnumType.STRING : 문자열로 들어감
     */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;  // READY, COMP

}
