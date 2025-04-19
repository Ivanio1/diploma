package carservicecrm.services;

import carservicecrm.models.Worker;
import carservicecrm.repositories.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerRepository workerRepository;

    public boolean saveWorker(Worker worker) {
        try {
            workerRepository.save(worker);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Worker> list() {
        return workerRepository.findAll();
    }

    public Optional<Worker> getWorker(Long workerId) {
        return workerRepository.findById(workerId);
    }

    public void update_purchase_status_to_done(Long id) {
        workerRepository.update_purchase_status_to_done(id);
    }

    public void update_purchase_status_to_waiting(Long id) {
        workerRepository.update_purchase_status_to_waiting(id);
    }

    public void update_purchase_status_to_in_process(Long id) {
        workerRepository.update_purchase_status_to_in_process(id);
    }

}

