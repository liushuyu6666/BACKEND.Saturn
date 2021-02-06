package jupiter.backend.dish;

import com.amazonaws.services.dynamodbv2.xspec.L;
import jupiter.backend.lsyexception.LsyException;
import jupiter.backend.shop.Shop;
import jupiter.backend.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    @Autowired
    DishRepository dishRepository;

    @Autowired
    ShopRepository shopRepository;

    public Dish createDish(String shopId, String ownerId, Dish newDish) throws LsyException{
        Shop shop = shopRepository.findBy_idAndOwnerId(shopId, ownerId);
        Shop testDish = shopRepository.findShopBy_idAndDishName(shopId, newDish.getName());
        if(shop == null){
            throw new LsyException("no such shop under this owner");
        }
        if(testDish != null){
            throw new LsyException("dish name existed");
        }
        try{
            Dish dish = new Dish();
            dish.set_id();
            dish.setName(newDish.getName());
            dish.setImgUrl(newDish.getImgUrl());
            dish.setDesc(newDish.getDesc());
            dish.setCategory(newDish.getCategory());
            dish.setPrice(newDish.getPrice());
            dish.setReview(new ArrayList<>());
            dish.setModifiedAt();
            dishRepository.createDish(shopId, dish);
            return dish;
        }
        catch(Exception e){
            throw e;
        }
    }

//    public Dish createDish(String shopId, Dish newDish) throws LsyException{
//        Shop shop = shopRepository.findBy_id(shopId);
//        Shop testDish = shopRepository.findShopBy_idAndDishName(shopId, newDish.getName());
//        if(shop == null){
//            throw new LsyException("no such shop");
//        }
//        if(testDish != null){
//            throw new LsyException("dish name existed");
//        }
//        try{
//            Dish dish = new Dish();
//            dish.set_id();
//            dish.setName(newDish.getName());
//            dish.setImgUrl(newDish.getImgUrl());
//            dish.setDesc(newDish.getDesc());
//            dish.setCategory(newDish.getCategory());
//            dish.setPrice(newDish.getPrice());
//            dish.setReview(newDish.getReview());
//            dish.setModifiedAt();
//            shop.getDishes().add(dish);
//            shopRepository.save(shop);
//            return dish;
//        }
//        catch(Exception e){
//            throw e;
//        }
//    }

    public Dish retrieveDish(String shopId, String dishId) throws LsyException{
        Shop findShop = shopRepository.findShopBy_idAndDish_id(shopId, dishId);
        if(findShop == null){
            throw new LsyException("no such shop or dish");
        }
        try{
            Dish findDish = findShop
                    .getDishes()
                    .stream()
                    .filter(dish -> dish
                            .get_id()
                            .equals(dishId))
                    .findFirst()
                    .orElse(null);
            return findDish;
        }
        catch (Exception e){
            throw e;
        }
    }

    public Dish retrieveDish(String shopId, String dishId, String ownerId) throws LsyException{
        Shop findShop = shopRepository.findShopBy_idDish_idAndOwnerId(shopId, dishId, ownerId);
        if(findShop == null){
            throw new LsyException("no such shop or dish under the login owner");
        }
        try{
            Dish findDish = findShop
                    .getDishes()
                    .stream()
                    .filter(dish -> dish
                            .get_id()
                            .equals(dishId))
                    .findFirst()
                    .orElse(null);
            return findDish;
        }
        catch (Exception e){
            throw e;
        }
    }

    public List<Dish> listDishes(String shopId) throws LsyException{
        Shop shop = shopRepository.findBy_id(shopId);
        if(shop == null){
            throw new LsyException("no such shop");
        }
        try{
            List<Dish> dishes = shop.getDishes();
            return dishes;
        }
        catch (Exception e){
            throw e;
        }
    }

    public List<Dish> listDishes(String shopId, String ownerId) throws LsyException{
        Shop shop = shopRepository.findBy_idAndOwnerId(shopId, ownerId);
        if(shop == null){
            throw new LsyException("no such shop under this login owner");
        }
        try{
            List<Dish> dishes = shop.getDishes();
            return dishes;
        }
        catch (Exception e){
            throw e;
        }
    }

    public Dish updateDish(String shopId, String ownerId, Dish dish) throws LsyException{
        Shop shop = shopRepository.findShopBy_idDish_idAndOwnerId(shopId, dish.get_id(), ownerId);
        Dish duplicateNameDish = dishRepository.findDishByName(shopId, dish.getName());
        if(shop != null){
            try{
                if(duplicateNameDish == null ||
                        duplicateNameDish.get_id().equals(dish.get_id())){
                    return dishRepository.updateDish(shopId, dish);
                }
                else{
                    throw new LsyException("new dish name existed in this shop");
                }
            }
            catch(Exception e){
                throw e;
            }
        }
        else{
            throw new LsyException("no such shop or dish under this login owner");
        }
    }

    public boolean deleteDish(String shopId, String dishId, String ownerId) throws LsyException{
        Shop shop = shopRepository.findShopBy_idDish_idAndOwnerId(shopId, dishId, ownerId);
        if(shop == null){
            throw new LsyException("no such shop or dish under this login owner");
        }
        else{
            try{
                dishRepository.deleteDish(shopId, dishId);
                return true;
            }
            catch(Exception e){
                throw e;
            }
        }
    }

}
