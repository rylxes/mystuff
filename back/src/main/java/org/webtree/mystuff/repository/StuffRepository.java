package org.webtree.mystuff.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.webtree.mystuff.domain.Stuff;

public interface StuffRepository extends GraphRepository<Stuff> {

}
