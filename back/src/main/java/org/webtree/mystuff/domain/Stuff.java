package org.webtree.mystuff.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@Data
@Builder
@NodeEntity
@JsonInclude(NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class Stuff {
    @GraphId
    private Long id;
    private String name;
    private String description;
    @Relationship(type = "use", direction = INCOMING)
    private Set<User> users;
    private List<StuffCategory> categories;
}