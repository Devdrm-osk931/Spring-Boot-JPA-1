package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    // 해당 애노테이션이 있으면 JPA EntityManager를 주입받는다, Spring Data JPA 가 @PersistenceContext 대신 Autowired를 달아도 주입을 받을 수 있게끔 해줌
    // 그 말은 즉, 다음과 같이 코드를 작성할 수 있다는 말
    /**
     * MemberRepository class에 @RequiredArgsConstructor 를 추가한 뒤, EntityManager를 생성자 주입을 통해 주입받는다
     * private final EntityManager em; 이렇게 하면 됨!
     */
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        //JPQL --> first param: Query  second param: return class type
        // Entity 객체에 대한 쿼리로 SQL과 문법이 조금 상이함!
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
