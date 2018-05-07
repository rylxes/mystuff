package org.webtree.mystuff.boot;

import com.google.common.base.Strings;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.DriverConfiguration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
@ComponentScan("org.webtree.mystuff")
@EnableNeo4jRepositories("org.webtree.mystuff.repository")
public class Neo4jConfig extends Neo4jConfiguration {
    @Value("${spring.data.neo4j.driver}")
    private String driver;
    @Value("${spring.data.neo4j.uri:#{null}}")
    private String uri;

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory(configuration(), "org.webtree.mystuff.domain");
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        Configuration config = new Configuration();
        DriverConfiguration driverConfiguration = config.driverConfiguration();
        driverConfiguration.setDriverClassName(driver);
        if (!Strings.isNullOrEmpty(uri)) {
            driverConfiguration.setURI(uri);
        }
        return config;
    }
}
