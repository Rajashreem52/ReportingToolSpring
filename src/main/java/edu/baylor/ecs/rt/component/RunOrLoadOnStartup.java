package edu.baylor.ecs.rt.component;


import edu.baylor.ecs.rt.model.auth.ERole;
import edu.baylor.ecs.rt.model.auth.Role;
import edu.baylor.ecs.rt.model.nodes.Edge;
import edu.baylor.ecs.rt.model.nodes.EdgeCategory;
import edu.baylor.ecs.rt.model.nodes.Node;
import edu.baylor.ecs.rt.model.nodes.NodeCategory;
import edu.baylor.ecs.rt.model.nodes.Ontology;
import edu.baylor.ecs.rt.model.License;
import edu.baylor.ecs.rt.model.LicenseType;

import edu.baylor.ecs.rt.repository.RoleRepository;

import edu.baylor.ecs.rt.repository.neo4j.EdgeRepository;
import edu.baylor.ecs.rt.repository.neo4j.NodeRepository;
import edu.baylor.ecs.rt.repository.neo4j.OntologyRepository;
import edu.baylor.ecs.rt.repository.LicenseRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RunOrLoadOnStartup {

    @Autowired
    RoleRepository roleRepository;

   

    @Autowired
    LicenseRepository licenseRepository;

    
    @Autowired
    NodeRepository nodeRepository;
    
    @Autowired
    EdgeRepository edgeRepository;
    
    @Autowired
    OntologyRepository ontologyRepository;
    
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {

        createUserRoles();
        createLicenses();
        populateGraph();

    }

    private void createLicenses() {
		License pervisit=new License();
		pervisit.setType(LicenseType.PERVISIT);
		pervisit.setPrice(200);
		pervisit.setYear(2020);
		licenseRepository.save(pervisit);
		
		License monthly=new License();
		monthly.setType(LicenseType.MONTHLY);
		monthly.setPrice(500);
		monthly.setYear(2020);
		licenseRepository.save(monthly);
	
		License yearly=new License();
		yearly.setType(LicenseType.YEARLY);
		yearly.setPrice(700);
		yearly.setYear(2020);
		licenseRepository.save(yearly);

		
		
	}

	private void createUserRoles() {
        // Create USER role:
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
        if(userRole == null) {
            userRole = new Role(ERole.ROLE_USER);
            roleRepository.save(userRole);
        }
        // Create ADMIN role:
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);
        if(adminRole == null) {
            adminRole = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
        // Create SUPERADMIN role:
        Role superAdminRole = roleRepository.findByName(ERole.ROLE_SUPERADMIN).orElse(null);
        if(superAdminRole == null) {
            superAdminRole = new Role(ERole.ROLE_SUPERADMIN);
            roleRepository.save(superAdminRole);
        }

    }

   private void populateGraph(){
	   
	   Node node1 = new Node();
       node1.setName("Operational Policies");
       node1.setNodeCategory(NodeCategory.BuildingComponent);
       node1 = nodeRepository.save(node1);

       Node node2 = new Node();
       node2.setName("room");
       node2.setNodeCategory(NodeCategory.HealthConcern);
       node2 = nodeRepository.save(node2);

       Edge edge1 = new Edge();
       edge1.setFromNode(node1);
       edge1.setToNode(node2);
       edge1.setEdgeCategory(EdgeCategory.IS_A);
       edge1=edgeRepository.save(edge1);

       Ontology ontology = new Ontology();
       ontology.setName("Architects");
       List<Long> edgeList = new ArrayList<>();
       edgeList.add(edge1.getId());
       ontology.setEdgeList(edgeList);
       createOntology(ontology);



       Node node3 = new Node();
       node3.setName("student");
       node3.setNodeCategory(NodeCategory.Nutrition);
       node3 = nodeRepository.save(node3);

       Node node4 = new Node();
       node4.setName("teacher");
       node4.setNodeCategory(NodeCategory.HealthConcern);
       node4 = nodeRepository.save(node4);

       Edge edge2 = new Edge();
       edge2.setFromNode(node3);
       edge2.setToNode(node4);
       edge2.setEdgeCategory(EdgeCategory.IS_A);
       edge2=edgeRepository.save(edge2);

       Ontology ontology2 = new Ontology();
       ontology2.setName("Civil Engineering");
       List<Long> edgeList2 = new ArrayList<>();
       edgeList2.add(edge2.getId());
       ontology2.setEdgeList(edgeList2);
       createOntology(ontology2);
	   
   }

   public Ontology createOntology(Ontology ontology){
       ontology.setCreateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
       System.out.println(ontology.getCreateTime());
       System.out.println(ontology);
       return ontologyRepository.save(ontology);
   }

}
