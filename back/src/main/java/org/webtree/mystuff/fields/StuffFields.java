package org.webtree.mystuff.fields;

import com.merapar.graphql.GraphQlFields;
import graphql.Scalars;
import graphql.schema.*;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webtree.mystuff.fetcher.StuffFetcher;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.merapar.graphql.base.GraphQlFieldsHelper.*;
import static com.merapar.graphql.base.GraphQlFieldsHelper.INPUT;
import static com.merapar.graphql.base.GraphQlFieldsHelper.getInputMap;
import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLLong;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;

@Component
public class StuffFields implements GraphQlFields {
    private final StuffFetcher stuffFetcher;

    private GraphQLObjectType stuffType;

    private GraphQLInputObjectType addStuffInputType;
    private GraphQLInputObjectType updateStuffInputType;
    private GraphQLInputObjectType deleteStuffInputType;

    private GraphQLInputObjectType filterStuffInputType;

    private GraphQLFieldDefinition stuffsField;
    private GraphQLFieldDefinition addStuffField;
    private GraphQLFieldDefinition updateStuffField;
    private GraphQLFieldDefinition deleteStuffField;

    @Getter
    private List<GraphQLFieldDefinition> queryFields;

    @Getter
    private List<GraphQLFieldDefinition> mutationFields;

    @Autowired public StuffFields(StuffFetcher stuffFetcher) {
        this.stuffFetcher = stuffFetcher;
    }

    @PostConstruct
    public void postConstruct() {
        createTypes();
        createFields();
        queryFields = Collections.singletonList(stuffsField);
        mutationFields = Arrays.asList(addStuffField, updateStuffField, deleteStuffField);
    }

    private void createTypes() {
        stuffType = newObject().name("stuff").description("A stuff")
            .field(newFieldDefinition().name("id").description("The id").type(GraphQLInt).build())
            .field(newFieldDefinition().name("name").description("The name").type(GraphQLString).build())
            .build();

        addStuffInputType = newInputObject().name("addStuffInput")
            .field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLLong)).build())
            .field(newInputObjectField().name("name").type(new GraphQLNonNull(Scalars.GraphQLString)).build())
            .build();

        updateStuffInputType = newInputObject().name("updateStuffInput")
            .field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLLong)).build())
            .field(newInputObjectField().name("name").type(GraphQLString).build())
            .build();

        deleteStuffInputType = newInputObject().name("deleteStuffInput")
            .field(newInputObjectField().name("id").type(new GraphQLNonNull(GraphQLLong)).build())
            .build();

        filterStuffInputType = newInputObject().name("filterStuffInput")
            .field(newInputObjectField().name("id").type(GraphQLLong).build())
            .build();
    }

    private void createFields() {
        stuffsField = newFieldDefinition()
            .name("stuffList").description("Provide an overview of all stuff")
            .type(new GraphQLList(stuffType))
            .argument(newArgument().name(FILTER).type(filterStuffInputType).build())
            .dataFetcher(environment -> stuffFetcher.getByFilter(getFilterMap(environment)))
            .build();

        addStuffField = newFieldDefinition()
            .name("addStuff").description("Add new stuff")
            .type(stuffType)
            .argument(newArgument().name(INPUT).type(new GraphQLNonNull(addStuffInputType)).build())
            .dataFetcher(environment -> stuffFetcher.add(getInputMap(environment)))
            .build();

        updateStuffField = newFieldDefinition()
            .name("updateStuff").description("Update existing stuff")
            .type(stuffType)
            .argument(newArgument().name(INPUT).type(new GraphQLNonNull(updateStuffInputType)).build())
            .dataFetcher(environment -> stuffFetcher.update(getInputMap(environment)))
            .build();

        deleteStuffField = newFieldDefinition()
            .name("deleteStuff").description("Delete existing stuff")
            .type(stuffType)
            .argument(newArgument().name(INPUT).type(new GraphQLNonNull(deleteStuffInputType)).build())
            .dataFetcher(environment -> stuffFetcher.delete(getInputMap(environment)))
            .build();
    }
}
