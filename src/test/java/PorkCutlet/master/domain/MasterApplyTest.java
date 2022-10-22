package PorkCutlet.master.domain;

import PorkCutlet.master.repository.MasterApplyRepository;
import PorkCutlet.master.repository.UserRepository;
import PorkCutlet.master.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MasterApplyTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    MasterApplyRepository masterApplyRepository;

    @Test
    public void 유저_삭제시_미식가신청도_삭제_테스트() throws Exception {
        //given
        List<User> all = userRepository.findAll();
        User user = all.get(0);
        MasterApply masterApply = new MasterApply(user, "정말 돈가스 미식가가 되고 싶습니다.");
        masterApplyRepository.save(masterApply);

        //when
        userService.deleteUser(user);
        Optional<MasterApply> findApply = masterApplyRepository.findById(masterApply.getId());

        //then
        assertThat(findApply).isEmpty();
    }

}