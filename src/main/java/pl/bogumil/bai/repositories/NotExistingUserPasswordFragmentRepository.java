package pl.bogumil.bai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bogumil.bai.entity.PasswordFragmentNotExistingUser;

/**
 * Created by bogumil on 4/17/16.
 */
@Repository
public interface NotExistingUserPasswordFragmentRepository extends JpaRepository<PasswordFragmentNotExistingUser, Integer> {
}
