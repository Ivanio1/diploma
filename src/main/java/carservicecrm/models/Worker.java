package carservicecrm.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "worker")
@Getter
@Setter
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String specialization;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Employee employee;

    @OneToMany(mappedBy = "worker", cascade = {CascadeType.PERSIST})
    private Set<Purchase> purchases;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<WorkerRequest> workerRequests;

    @PreRemove
    private void preRemove() {
        purchases.forEach(child -> child.setWorker(null));
    }

}



