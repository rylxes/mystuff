package org.webtree.mystuff.model.rest;


import org.webtree.mystuff.model.domain.Stuff;

import java.util.Set;
import javax.validation.constraints.NotNull;

public class CreateStuff {
    @NotNull
    private Stuff stuff;
    private Set<Long> categories;

    public Stuff getStuff() {
        return stuff;
    }

    public CreateStuff setStuff(Stuff stuff) {
        this.stuff = stuff;
        return this;
    }

    public Set<Long> getCategories() {
        return categories;
    }

    public CreateStuff setCategories(Set<Long> categories) {
        this.categories = categories;
        return this;
    }

    public static final class Builder {
        private Stuff stuff;
        private Set<Long> categories;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withStuff(Stuff stuff) {
            this.stuff = stuff;
            return this;
        }

        public Builder withCategories(Set<Long> categories) {
            this.categories = categories;
            return this;
        }

        public CreateStuff build() {
            CreateStuff createStuff = new CreateStuff();
            createStuff.setStuff(stuff);
            createStuff.setCategories(categories);
            return createStuff;
        }
    }
}
