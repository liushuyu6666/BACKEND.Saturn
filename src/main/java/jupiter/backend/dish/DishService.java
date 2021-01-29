package jupiter.backend.dish;

import com.amazonaws.services.dynamodbv2.xspec.L;
import jupiter.backend.lsyexception.LsyException;
import jupiter.backend.shop.Shop;
import jupiter.backend.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DishService {

    @Autowired
    DishRepository dishRepository;

    @Autowired
    ShopRepository shopRepository;

    public Dish createDish(String shopId, Dish newDish) throws LsyException{
        Shop shop = shopRepository.findBy_id(shopId);
        Shop testDish = shopRepository.findShopBy_idAndDishName(shopId, newDish.getName());
        if(shop == null){
            throw new LsyException("no such shop");
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
            dish.setReview(newDish.getReview());
            dish.setModifiedAt();
            shop.getDishes().add(dish);
            shopRepository.save(shop);
            return dish;
        }
        catch(Exception e){
            throw e;
        }
    }

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

    public Dish updateDish(String shopId, Dish dish) throws LsyException{
        Shop shop = shopRepository.findShopBy_idAndDishName(shopId, dish.getName());
        if(shop != null){
            throw new LsyException("new dish name existed");
        }
        else{
            try{
                Dish updatedDist = dishRepository.updateDish(shopId, dish);
                return updatedDist;
            }
            catch (Exception e){
                throw e;
            }
        }
    }

    
}
