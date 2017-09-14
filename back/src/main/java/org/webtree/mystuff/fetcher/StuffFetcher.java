package org.webtree.mystuff.fetcher;

import com.merapar.graphql.base.TypedValueMap;
import lombok.val;
import org.springframework.stereotype.Component;
import org.webtree.mystuff.domain.Stuff;

import java.util.*;

@Component
public class StuffFetcher {

    //TODO: replace by service
    public Map<Long, Stuff> stuffList = new HashMap<>();

    public Stuff delete(TypedValueMap arguments) {
        val stuff = stuffList.get(arguments.get("id"));
        stuffList.remove(stuff.getId());
        return stuff;
    }

    public Stuff update(TypedValueMap arguments) {
        val stuff = stuffList.get(arguments.get("id"));
        if (arguments.containsKey("name")) {
            stuff.setName(arguments.get("name"));
        }
        return stuff;
    }

    public Stuff add(TypedValueMap arguments) {
        val stuff = Stuff.builder()
            .id(arguments.get("id"))
            .name(arguments.get("name"))
            .build();
        stuffList.put(stuff.getId(), stuff);
        return stuff;
    }

    public List<Stuff> getByFilter(TypedValueMap arguments) {
        Long id = arguments.get("id");
        if (id != null) {
            return Collections.singletonList(stuffList.get(id));
        } else {
            return new ArrayList<>(stuffList.values());
        }
    }
}
