package jupiter.backend.dump.shop;

import jupiter.backend.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DumpShopService {

    @Autowired
    DumpShopRepository dumpShopRepository;

    public DumpShop createDumpShop(Shop deletingShop){
        DumpShop savingShop = new DumpShop(
                deletingShop.getShopName(),
                deletingShop.getDesc(),
                deletingShop.getImgUrl(),
                deletingShop.getCategories(),
                deletingShop.getOwnerId(),
                deletingShop.getAddress()
        );
        return dumpShopRepository.save(savingShop);
    }
}
