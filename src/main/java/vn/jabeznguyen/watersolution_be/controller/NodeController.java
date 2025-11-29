package vn.jabeznguyen.watersolution_be.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.jabeznguyen.watersolution_be.domain.Node;
import vn.jabeznguyen.watersolution_be.service.NodeService;

import java.util.List;

@RestController
public class NodeController {
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping("/nodes")
    public ResponseEntity<?> createNode(@Valid @RequestBody Node reqnode) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.nodeService.handleCreateNode(reqnode));
    }


}
