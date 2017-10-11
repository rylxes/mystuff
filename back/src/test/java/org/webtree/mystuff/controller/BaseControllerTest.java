package org.webtree.mystuff.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.webtree.mystuff.BaseSpringTest;

@AutoConfigureMockMvc
public abstract class BaseControllerTest extends BaseSpringTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    protected MockMvc mockMvc;
}
