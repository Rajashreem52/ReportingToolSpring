package edu.baylor.ecs.rt.repository.neo4j;

import edu.baylor.ecs.rt.model.nodes.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface NodeRepository extends Neo4jRepository<Node, Long> {

    Optional<Node> findById(Long id);

}
