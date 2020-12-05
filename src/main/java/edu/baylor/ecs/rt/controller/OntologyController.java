package edu.baylor.ecs.rt.controller;

import edu.baylor.ecs.rt.model.nodes.Edge;
import edu.baylor.ecs.rt.model.nodes.Ontology;
import edu.baylor.ecs.rt.security.payload.request.OntologyRequest;
import edu.baylor.ecs.rt.Service.OntologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth/")
@RestController
public class OntologyController {

    @Autowired
    OntologyService ontologyService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Ontology> createNewOntology(@RequestBody Ontology ontology){
        Ontology newOntology = ontologyService.createOntology(ontology);
        return ResponseEntity.ok().body(newOntology);
    }

    @GetMapping("/ontology/findById")
    public Ontology findById(@RequestParam Long id){
        return ontologyService.findById(id);
    }

    @GetMapping("/findAll")
    public Iterable<Ontology> findAllOntology(){
        return ontologyService.findAllOntolology();
    }

    @PostMapping("/find")
    @ResponseBody
    public List<Edge> findOntology(@RequestBody OntologyRequest ontologyRequest){
        return ontologyService.findOntolology(ontologyRequest);
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<Ontology> updateOntology(@RequestBody Ontology ontology){
        Ontology updatedOntology = ontologyService.updateOntology(ontology);
        if(updatedOntology == null){
            return (ResponseEntity<Ontology>) ResponseEntity.notFound();
        }

        return ResponseEntity.ok().body(updatedOntology);
    }

    @GetMapping("/deleteById")
    public ResponseEntity deleteById(@RequestParam Long id){
        ontologyService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/ontology/populate")
    public ResponseEntity populate(){
        ontologyService.populate();
        return ResponseEntity.ok().body(null);
    }


}
