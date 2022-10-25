package PorkCutlet.master.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PorkCutlet.master.domain.MasterApply;
import PorkCutlet.master.domain.User;
import PorkCutlet.master.repository.MasterApplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MasterApplyService {
	private final MasterApplyRepository masterApplyRepository;

	@Transactional
	public Long apply(User user, String content) {
		MasterApply masterApply = new MasterApply(user, content);
		masterApplyRepository.save(masterApply);
		return masterApply.getId();
	}

	@Transactional
	public void delete(Long applyId) {
		masterApplyRepository.deleteById(applyId);
	}

	public List<MasterApply> getApplies() {
		return masterApplyRepository.findAll();
	}

	public boolean isApplied(Long userId) {
		Optional<MasterApply> findApply = masterApplyRepository.findByUserId(userId);
		if (findApply.isPresent())
			return true;
		return false;
	}

}
