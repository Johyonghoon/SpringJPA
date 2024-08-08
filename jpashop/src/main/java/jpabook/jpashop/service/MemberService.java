package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// JPA의 모든 데이터 변경 등 로직들은 트랜잭션 하에 발생해야 함
@Transactional(readOnly = true)
// @AllArgsConstructor : Lombok이 자동으로 [방법3]의 생성자를 만들어 줌
@RequiredArgsConstructor  // final에 있는 필드만을 가지고 생성자를 만들어 줌
public class MemberService {

    /**
     * @Autowired : Injection 목적
     * 하지만 이 방식을 사용하면 테스트 등의 목적에서도 변경할 수 없다는 단점
     * 따라서 setter injection 방식으로 설정해준다.
     * 이때 테스트 코드 등을 작성할 때 주입하기 편하다.
     *
     * 하지만 이 또한 단점이 존재하는데, 애플리케이션이 돌아가는 시점에 누군가가 변경할 수도 있음
     * 개발 중간에 memberRepository를 변경할 일이 없기 때문에 setter 방식 또한 사용하지 않음
     *
     * 따라서, 요즘 권장하는 방식은 생성자 인젝션 방식을 사용
     * 생성 단계에서 레포지토리를 설정하기 때문에 변경될 가능성이 존재하지 않음
     * 또한, 테스트 작성 시 어떤 레포지토리를 주입해야 하는지 놓치지 않고 명확히 확인할 수 있음
     */
    // [방법1] direct injection
//    @Autowired
    // 변경할 일이 없기 때문에 final로 설정,
    // 생성자를 만들었을 때 값을 세팅해주지 않으면 에러를 발생해서 컴파일 시점에서 놓치지 않을 수 있음
    private final MemberRepository memberRepository;

    // [방법2] setter injection
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // [방법3] Constructor injection
    // 최신 버전 스프링에서는 생성자가 하나만 있는 경우 @Autowired 애노테이션이 없어도 자동으로 주입해 줌
    // @RequiredArgsConstructor에 의해 자동으로 생성 됨
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }


    /**
     * 회원 가입
     * @Transactional(readOnly = true) 사용 시 데이터 변경이 되지 않음
     */
    @Transactional  // 하위에 트랜잭션 애노테이션을 작성하면 readOnly = false 옵션으로 설정된다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 발생 시 예외 발생
     * WAS에 동시에 여러 개가 뜨게 된다면 (똑같은 이름이 동시에 DB Insert를 하게 되면) 동시 호출이 될 수 있음
     * 비즈니스 로직이 이렇게 되어 있다고 하더라도, 멀티 스레드를 고려하여 member 이름을 유니크 제약조건을 걸어주는 것을 권장
     */
    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     * @Transactional(readOnly = true) : JPA가 조회하는 곳에서 최적화
     * 상위 클래스에 애노테이션이 존재하면 명시할 필요 없이 하위 메서드에도 해당 트랜잭션이 적용된다.
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 변경 감지 사용
     */
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
