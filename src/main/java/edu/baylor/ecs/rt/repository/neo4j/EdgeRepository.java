package edu.baylor.ecs.rt.repository.neo4j;

import edu.baylor.ecs.rt.model.nodes.Edge;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EdgeRepository extends Neo4jRepository<Edge, Long> {

}
