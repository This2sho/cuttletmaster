package PorkCutlet.master.repository;

import PorkCutlet.master.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public Optional<User> findById(Long id){
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

}
