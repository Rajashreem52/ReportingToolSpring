package edu.baylor.ecs.rt.model.nodes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "Relation")
public class Edge {
    @Id
    @GeneratedValue
    private Long id;

    private EdgeCategory edgeCategory;

    /*@JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,property = "id" )
    @JsonIdentityReference(alwaysAsId = true)*/
    @StartNode
    private Node fromNode;

    /*@JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,property = "id" )
    @JsonIdentityReference(alwaysAsId = true)*/
    @EndNode
    private Node toNode;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,property = "id" )
    @JsonIdentityReference(alwaysAsId = true)
    @Relationship(type = "OWNS")
    private Ontology ownerOntology;

    public Edge() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EdgeCategory getEdgeCategory() {
        return edgeCategory;
    }

    public void setEdgeCategory(EdgeCategory edgeCategory) {
        this.edgeCategory = edgeCategory;
    }

    public Node getFromNode() {
        return fromNode;
    }

    public void setFromNode(Node fromNode) {
        this.fromNode = fromNode;
    }

    public Node getToNode() {
        return toNode;
    }

    public void setToNode(Node toNode) {
        this.toNode = toNode;
    }
}