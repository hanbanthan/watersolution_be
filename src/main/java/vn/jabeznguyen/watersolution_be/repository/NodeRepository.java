package vn.jabeznguyen.watersolution_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.jabeznguyen.watersolution_be.domain.Node;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
}
