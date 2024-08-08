package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    /*
    다대다 관계를 보여주기 위함. 적절한 방법은 아님
    객체는 컬렉션 관계를 양쪽에 가질 수 있지만,
    관계형 데이터베이스는 컬렉션 관계를 양쪽에 가질 수 없기 때문에 일대다, 다대일로 풀어내는 과정이 필요함
    변동사항이 발생하면 어렵기 때문에 실무에서 사용하지 않는 것
     */
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    /*
    나에 대한 연관관계는 다른 연관관계 매핑과 같이 설정하고, 필드 값만 제대로 설정하면 된다.
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 편의 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

}
