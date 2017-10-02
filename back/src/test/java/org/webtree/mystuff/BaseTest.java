package org.webtree.mystuff;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.webtree.mystuff.boot.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public abstract class BaseTest {
}
