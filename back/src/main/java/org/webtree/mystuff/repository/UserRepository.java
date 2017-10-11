package org.webtree.mystuff.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.webtree.mystuff.domain.User;

public interface UserRepository extends GraphRepository<User> {
    @Query("MATCH (user:User{username:{username}}) RETURN user;")
    User findByUsername(@Param("username") String username);
}
