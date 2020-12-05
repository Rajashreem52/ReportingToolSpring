package edu.baylor.ecs.rt.repository.neo4j;

import edu.baylor.ecs.rt.model.nodes.Ontology;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface OntologyRepository extends Neo4jRepository<Ontology, Long> {
	 @Depth(value = 2)
	    Optional<Ontology> findById(Long id);

	    @Depth(value = 2)
	    Iterable<Ontology> findByName(String name);

	    @Depth(value = 2)
	    Iterable<Ontology> findOntologiesByCreateTimeAfter(String name);

	    @Depth(value = 2)
	    Iterable<Ontology> findOntologiesByNameAndAndCreateTime(String name, String createTime); 
	
}
