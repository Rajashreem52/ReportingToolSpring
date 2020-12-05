package edu.baylor.ecs.rt.model.relationships;

import edu.baylor.ecs.rt.model.nodes.Customer;
import edu.baylor.ecs.rt.model.nodes.License;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "OWNS")
public class Membership {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @StartNode
    private Customer customer;

    @EndNode
    private License license;

    public Membership() {
    }

    public Membership(License license, Customer customer) {
        this.license = license;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public License getLicense() {
        return license;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}