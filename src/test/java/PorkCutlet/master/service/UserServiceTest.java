package PorkCutlet.master.service;

import PorkCutlet.master.domain.User;
import PorkCutlet.master.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입_성공_테스트(){
        //given
        User user = new User("hello", "password", "Im Nick");
        Long joinId = userService.join(user);
        //when
        User find = userRepository.findById(joinId).orElseThrow();
        //then
        assertThat(user).isEqualTo(find);
    }

    @Test
    public void 회원가입_실패_테스트() {
        //given
        User userA = new User("userA", "password", "Im Nick");
        userService.join(userA);
        //when
        User userB = new User("userA", "password234", "Im Bob");
        // 여기서 IllegalStateException 발생해야함.
        assertThrows(IllegalStateException.class, () -> userService.join(userB));
    }

    @Test
    public void 로그인_성공_테스트() throws Exception {
        //given
        User user = new User("hello", "password", "Im Nick");
        userService.join(user);
        //when
        User login = userService.login(user.getLoginId(), user.getPassword());
        //then
        assertThat(user).isEqualTo(login);
    }

    @Test
    public void 로그인_실패_테스트() throws Exception {
        //given
        User user = new User("hello", "password", "Im Nick");
        userService.join(user);
        //when
        User wrongId = userService.login("이런 아이디는 없습니다.", "이런 비밀번호는 없습니다.");
        User wrongPassword = userService.login(user.getLoginId(), "이런 비밀번호는 없습니다.");
        //then
        assertThat(wrongId).isNull();
        assertThat(wrongPassword).isNull();
    }
}