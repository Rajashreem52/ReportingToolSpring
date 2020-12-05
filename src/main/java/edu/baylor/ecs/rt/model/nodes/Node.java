package edu.baylor.ecs.rt.model.nodes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


import java.util.List;

@NodeEntity
public class Node {
    @Id
    @GeneratedValue
    private Long id;

    private String name;


    private NodeCategory nodeCategory;

    private List<OtherProperties> otherProperties;

    /*@JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,property = "id" )
    @JsonIdentityReference(alwaysAsId = true)
    @Relationship(type = "Relation", direction = Relationship.INCOMING)
    private List<Node> childNodes;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,property = "id" )
    @JsonIdentityReference(alwaysAsId = true)
    @Relationship(type = "Relation", direction = Relationship.OUTGOING)
    private List<Node> parentNodes;*/

    /*@Relationship(type = "HAS_A", direction = Relationship.INCOMING)
    private List<Node> has_a_childNodes;

    @Relationship(type = "HAS_A", direction = Relationship.OUTGOING)
    private List<Node> has_a_ParentNodes;*/

    public Node() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeCategory getNodeCategory() {
        return nodeCategory;
    }

    public void setNodeCategory(NodeCategory nodeCategory) {
        this.nodeCategory = nodeCategory;
    }

    public List<OtherProperties> getOtherProperties() {
        return otherProperties;
    }

    public void setOtherProperties(List<OtherProperties> otherProperties) {
        this.otherProperties = otherProperties;
    }

}
