package vn.jabeznguyen.watersolution_be.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "edges")
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
