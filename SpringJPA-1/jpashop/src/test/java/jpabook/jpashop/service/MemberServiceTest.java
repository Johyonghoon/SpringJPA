package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * 스프링과 통합하여 테스트하기 위해
 * @RunWith(SpringRunner.class) @SpringBootTest
 * 두 개의 애노테이션을 사용
 */
@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * JPA 같은 트랜잭션 내에서 PK 값이 같다면, 같은 영속성 컨텍스트로 관리된다.
 * 또한 테스트에서 트랜잭션은 커밋을 하지 않고, 롤백을 하지 않기 때문에 SQL insert문이 실행되지 않는다.
 */
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Rollback(false)  // 눈으로 확인하고 싶다면 롤백하지 않고 데이터베이스에서 확인한다.
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    // (expected = IllegalStateException.class) : try-catch 구문 없이 테스트 케이스를 작성할 수 있다.
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2);  // 예외 발생해야 한다!!

        // then
        fail("예외가 발생해야 한다.");
    }

}