package org.webtree.mystuff;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.Map;

public abstract class GraphRepoTest extends BaseTest {

    @Autowired
    private ApplicationContext ctx;

    @After
    public void tearDown() throws Exception {
        clearAllGraphRepositories();
    }

    protected void clearAllGraphRepositories() {
        Map<String, GraphRepository> graphRepositories = ctx.getBeansOfType(GraphRepository.class);
        for (GraphRepository graphRepository : graphRepositories.values()) {
            graphRepository.deleteAll();
        }
    }
}
