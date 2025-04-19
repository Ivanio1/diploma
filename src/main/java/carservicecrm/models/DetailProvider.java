package carservicecrm.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "detailprovider")
@Getter
@Setter
public class DetailProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, updatable = false)
    private String name;
    private String contact;

    @ManyToMany(mappedBy = "providers")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Detail> details = new HashSet<>();


    public void addDetail(Detail detail) {
        details.add(detail);
        detail.getProviders().add(this);
    }

    public void removeDetail(Detail detail) {
        details.remove(detail);
        detail.getProviders().remove(this);
    }

}
