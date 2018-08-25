package org.webtree.mystuff.model.rest;

import lombok.Builder;
import lombok.Data;
import org.webtree.mystuff.model.domain.Stuff;

import java.util.Set;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateStuff {
    @NotNull
    private Stuff stuff;
    private Set<Long> categories;
}
