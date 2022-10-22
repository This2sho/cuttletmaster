package PorkCutlet.master.repository;

import PorkCutlet.master.domain.MasterApply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MasterApplyRepository extends JpaRepository<MasterApply, Long> {

    @Override
    @EntityGraph(attributePaths = {"user"})
    List<MasterApply> findAll();

    Optional<MasterApply> findByUserId(Long userId);
}