package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {
    @PersistenceContext
    EntityManager em;

    @Test
    public void updateTest() throws Exception
    {
        //given
        Book book = em.find(Book.class, 1L);

        // TX
        book.setName("newName");

        // TX Commit -> 변경을 JPA가 스스로 감지한다
        // 변경 감지 == dirty checking : flush 할때 JPA가 알아서 업데이트를 날리게 된다
        // 이걸 통해 원하는 것으로 값을 수정할 수 있다.
        // 값을 setName으로 변경해준 뒤 별도의 작업이 필요 없다라는 뜻이다.
    }
    }
}
