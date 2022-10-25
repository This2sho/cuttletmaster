package PorkCutlet.master.repository;

import static org.assertj.core.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import PorkCutlet.master.domain.User;

@SpringBootTest
@Transactional
@Rollback
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	@Rollback(value = false)
	public void UserCreateTest() {
		//given
		User user = new User("A", "123", "A");
		userRepository.save(user);
		//when
		User findUser = userRepository.findById(user.getId()).get();
		//then
		assertThat(user.getId()).isEqualTo(findUser.getId());
		assertThat(user.getLoginId()).isEqualTo(findUser.getLoginId());
		assertThat(user).isEqualTo(findUser);
	}

}
