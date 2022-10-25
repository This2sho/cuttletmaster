package PorkCutlet.master.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PorkCutlet.master.domain.User;
import PorkCutlet.master.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;

	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
	}

	@Transactional
	public Long join(User user) {
		validateDuplicateLoginId(user); // 중복 회원 거르기
		validateDuplicateNickName(user);
		userRepository.save(user);
		return user.getId();
	}

	@Transactional
	public void updateUser(User user, String password, String nickName) {
		validateDuplicateNickName(nickName);
		user.update(password, nickName);
	}

	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Transactional
	public void appointUser(Long masterApplyId) {
		User user = userRepository.findByMasterApplyId(masterApplyId).orElseThrow();
		user.setAdmin();
	}

	private void validateDuplicateLoginId(User user) {
		if (userRepository.findByLoginId(user.getLoginId()).isPresent()) {
			throw new IllegalStateException("이미 존재하는 아이디 입니다.");
		}
	}

	private void validateDuplicateNickName(User user) {
		if (userRepository.findByNickName(user.getNickName()).isPresent()) {
			throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
		}
	}

	private void validateDuplicateNickName(String nickName) {
		if (userRepository.findByNickName(nickName).isPresent()) {
			throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
		}
	}

	/**
	 * 로그인
	 *
	 * @param loginId
	 * @param password
	 * @return 성공 -> User , 실패 -> null
	 */
	public User login(String loginId, String password) {
		return userRepository.findByLoginId(loginId)
			.filter(user -> user.getPassword().equals(password))
			.orElse(null);
	}
}
