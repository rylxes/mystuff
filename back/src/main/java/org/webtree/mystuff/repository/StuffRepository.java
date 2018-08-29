package org.webtree.mystuff.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.webtree.mystuff.model.domain.Stuff;

import java.util.List;
import java.util.Set;

@Transactional
public interface StuffRepository extends GraphRepository<Stuff> {

    @Query("MATCH (stuff: Stuff) RETURN stuff")
    List<Stuff> getAll();

    @Query("MATCH (user:User{username:{username}})-[:use]->(stuff:Stuff) RETURN stuff")
    List<Stuff> getUserStuff(@Param("username") String username);

    @Query("UNWIND {categories} AS cat MATCH (c:Category) "
        + "WHERE id(c)=cat MATCH(s:Stuff) WHERE id(s)={id} CREATE (s)-[:has]->(c)")
    void addRelationshipsWithCategories(@Param("categories") Set<Long> categories, @Param("id") Long id);

    @Query("UNWIND {users} AS user MATCH (u:User) "
        + "WHERE id(u)=user  MATCH(s:Stuff) WHERE id(s)={id} CREATE (u)-[:use]->(s)")
    void addRelationshipsWithUsers(@Param("users") Set<Long> users, @Param("id") Long id);

    @Query("MATCH (u:User) WHERE id(u)={user} MATCH(s:Stuff) "
        + "WHERE id(s)={id} CREATE (u)-[:create]->(s) CREATE (u)-[:use]->(s)")
    void addCreateRelationship(@Param("user") Long user, @Param("id") Long id);
}
