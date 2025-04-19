package carservicecrm.repositories;

import carservicecrm.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    @Transactional
    @Query(value = "SELECT update_purchase_status_to_done(:id)", nativeQuery = true)
    void update_purchase_status_to_done(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT update_purchase_status_to_waiting(:id)", nativeQuery = true)
    void update_purchase_status_to_waiting(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT update_purchase_status_to_in_process(:id)", nativeQuery = true)
    void update_purchase_status_to_in_process(@Param("id") Long id);
}
