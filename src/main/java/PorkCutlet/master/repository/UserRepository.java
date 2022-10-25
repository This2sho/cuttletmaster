package PorkCutlet.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import PorkCutlet.master.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByLoginId(String loginId);

	Optional<User> findByNickName(String nickName);

	@EntityGraph(attributePaths = {"masterApply"})
	Optional<User> findByMasterApplyId(Long masterApplyId);
}
