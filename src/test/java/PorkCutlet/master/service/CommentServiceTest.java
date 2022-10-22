package PorkCutlet.master.service;

import PorkCutlet.master.domain.Comment;
import com.mysema.commons.lang.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @Test
    public void 마지막_페이지_댓글_가져오기() throws Exception {
        //given
        for (int i = 0; i < 6; i++) {
            commentService.createComment(1L, 6L, "i = " + i);
        }
        //when
        Pair<Integer, List<Comment>> lastComments = commentService.getLastCommentsWithTotalPage(6L);
        //then
        for (Comment comment : lastComments.getSecond()) {
            System.out.println(comment.getContent());
        }
    }

}