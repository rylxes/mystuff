package org.webtree.mystuff.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.webtree.mystuff.domain.Stuff;

import java.util.List;

@Transactional
public interface StuffRepository extends GraphRepository<Stuff> {
    @Query("MATCH (stuff: Stuff) RETURN stuff")
    List<Stuff> getAll();

    @Query("MATCH (user:User{username:{username}})-[:use]->(stuff:Stuff) RETURN stuff")
    List<Stuff> getUserStuff(@Param("username") String username);
}
