package org.webtree.mystuff.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.webtree.mystuff.boot.App;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.domain.StuffCategory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class StuffServiceTest {
    private static final long ID = 123L;
    public static final String NAME = "test name";

    @Autowired
    private StuffService stuffService;

    @Test
    public void testAddAndGetStuff() throws Exception {
        StuffCategory sc1 = StuffCategory.builder().category("category1").build();
        StuffCategory sc2 = StuffCategory.builder().category("category2").build();
        assertThat(stuffService.getById(ID)).isNull();
        Stuff stuff = Stuff.builder().name(NAME).category(sc1).category(sc2).build();
        Stuff addedStuff = stuffService.save(stuff);
        assertThat(addedStuff.getId()).isNotNull();
        Stuff byId = stuffService.getById(addedStuff.getId());
        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo(NAME);
        assertThat(byId.getCategories()).isEqualTo(stuff.getCategories());

    }
}