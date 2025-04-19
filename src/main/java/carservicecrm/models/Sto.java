package carservicecrm.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sto")
@Getter
@Setter
public class Sto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, updatable = false)
    private String name;
    private String phone;

    @ManyToMany(mappedBy = "stoes")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Employee> employees = new HashSet<>();


    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.getStoes().add(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.getStoes().remove(this);
    }

}
