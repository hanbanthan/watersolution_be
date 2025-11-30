package vn.jabeznguyen.watersolution_be.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.jabeznguyen.watersolution_be.domain.Node;
import vn.jabeznguyen.watersolution_be.service.NodeService;
import vn.jabeznguyen.watersolution_be.util.annotation.ApiMessage;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NodeController {
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping("/nodes")
    public ResponseEntity<?> createNode(@Valid @RequestBody Node reqnode) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.nodeService.handleCreateNode(reqnode));
    }

    @GetMapping("/nodes")
    @ApiMessage("fetch nodes")
    public ResponseEntity<List<Node>> getNode() {
        List<Node> nodes = this.nodeService.handleGetNode();
        return ResponseEntity.ok(nodes);
    }

    @PutMapping("/nodes")
    public ResponseEntity<Node> updateNode(@Valid @RequestBody Node reqNode) {
        Node updatedNode = this.nodeService.handleUpdateNode(reqNode);
        return ResponseEntity.ok(updatedNode);
    }

    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") Long id) {
        this.nodeService.handleDeleteNode(id);
        return ResponseEntity.ok(null);
    }


}
