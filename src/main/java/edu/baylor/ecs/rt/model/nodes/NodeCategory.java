package edu.baylor.ecs.rt.model.nodes;

public enum NodeCategory {


	BuildingComponent("Builidng Component"),
    Nutrition("Nutrition"),
    HealthConcern("Health Concern");

    public final String label;

    private NodeCategory(String label){
        this.label = label;
    }
	

}
