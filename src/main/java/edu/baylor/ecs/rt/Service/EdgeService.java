package edu.baylor.ecs.rt.Service;
import edu.baylor.ecs.rt.model.nodes.Edge;
import edu.baylor.ecs.rt.repository.neo4j.EdgeRepository;
import edu.baylor.ecs.rt.repository.neo4j.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


@Service
public class EdgeService {
    @Autowired
    EdgeRepository edgeRepository;

    @Autowired
    NodeRepository nodeRepository;

    public Edge createEdge(Edge edge){
        /*Node fromNode = edge.getFromNode();
        Node toNode = edge.getToNode();
        List<Node> temp1 = new LinkedList<>();
        temp1.add(toNode);
        List<Node> temp2 = new LinkedList<>();
        temp2.add(fromNode);
        fromNode.setChildNodes(temp1);
        toNode.setParentNodes(temp2);
        nodeRepository.save(fromNode);
        nodeRepository.save(toNode);*/
        return edgeRepository.save(edge);
    }

    public Iterable<Edge> findAllEdge(){
        return edgeRepository.findAll();
    }

    public Edge findById(Long id){
        return edgeRepository.findById(id).get();
    }

    public Edge updateEdge(Edge edge){
        if(findById(edge.getId())!=null) {
            return edgeRepository.save(edge);
        }
        return null;
    }
    public void deleteById(Long id){
        edgeRepository.deleteById(id);
    }

}
