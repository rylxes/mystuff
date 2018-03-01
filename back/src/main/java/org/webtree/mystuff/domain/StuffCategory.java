package org.webtree.mystuff.domain;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by Udjin on 01.03.2018.
 */
@Data

@NodeEntity
public class StuffCategory {
    @GraphId
    private long id;
    private String category;
}
