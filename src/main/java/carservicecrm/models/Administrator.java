package carservicecrm.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "administrator")
@Getter
@Setter
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Employee employee;

    @OneToMany(mappedBy = "administrator", cascade = {CascadeType.PERSIST})
    private Set<Purchase> purchases;

    @PreRemove
    private void preRemove() {
        purchases.forEach(child -> child.setAdministrator(null));
    }

}
