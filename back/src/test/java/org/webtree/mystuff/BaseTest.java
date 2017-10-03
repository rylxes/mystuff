package org.webtree.mystuff;


import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.webtree.mystuff.boot.App;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public abstract class BaseTest {
    @Autowired
    private ApplicationContext ctx;

    protected void clearAllGraphRepositories() {
        Map<String, GraphRepository> graphRepositories = ctx.getBeansOfType(GraphRepository.class);
        for (GraphRepository graphRepository : graphRepositories.values()) {
            graphRepository.deleteAll();
        }
    }

    public class ClearGraphDBRule implements TestRule {
        @Override
        public Statement apply(Statement statement, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    statement.evaluate();
                    clearAllGraphRepositories();
                }
            };
        }
    }
}
