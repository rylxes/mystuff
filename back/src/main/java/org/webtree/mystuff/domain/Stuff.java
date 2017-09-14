package org.webtree.mystuff.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@Builder
@Accessors(chain = true)
@NodeEntity
public class Stuff {
    @GraphId
    private Long id;
    private String name;
}