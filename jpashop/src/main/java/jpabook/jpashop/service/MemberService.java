package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  //클래스에 달아준 애노테이션이 모든 퍼블릭 메소드에 적용이 되는데, 개별적으로 달린 조건들은 개별적으로 달린 애노테이션 우선 적용!
@RequiredArgsConstructor
public class MemberService {

//    @Autowired  //Field Injection --> 바꾸고 싶어도 못바꾸는것이 단점
    private final MemberRepository memberRepository;

    // 생성자 주입: 애플리케이션 로딩 시점에 원하는 객체를 꽂아줌
    // Setter 주입처럼 원하는 객체를 꽂아줄 수 있는 동시에 아무때나 주입 대상이 바뀔 수 있다는 리스크를 줄일 수 있어서 권장됨
    // 생성자가 하나인 경우엔 Autowired가 없어도 Spring이 알아서 주입을 시켜줌
    // memberRepository에 final을 적용시켜서 compile 시점에 주입 대상을 잘 명시했는지 확인한다.
    // 아래와 같은 생성자를 아예 쓰지 않고, Lombok 의 RequiredArgsConstructor를 사용하면 알아서 final 이 붙은 변수에 주입을 한다.

    /**
     @Autowired
     public MemberService(MemberRepository memberRepository) {
     this.memberRepository = memberRepository;
     }
     */


    /**
     *회원가입
     */
    @Transactional  //직접 달아준 애노테이션이 우선적으로 적용되어 readOnly 가 적용되지 않는다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 회원 전체 조회
     */
//    @Transactional(readOnly = true)  // 읽기 전용 트랜잭션에 해당 애노테이션을 달아주면 조금 더 최적화한다
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
//    @Transactional(readOnly = true)  // 읽기 전용 트랜잭션에 해당 애노테이션을 달아주면 조금 더 최적화한다
    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 중복 아이디가 생성되지 않도록 다음과 같은 검증 메소드를 생성하였다.
     * 하지만 멀티스레드 환경에서 다음과 같은 검증 메소드가 있어도 두 사용자가 동시에 동일한 아이디를 생성하면
     * 의도한대로 검증이 되지 않을수 있다.
     * 따라서 이러한 상황을 방지할수있도록 도메인 생성 시 멤버의 이름에 Unique 제약조건을 부여하는것을 권장한다.
     */
    private void validateDuplicateMember(Member member) {
        //EXCEPTION IF DUPLICATE
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
