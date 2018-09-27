package org.webtree.mystuff.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.webtree.mystuff.model.domain.User;

@Repository
public interface UserRepository extends GraphRepository<User> {
    @Query("MATCH (user:User{username:{username}}) RETURN user;")
    User findByUsername(@Param("username") String username);
}
