package edu.baylor.ecs.rt;


import edu.baylor.ecs.rt.Service.LicenseService;
import edu.baylor.ecs.rt.Service.OntologyService;
import edu.baylor.ecs.rt.Service.PurchaseService;
import edu.baylor.ecs.rt.Service.UserService;
import edu.baylor.ecs.rt.model.License;
import edu.baylor.ecs.rt.model.LicenseType;
import edu.baylor.ecs.rt.model.auth.User;

import edu.baylor.ecs.rt.model.nodes.Edge;
import edu.baylor.ecs.rt.model.nodes.EdgeCategory;
import edu.baylor.ecs.rt.model.nodes.Node;
import edu.baylor.ecs.rt.model.nodes.NodeCategory;
import edu.baylor.ecs.rt.repository.LicenseRepository;
import edu.baylor.ecs.rt.repository.UserRepository;
import edu.baylor.ecs.rt.repository.neo4j.EdgeRepository;
import edu.baylor.ecs.rt.repository.neo4j.NodeRepository;
import edu.baylor.ecs.rt.security.payload.request.OntologyRequest;
import edu.baylor.ecs.rt.security.payload.request.PurchaseRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

@SpringBootTest
class OntologioApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    LicenseRepository licenseRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    EdgeRepository edgeRepository;

    //test function1: register - populate user data
    @Test
    void createUser() {
        User user = new User();
        user.setUsername("test1");
        user.setFirstName("Test");
        user.setLastName("One");
        user.setPassword("1234");
        user.setEmail("test1@test.com");
        userRepository.save(user);
    }

    // test function2: purchase a license - add a sale record
    @Test
    void purchaseLicense() throws IOException, MessagingException {
        PurchaseRequest request = new PurchaseRequest();
        request.setPrice(BigInteger.valueOf(1000));
        request.setTypel(BigInteger.valueOf(2));
        request.setUsername("Rajashree");
        purchaseService.purchase(request);
    }

    //test function3: update user profile - update user data
    @Test
    void updateUser() {
        User user = userRepository.findByUsername("test1").get();
        user.setFirstName("Test");
        user.setLastName("Update");
        userRepository.save(user);
    }
    //test function4: view ontology - list ontology data
    @Test
    void viewOntology(){
        Node node1 = new Node();
        node1.setName("Health Care");
        node1.setNodeCategory(NodeCategory.HealthConcern);
        node1 = nodeRepository.save(node1);

        Node node4 = new Node();
        node4.setName("Physical activity");
        node4.setNodeCategory(NodeCategory.BuildingComponent);
        node4 = nodeRepository.save(node4);
        Edge edge = new Edge();
        edge.setFromNode(node1);
        edge.setToNode(node4);
        edge.setEdgeCategory(EdgeCategory.IS_A);
        edge = edgeRepository.save(edge);

        OntologyRequest request = new OntologyRequest();
        request.setUserId("2");
        OntologyService service = new OntologyService();
        service.findAllOntolology();
    }

    //test function5: search ontology by date - filter ontology data
    @Test
    void searchOntology(){
        OntologyRequest request = new OntologyRequest();
        request.setUserId("1");
        request.setCreateTime(new Date());
        OntologyService service = new OntologyService();
        service.findOntolology(request);
    }

    //test function6: list all license - list license data
    @Test
    void listAllLicense(){
        License pervisit=new License();
        pervisit.setType(LicenseType.PERVISIT);
        pervisit.setPrice(2000);
        pervisit.setYear(2020);
        licenseRepository.save(pervisit);

        License monthly=new License();
        monthly.setType(LicenseType.MONTHLY);
        monthly.setPrice(5000);
        monthly.setYear(2020);
        licenseRepository.save(monthly);

        License yearly=new License();
        yearly.setType(LicenseType.YEARLY);
        yearly.setPrice(10000);
        yearly.setYear(2020);
        licenseRepository.save(yearly);

        LicenseService service = new LicenseService();
        service.findAll();
    }

    //test function7: list license for a user - filter license data
    @Test
    void listLicenseForUser(){
        LicenseService service = new LicenseService();
        service.findByUserId((long)1);
    }

    //test function8: update a license - update license data
    @Test
    void updateLicense(){
        LicenseService service = new LicenseService();
        License license = service.findById((long)1);
        license.setPrice(200);
        service.updateLicense((long)1,license);
    }


}
