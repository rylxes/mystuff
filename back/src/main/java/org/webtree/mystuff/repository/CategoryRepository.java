package org.webtree.mystuff.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.webtree.mystuff.domain.Category;

import java.util.List;


@Transactional
public interface CategoryRepository extends GraphRepository<Category> {

    @Query("MATCH (n:Category) WHERE n.name STARTS WITH {searchString}  RETURN n")
    List<Category> getCategoriesBySearchString(@Param("searchString") String searchString);

    @Query("MATCH (s:Category{name:{name}}) RETURN s")
    Category getByName(@Param("name") String name);
}

