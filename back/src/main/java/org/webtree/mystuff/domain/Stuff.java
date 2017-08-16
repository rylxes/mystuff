package org.webtree.mystuff.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Stuff {
    private Long id;
    private String name;
}
