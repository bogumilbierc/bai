package pl.bogumil.bai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.bogumil.bai.entity.NotExistingUserProfile;

/**
 * Created by bbierc on 2016-04-01.
 */
@Repository
public interface NotExistingUserRepository extends JpaRepository<NotExistingUserProfile, Integer>, QueryDslPredicateExecutor<NotExistingUserProfile> {
}
