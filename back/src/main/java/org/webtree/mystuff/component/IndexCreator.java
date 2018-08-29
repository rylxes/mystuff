package org.webtree.mystuff.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Component;

import java.util.Collections;
import javax.annotation.PostConstruct;

@Component
public class IndexCreator {
    private final Neo4jOperations neo4jTemplate;


    @Autowired
    public IndexCreator(Neo4jOperations neo4jTemplate) {
        this.neo4jTemplate = neo4jTemplate;

    }

    @PostConstruct
    public void createIndexes() {
        neo4jTemplate.query("CREATE CONSTRAINT ON (n:Category) ASSERT n.name IS UNIQUE", Collections.emptyMap());
    }
}
