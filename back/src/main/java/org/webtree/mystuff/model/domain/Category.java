package org.webtree.mystuff.model.domain;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by Udjin on 01.03.2018.
 */
@NodeEntity
public class Category {
    @GraphId
    private Long id;
    private String name;
    @Relationship(type = "create", direction = INCOMING)
    private User creator;

    public Category(Long id, String name, User creator) {
        this.id = id;
        this.name = name;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public Category setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public User getCreator() {
        return creator;
    }

    public Category setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public static final class Builder {
        private Long id;
        private String name;
        private User creator;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCreator(User creator) {
            this.creator = creator;
            return this;
        }

        public Category build() {
            return new Category(id, name, creator);
        }
    }
}
