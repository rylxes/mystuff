package org.webtree.mystuff.model.domain;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by Udjin on 01.03.2018.
 */
@Data
@NodeEntity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @GraphId
    private Long id;
    private String name;
    @Relationship(type = "create", direction = INCOMING)
    private User creator;
}
