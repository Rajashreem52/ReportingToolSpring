package edu.baylor.ecs.rt.controller;
import edu.baylor.ecs.rt.model.nodes.Edge;
import edu.baylor.ecs.rt.Service.EdgeService;
import edu.baylor.ecs.rt.Service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth/edge")
@RestController
public class EdgeController {
    @Autowired
    EdgeService edgeService;

    @Autowired
    NodeService nodeService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Edge> createNewEdge(@RequestBody Edge edge){
        return ResponseEntity.ok().body(edgeService.createEdge(edge));
    }

    @GetMapping("/findAll")
    public Iterable<Edge> findAllEdge(){
        return edgeService.findAllEdge();
    }

    @GetMapping("/findById")
    public Edge findById(@RequestParam Long id){
        return edgeService.findById(id);
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<Edge> updateEdge(@RequestBody Edge edge){
        Edge updatedEdge = edgeService.updateEdge(edge);
        if(updatedEdge == null){
            return (ResponseEntity<Edge>) ResponseEntity.notFound();
        }

        return ResponseEntity.ok().body(updatedEdge);
    }

    @GetMapping("/deleteById")
    public ResponseEntity deleteById(@RequestParam Long id){
        edgeService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }


}
