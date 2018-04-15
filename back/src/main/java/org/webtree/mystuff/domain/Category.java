package org.webtree.mystuff.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/**
 * Created by Udjin on 01.03.2018.
 */
@Data
@NodeEntity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    public static final String USER_RELATIONSHIP = "creator";
    @GraphId
    private Long id;
    private String name;
    @Relationship(type = "create", direction = INCOMING)
    private User creator;
}
