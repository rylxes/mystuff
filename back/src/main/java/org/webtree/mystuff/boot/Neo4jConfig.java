package org.webtree.mystuff.boot;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("org.webtree.mystuff")
@EnableNeo4jRepositories("org.webtree.mystuff.repository")
public class Neo4jConfig extends Neo4jConfiguration {
    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("org.webtree.mystuff.domain");
    }
}
