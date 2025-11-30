package vn.jabeznguyen.watersolution_be.service;

import org.springframework.stereotype.Service;
import vn.jabeznguyen.watersolution_be.domain.Node;
import vn.jabeznguyen.watersolution_be.repository.NodeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;

    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public Node handleCreateNode(Node node) {
        return this.nodeRepository.save(node);
    }

    public List<Node> handleGetNode() {
        return this.nodeRepository.findAll();
    }

    public Node handleUpdateNode(Node c) {
        Optional<Node> nodeOptional = this.nodeRepository.findById(c.getId());
        if (nodeOptional.isPresent()) {
            Node currentNode = nodeOptional.get();
            currentNode.setStatus(c.getStatus());
            currentNode.setDescription(c.getDescription());
            return this.nodeRepository.save(currentNode);
        }
        return null;
    }

    public void handleDeleteNode(Long id) {
        this.nodeRepository.deleteById(id);
    }
}
