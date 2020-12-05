package edu.baylor.ecs.rt.model.nodes;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int born;

    @Relationship(type = "OWNS")
    private List<License> licenses = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, int born) {
        this.name = name;
        this.born = born;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBorn() {
        return born;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void addLicense(License license) {
        if(licenses == null) {
            licenses = new ArrayList<>();
        }
        licenses.add(license);
    }

}
