package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

// Spring과 Integration 해서 테스트
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
    // Spring Transaction은 기본적으로 트랜잭션이 발생한 이후 커밋을 하는 것이 아닌 롤백을 한다. 따라서 insert 문이 나가지 않음
    // Rollback false 설정 하면 커밋을 하기 때문에 쿼리 인서트 문을 볼 수 있음
    // Rollback도 보고 디비에 쿼리 날리는것도 보고싶다면, em.flush()를 실행한다

    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("Kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));
     }

     @Test(expected = IllegalStateException.class)
     public void 중복_회원_예외() throws Exception {
         //given
         Member member1 = new Member();
         member1.setName("kim");

         Member member2 = new Member();
         member2.setName("kim");

         //when
         memberService.join(member1);
         memberService.join(member2);

//         try {
//             memberService.join(member2);
//         } catch(IllegalStateException e) {
//             return;
//         }

         //then
         fail("예외가 발생!");
      }



}