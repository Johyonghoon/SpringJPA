package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 총 주문 2개
 * userA
 * - JPA1 BOOK
 * - JPA2 BOOK 
 * userB
 * - SPRING1 BOOK 
 * - SPRING2 BOOK
 */

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    
    @PostConstruct
    public void init() {
        // @PostConstruct 애노테이션을 사용하면 @Transactional이 제대로 동작하지 않기 때문에 별도로 생성하는 것이 좋음
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        
        private final EntityManager em;
        public void dbInit1() {
            Member member = createMember("userA", new Address("서울", "1", "1111"));
            em.persist(member);

            Book book1 = createBook("JPA1 Book", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 Book", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB", new Address("진주", "2", "2222"));
            em.persist(member);

            Book book1 = createBook("SPRING1 Book", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 Book", 40000, 400);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        // option + command + M : 해당 영역 메서드 생성
        // option + command + P : 해당 setter 파라미터 생성
        private static Member createMember(String name, Address address) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(address);
            return member;
        }

        private static Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

    }
    
    
}

