package jupiter.backend.shop;

import jupiter.backend.core.CoreService;
import jupiter.backend.exception.NoExistsEntityFromIdException;
import jupiter.backend.user.User;
import jupiter.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class ShopService extends CoreService{

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CoreService coreService;

    @Value("${aws.S3.properties.endpointUrl}")
    private String endpointUrl;

    @Value("${aws.S3.properties.bucketName}")
    private String bucketName;

    public Shop createShop(Shop newShop, String loginUserId){
        Shop savingShop = new Shop(
                newShop.getShopName(),
                newShop.getDesc(),
                null,
                newShop.getCategories(),
                loginUserId,
                newShop.getAddress()
        );
        Shop savedShop = shopRepository.save(savingShop);
        savingShop.setImgUrl(endpointUrl + "/" + bucketName + "/" + savedShop.getId());
        return shopRepository.save(savingShop);
    }

    public Optional<Shop> retrieveShop(String shopId){
        return shopRepository.findShopById(shopId);
    }

    public Optional<Shop> retrieveShopUnderOwner(String shopId,
                                                 String ownerId){
        return shopRepository.findShopByIdAndOwnerId(shopId, ownerId);
    }

    public List<Shop> listShop(){
        return shopRepository.findAll();
    }

    public List<Shop> listShopUnderOwner(String ownerId){
        return shopRepository.findShopsByOwnerId(ownerId);
    }

    public Shop updateShop(Shop updatingShop, String loginUserId){
        Shop updatedShop = shopRepository.findShopById(updatingShop.getId()).orElse(null);
        assert updatedShop != null;
        updatedShop.setShopName(updatingShop.getShopName());
        updatedShop.setDesc(updatingShop.getDesc());
//        updatedShop.setImgUrl(updatingShop.getImgUrl());
        updatedShop.setCategories(updatingShop.getCategories());
//        updatedShop.setOwnerId(loginUserId);
        updatedShop.setAddress(updatingShop.getAddress());

        return shopRepository.save(updatedShop);
    }


    public Boolean existsByShopName(String shopName){
        return shopRepository.existsByShopName(shopName);
    }

    public Boolean existsById(String shopId){
        return shopRepository.existsById(shopId);
    }

    public Boolean otherExistsByShopName(String shopId, String shopName){
        Shop foundShopById = shopRepository.findShopById(shopId).orElse(null);
        Shop foundShopByShopName
                = shopRepository.findShopByShopName(shopName).orElse(null);
        if(foundShopById == null && foundShopByShopName != null){
            return true;
        }
        else if(foundShopByShopName != null){
            return !foundShopById.getId().equals(foundShopByShopName.getId());
        }
        else return false;
    }

    public Optional<Shop> findShopByIdAndOwnerId(String shopId, String ownerId){
        return shopRepository.findShopByIdAndOwnerId(shopId, ownerId);
    }

    public Boolean deleteShop(String shopId){
        shopRepository.deleteById(shopId);
        return !shopRepository.existsById(shopId);
    }

    public Boolean existsByIdAndOwnerId(String shopId, String ownerId){
        return shopRepository.existsByIdAndOwnerId(shopId, ownerId);
    }
}
