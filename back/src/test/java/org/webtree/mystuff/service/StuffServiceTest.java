package org.webtree.mystuff.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.webtree.mystuff.boot.App;
import org.webtree.mystuff.domain.Stuff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { App.class})
public class StuffServiceTest {
    private static final long ID = 123L;
    @Autowired
    private StuffService stuffService;

    @Test
    public void testAddAndGetStuff() throws Exception {
        assertThat(stuffService.getById(ID)).isNull();
        Stuff stuff = new Stuff().setName("test name");
        stuffService.addStuff(stuff);
        assertThat(stuffService.getById(ID).getId()).isNull();
    }
}