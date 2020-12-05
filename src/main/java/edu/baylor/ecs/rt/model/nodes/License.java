package edu.baylor.ecs.rt.model.nodes;

import java.util.ArrayList;
import java.util.List;

import edu.baylor.ecs.rt.model.relationships.Membership;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@NodeEntity
public class License {

    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private boolean paid;

    @JsonIgnoreProperties("license")
    @Relationship(type = "OWNS", direction = Relationship.INCOMING)
    private List<Membership> memberships;

    public License() {
    }

    public License(String type, boolean paid) {
        this.type = type;
        this.paid = paid;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean getPaid() {
        return paid;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void addMembership(Membership membership) {
        if (this.memberships == null) {
            this.memberships = new ArrayList<>();
        }
        this.memberships.add(membership);
    }
}
