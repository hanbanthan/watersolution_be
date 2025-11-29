package vn.jabeznguyen.watersolution_be.service;

import org.springframework.stereotype.Service;
import vn.jabeznguyen.watersolution_be.domain.Node;
import vn.jabeznguyen.watersolution_be.repository.NodeRepository;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;

    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public Node handleCreateNode(Node node) {
        return this.nodeRepository.save(node);
    }
}
