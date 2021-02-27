package jupiter.backend.dump.dish;

import jupiter.backend.dish.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DumpDishService {

    @Autowired
    DumpDishRepository dumpDishRepository;

    public DumpDish createDumpDish(Dish deletingDish){
        DumpDish savingDish = new DumpDish(
                deletingDish.getShopId(),
                deletingDish.getOwnerId(),
                deletingDish.getName(),
                deletingDish.getImgUrl(),
                deletingDish.getDesc(),
                deletingDish.getCategories(),
                deletingDish.getTop3Reviews(),
                deletingDish.getPrice()
        );
        return dumpDishRepository.save(savingDish);
    }
}
