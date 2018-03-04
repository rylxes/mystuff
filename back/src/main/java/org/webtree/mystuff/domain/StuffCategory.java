package org.webtree.mystuff.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by Udjin on 01.03.2018.
 */
@Data

@NodeEntity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StuffCategory {
    @GraphId
    private Long id;
    private String category;
}
