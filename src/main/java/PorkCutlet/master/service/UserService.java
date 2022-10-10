package PorkCutlet.master.service;

import PorkCutlet.master.domain.User;
import PorkCutlet.master.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        validateDuplicateUser(user); // 중복 회원 거르기
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        if (userRepository.findByLoginId(user.getLoginId()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
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
