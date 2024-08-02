package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    /*
    @XToOne 관계는 기본 설정이 즉시로딩(EAGER)이므로 직접 지연로딩으로 설정해야 한다.
    이 작업을 하지 않으면, N+1 문제가 발생하게 된다.
     */
    @ManyToOne(fetch = LAZY)
    /*
    양방향 연관관계는 연관관계의 주인을 정해야 한다.
    Member도 orders를 List 필드로 가지고 있고, Order도 member 필드를 가지고 있다.
    값이 변경되었을 때 FK를 변경하게 되는 것을 연관관계의 주인으로 설정하면 된다.
    여기서는 Member가 변경되었을 때 Order를 변경하는 것이
    Order를 변경했을 때 Member를 변경해야하는 것보다 쉬우므로 Order가 연관관계의 주인이 된다.
     */
    @JoinColumn(name = "member_id")
    private Member member;

    /*
    cascade 옵션
    예를 들어 orderItemA, orderItemB, orderItemC가 있을 때 persist 옵션을 직접 하지 않아도 자동으로 된다.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /*
    일대일 관계에서 FK를 두는 방법 (김영한)
    접근을 자주 하는 곳에 FK를 두는 것을 선호한다.
    Delivery를 가지고 Order를 찾는 것보다, Order를 통해 Delivery를 찾는 경우가 많으므로,
    Orders 테이블에 FK를 두는 것을 선호한다.
     */
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;  // java8이므로 LocalDateTime 사용 (하이버네이트가 자동 지원)

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // 주문상태 [ORDER, CANCEL]

    //==연관관계 편의 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    /*
    아래와 같은 과정을 생략하고, 편리하게 연관관계를 매핑해주는 것
    앙방향 코드를 원자적으로 해결해주는 코드
    public static void main(String[] args) {
        Member member = new Member();
        Order order = new Order();

        member.getOrders().add(order);
        order.setMember(member);
    }
    */

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
