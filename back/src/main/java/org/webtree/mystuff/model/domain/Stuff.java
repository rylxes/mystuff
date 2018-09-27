package org.webtree.mystuff.model.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static org.neo4j.ogm.annotation.Relationship.INCOMING;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@JsonInclude(NON_EMPTY)
public class Stuff {
    @GraphId
    private Long id;
    private String name;
    private String description;
    @Relationship(type = "use", direction = INCOMING)
    private Set<User> users;
    @Relationship(type = "create", direction = INCOMING)
    private User creator;
    @Relationship(type = "has")
    private Set<Category> categories;

    public Long getId() {
        return id;
    }

    public Stuff setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Stuff setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Stuff setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Stuff setUsers(Set<User> users) {
        this.users = users;
        return this;
    }

    public User getCreator() {
        return creator;
    }

    public Stuff setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Stuff setCategories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String description;
        private Set<User> users;
        private User creator;
        private Set<Category> categories = new HashSet<>();

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

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withUsers(Set<User> users) {
            this.users = users;
            return this;
        }

        public Builder withCreator(User creator) {
            this.creator = creator;
            return this;
        }

        public Builder withCategories(Set<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Builder addCategory(Category category) {
            this.categories.add(category);
            return this;
        }

        public Stuff build() {
            Stuff stuff = new Stuff();
            stuff.setId(id);
            stuff.setName(name);
            stuff.setDescription(description);
            stuff.setUsers(users);
            stuff.setCreator(creator);
            stuff.setCategories(categories);
            return stuff;
        }
    }
}