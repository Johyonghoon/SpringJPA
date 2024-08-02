package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // 하위 클래스들을 포함하여 하나의 테이블 형태로 생성되는 옵션
@Getter
// 추상 클래스로 생성
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    /*
    다대다 관계를 보여주기 위함. 적절한 방법은 아님
    객체는 컬렉션 관계를 양쪽에 가질 수 있지만,
    관계형 데이터베이스는 컬렉션 관계를 양쪽에 가질 수 없기 때문에 일대다, 다대일로 풀어내는 과정이 필요함
    변동사항이 발생하면 어렵기 때문에 실무에서 사용하지 않는 것
     */
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//

    /**
     * stock(재고) 증가
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;

    }

}
