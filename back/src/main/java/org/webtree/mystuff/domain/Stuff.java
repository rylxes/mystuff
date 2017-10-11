package org.webtree.mystuff.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@Data
@Builder
@NodeEntity
@JsonInclude(NON_EMPTY)
public class Stuff {
    @GraphId
    private Long id;
    private String name;
    @Relationship(type = "use", direction = INCOMING)
    private Set<User> users;
}