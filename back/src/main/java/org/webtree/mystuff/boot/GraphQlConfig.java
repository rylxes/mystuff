package org.webtree.mystuff.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.webtree.mystuff.fetcher.StuffFetcher;
import org.webtree.mystuff.fields.StuffFields;

import javax.annotation.PostConstruct;

@Configuration
public class GraphQlConfig {
    @Bean
    public StuffFetcher stuffFetcher() {
        return new StuffFetcher();
    }

    @Bean
    public StuffFields stuffFields(StuffFetcher stuffFetcher) {
        return new StuffFields(stuffFetcher);
    }
}
