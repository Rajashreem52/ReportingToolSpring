package edu.baylor.ecs.rt.Service;
import edu.baylor.ecs.rt.model.Sale;
import edu.baylor.ecs.rt.model.auth.User;
import edu.baylor.ecs.rt.model.nodes.*;
import edu.baylor.ecs.rt.repository.neo4j.EdgeRepository;
import edu.baylor.ecs.rt.repository.neo4j.NodeRepository;
import edu.baylor.ecs.rt.repository.neo4j.OntologyRepository;
import edu.baylor.ecs.rt.repository.UserRepository;
import edu.baylor.ecs.rt.security.payload.request.OntologyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OntologyService {
    @Autowired
    OntologyRepository ontologyRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    EdgeRepository edgeRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    public Ontology createOntology(Ontology ontology){
        ontology.setCreateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        System.out.println(ontology.getCreateTime());
        System.out.println(ontology);
        return ontologyRepository.save(ontology);
    }


    public Ontology findById(Long id){
        return ontologyRepository.findById(id).get();
    }

    public Iterable<Ontology> findAllOntolology(){
        return ontologyRepository.findAll();
    }


    public List<Edge> findOntolology(OntologyRequest ontologyRequest){
        //check if the userid is valid for accessing the data
        Long userId = Long.valueOf(ontologyRequest.getUserId());
       
      
        Boolean stat= getlicensestatus(userId);
        if(stat==false)
        {
        	return null;
        }
        List<Edge> edges = new ArrayList<>();
        String name = ontologyRequest.getName();
        Date createdTime = ontologyRequest.getCreateTime();

        if(name == null && createdTime == null) {
            edges = createResponse(findAllOntolology());
        } else if(name != null && createdTime == null) {
            edges = createResponse(ontologyRepository.findByName(name));
        } else if(name == null && createdTime != null) {
            List<Ontology> filteredOntology = filterByTime(createdTime,findAllOntolology());
            edges = createResponse(filteredOntology);
        } else {
            List<Ontology> filteredOntology = filterByTime(createdTime,ontologyRepository.findByName(name));
            edges = createResponse(filteredOntology);
        }
        return edges;
        
    }

    private List<Ontology> filterByTime(Date createdTime, Iterable<Ontology> ontologies) {
        // 2020-11-29
        // 2020-11-29 20:17:01.486
        List<Ontology> filteredOntologies = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(createdTime); // Now use today date.
        c.add(Calendar.DATE, 1); // Adding 5 days

        Date date = c.getTime();
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        for(Ontology ontology : ontologies) {
            Date newdate = new Date(Timestamp.valueOf(ontology.getCreateTime()).getTime());
            int newYear = newdate.getYear();
            int newMonth = newdate.getMonth();
            int newDay = newdate.getDay();
            if(year == newYear && month == newMonth && day == newDay) {
                filteredOntologies.add(ontology);
            }
        }
        return filteredOntologies;
    }

    private List<Edge> createResponse(Iterable<Ontology> ontologies) {
        List<Edge> edges = new ArrayList<>();
        for(Ontology ontology : ontologies) {
            if(ontology.getEdgeList() == null) {
                continue;
            }
            for(long edgeId : ontology.getEdgeList()) {
                Edge edge = edgeRepository.findById(edgeId).get();
                edges.add(edge);
            }
        }
        return edges;
    }

    public Ontology updateOntology(Ontology ontology){
        if(findById(ontology.getId())!=null) {
            return ontologyRepository.save(ontology);
        }
        return null;
    }
    public void deleteById(Long id){
        ontologyRepository.deleteById(id);
    }


    private boolean getlicensestatus(Long id) {
        Connection c = null;
        Statement stmt = null;
        boolean flag=false;
        try {
            //Query query = em.createQuery("SELECT MAX(s.expired_date) FROM Sale s WHERE s.user_id=?0 GROUP BY s.user_id");
            User user = userRepository.findById(id).get();
            Query query = em.createQuery("SELECT s FROM Sale s WHERE s.user = ?0");
            query.setParameter(0, user);
            List<Sale> list = (List<Sale>) query.getResultList();
            for(int i=0;i<list.size();i++)
            {
            	
            	if(list.get(i).getExpiredDate().getTime()>=new Date().getTime() && list.get(i).isActive()==true)
            	{
            		flag=true;
            		break;
            	}
            }

            

            return flag;

        }
        catch ( Exception e ) {
            //System.exit(0);
        }
        return flag;
    }
    
   

    public void populate(){
//        Node node1 = new Node();
//        node1.setName("Operational Policies");
//        node1.setNodeCategory(NodeCategory.BuildingComponent);
//        node1 = nodeRepository.save(node1);
//
//        Node node2 = new Node();
//        node2.setName("Nutrition");
//        node2.setNodeCategory(NodeCategory.HealthConcern);
//        node2 = nodeRepository.save(node2);
//
//        Edge edge1 = new Edge();
//        edge1.setFromNode(node1);
//        edge1.setToNode(node2);
//        edge1.setEdgeCategory(EdgeCategory.IS_A);
//        edge1=edgeRepository.save(edge1);
//
//        Node node3 = new Node();
//        node3.setName("Nutritional Labeling");
//        node3.setNodeCategory(NodeCategory.Nutrition);
//        node3 = nodeRepository.save(node3);
//
//        Edge edge2 = new Edge();
//        edge2.setFromNode(node2);
//        edge2.setToNode(node3);
//        edge2.setEdgeCategory(EdgeCategory.IS_A);
//        edge2=edgeRepository.save(edge2);
//
//        Node node4 = new Node();
//        node4.setName("Physical Activity");
//        node4.setNodeCategory(NodeCategory.HealthConcern);
//        node4 = nodeRepository.save(node4);
//
//        Edge edge3 = new Edge();
//        edge3.setFromNode(node1);
//        edge3.setToNode(node4);
//        edge3.setEdgeCategory(EdgeCategory.IS_A);
//        edge3=edgeRepository.save(edge3);
//
//        Node node5 = new Node();
//        node5.setName("Encourage Use of Vertical Circulaiton");
//        node5.setNodeCategory(NodeCategory.HealthConcern);
//        node5 = nodeRepository.save(node5);
//
//        Edge edge4 = new Edge();
//        edge4.setFromNode(node4);
//        edge4.setToNode(node5);
//        edge4.setEdgeCategory(EdgeCategory.IS_A);
//        edge4=edgeRepository.save(edge4);
//
//        Node node6 = new Node();
//        node6.setName("Use of Step Counter");
//        node6.setNodeCategory(NodeCategory.HealthConcern);
//        node6 = nodeRepository.save(node6);
//
//        Edge edge5 = new Edge();
//        edge5.setFromNode(node5);
//        edge5.setToNode(node6);
//        edge5.setEdgeCategory(EdgeCategory.IS_A);
//        edge5=edgeRepository.save(edge5);
//
//        Edge edge6 = new Edge();
//        edge6.setFromNode(node4);
//        edge6.setToNode(node6);
//        edge6.setEdgeCategory(EdgeCategory.IS_A);
//        edge6=edgeRepository.save(edge6);
//
//        Ontology ontology = new Ontology();
//        ontology.setName("architects, civil engineers, and material engineers");
//        List<Long> edgeList = new ArrayList<>();
//        edgeList.add(edge1.getId());
//        edgeList.add(edge2.getId());
//        edgeList.add(edge3.getId());
//        edgeList.add(edge4.getId());
//        edgeList.add(edge5.getId());
//        edgeList.add(edge6.getId());
//        ontology.setEdgeList(edgeList);
//        createOntology(ontology);

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


//        Node node5 = new Node();
//        node5.setName("Encourage Use of Vertical Circulaiton");
//        node5.setNodeCategory(NodeCategory.HealthConcern);
//        node5 = nodeRepository.save(node5);
//
//        Edge edge4 = new Edge();
//        edge4.setFromNode(node4);
//        edge4.setToNode(node5);
//        edge4.setEdgeCategory(EdgeCategory.IS_A);
//        edge4=edgeRepository.save(edge4);
//
//        Node node6 = new Node();
//        node6.setName("Use of Step Counter");
//        node6.setNodeCategory(NodeCategory.HealthConcern);
//        node6 = nodeRepository.save(node6);
//
//        Edge edge5 = new Edge();
//        edge5.setFromNode(node5);
//        edge5.setToNode(node6);
//        edge5.setEdgeCategory(EdgeCategory.IS_A);
//        edge5=edgeRepository.save(edge5);
//
//        Edge edge6 = new Edge();
//        edge6.setFromNode(node4);
//        edge6.setToNode(node6);
//        edge6.setEdgeCategory(EdgeCategory.IS_A);
//        edge6=edgeRepository.save(edge6);

    }
}
