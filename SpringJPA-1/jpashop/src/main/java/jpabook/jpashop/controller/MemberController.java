package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * Model 인스턴스에 View에서 Controller로 넘어갈 때 데이터를 실어 보내주는 역할
     */
    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * @Valid 애노테이션은 해당 클래스의 javax의 validation 기능(@NotEmpty 등)을 validation 해주는 역할
     * BindingResult : 오류가 담긴 채 실행할 수 있음, 아래 if 조건문을 통해 redirect하게 되고,
     * MemberForm의 @NotEmpty에 작성한 message를 화면에 표현할 수 있다. (createMemberForm.html에서 메세지를 보일 수 있게 코드 작성)
     */
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    /**
     * 현재는 요구사항이 단순하기 때문에 Member 엔티티를 그대로 반환했지만,
     * 실무에서는 DTO를 사용해서 데이터를 가공해서 전달하는 것이 적절하다.
     * 예를 들어, 엔티티가 변경되어 패스워드 등 민감한 정보가 추가될 때 이를 그대로 노출하는 문제가 발생한다.
     * 따라서, API를 사용할 때는 반드시 DTO를 사용하는 것이 적절하다.
     */
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
