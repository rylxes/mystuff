package org.webtree.mystuff.service;

import com.merapar.graphql.GraphQlAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.webtree.mystuff.fetcher.StuffFetcher;
import org.webtree.mystuff.fields.StuffFields;

@Configuration
@Import(GraphQlAutoConfiguration.class)
public class GraphQlConf {
    @Bean
    public StuffFetcher stuffFetcher() {
        return new StuffFetcher();
    }

    @Bean
    public StuffFields stuffFields(StuffFetcher stuffFetcher) {
        return new StuffFields(stuffFetcher);
    }

}
