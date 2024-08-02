package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
@Repository : 스프링 빈으로 등록해 줌
 */
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /**
     * @PersistenceContext : JPA 표준으로 사용
     * 스프링이 EntityManager를 만들어서 자동으로 주입해줌
     * JPA 엔티티 매니저를 스프링의 엔티티 매니저에 주입하는 역할
     */
    // [방법 1] 표준 애노테이션
//    @PersistenceContext
//    private EntityManager em;

    // [방법 2] 스프링 부트가 Autowired도 인젝션 할 수 있게 만들어 줌
//    @Autowired
//    private EntityManager em;
//
//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

    // [방법 3] Constructor Injection 방식으로 일관성 있게 코드 작성 가능
    // MemberService 코드 참조
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    /*
    SQL : 테이블을 from의 대상으로 쿼리를 작성
    JPQL : 엔티티를 from의 대상으로 쿼리를 작성 (기본편 참고)
     */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
