package org.webtree.mystuff.restModel;

import lombok.Builder;
import lombok.Data;
import org.webtree.mystuff.domain.Stuff;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
public class CreateStuff {
    @NotNull
    private Stuff stuff;
    private Set<Long> categories;
}
