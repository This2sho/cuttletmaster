package PorkCutlet.master.service;

import PorkCutlet.master.domain.User;
import PorkCutlet.master.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user); // 중복 회원 거르기
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        if(userRepository.findByLoginId(user.getLoginId()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }


    /**
     * 로그인
     * @param loginId
     * @param password
     * @return 성공 -> User , 실패 -> IllegalStateException
     */
    public User login(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId).orElse(null);
        if(user == null) throw new IllegalStateException("아이디가 존재하지 않습니다.");
        if(!user.getPassword().equals(password)) throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        return user;
    }
}
