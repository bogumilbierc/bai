package pl.bogumil.bai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.bogumil.bai.entity.UserProfile;

/**
 * Created by bbierc on 2016-03-31.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>, QueryDslPredicateExecutor<UserProfile> {
}
