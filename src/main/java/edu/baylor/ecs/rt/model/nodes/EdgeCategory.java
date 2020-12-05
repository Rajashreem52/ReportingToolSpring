package edu.baylor.ecs.rt.model.nodes;

public enum EdgeCategory {

    IS_A("IS_A"),
    HAS_A("HAS_A");


    public final String label;

    private EdgeCategory(String label){
        this.label = label;
    }




}
