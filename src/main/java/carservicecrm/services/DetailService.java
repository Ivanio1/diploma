package carservicecrm.services;

import carservicecrm.models.Detail;
import carservicecrm.models.DetailProvider;
import carservicecrm.repositories.DetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetailService {

    private final DetailRepository detailRepository;


    public boolean saveDetail(Detail detail, DetailProvider provider) {
        try {
            Set<Detail> details = detailRepository.findAllByName(detail.getName());
            if (details.isEmpty()) {
                detailRepository.save(detail);
            } else {
                for (Detail d : details) {
                    if (provider.getDetails().contains(d)) {
                        d.setStock(detail.getStock() + d.getStock());
                        d.setPrice(detail.getPrice());
                        detailRepository.save(d);
                        return true;
                    }
                }
                detailRepository.save(detail);
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public Detail getDetailById(Long id) {
        return detailRepository.findDetailById(id);
    }

    public Detail getDetailByStorageStock(Integer id) {
        return detailRepository.findDetailByStoragestock(id);
    }

    public Detail getDetailByName(String name) {
        return detailRepository.findByName(name);
    }

    public List<Detail> list() {
        return detailRepository.findAll();
    }

    public List<Detail> listStorage() {
        return detailRepository.findAllInStorage();
    }

    public List<Detail> listStorageIfBigger(Integer num) {
        List<Detail> details = detailRepository.findAll();
        List<Detail> final_details = new ArrayList<>();
        for (Detail d : details) {
            if (detailRepository.isBiggerThanNum(d.getId(), num)) {
                final_details.add(d);
            }

        }
        return final_details;
    }

    public void updateStorageStock(Detail detail, Integer number) {
        detail.setStock(nullOrZero(detail.getStock()) - number);
        detail.setStoragestock(nullOrZero(detail.getStoragestock()) + number);
        detailRepository.save(detail);
        //detailRepository.updateStorageStock(id, number);
    }

    public void updateStorageStockByName(String name, Integer number) {
        detailRepository.updateStorageStockbyname(name, number);
    }

    private Integer nullOrZero(Integer number){
        if (number == null) return 0;
        else return number;
    }
}
