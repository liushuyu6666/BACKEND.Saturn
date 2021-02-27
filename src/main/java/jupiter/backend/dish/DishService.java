package jupiter.backend.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    @Autowired
    DishRepository dishRepository;

    public Dish createDish(Dish newDish, String ownerId){
        Dish savingDish = new Dish(
                newDish.getShopId(),
                ownerId,
                newDish.getName(),
                newDish.getImgUrl(),
                newDish.getDesc(),
                newDish.getCategories(),
                newDish.getPrice()
        );
        return dishRepository.save(savingDish);
    }

    public Boolean existsById(String dishId){
        return dishRepository.existsById(dishId);
    }

    public Boolean existsByShopIdAndName(String shopId, String dishName){
        return  dishRepository.existsByShopIdAndName(shopId, dishName);
    }

    public Boolean existsByIdAndShopId(String dishId, String shopId){
        return dishRepository.existsByIdAndShopId(dishId, shopId);
    }

    public Dish findByShopIdAndId(String shopId, String dishId){
        return dishRepository.findByShopIdAndId(shopId, dishId).orElse(null);
    }

    public List<Dish> listDishes(String shopId){
        return dishRepository.findByShopId(shopId);
    }

    public Optional<Dish> findById(String dishId){
        return dishRepository.findById(dishId);
    }

    public Boolean existsByIdAndShopIdAndOwnerId(String dishId, String shopId, String ownerId){
        return dishRepository.existsByIdAndShopIdAndOwnerId(dishId, shopId, ownerId);
    }

    public Boolean otherExistsByDishName(String shopId, String dishId, String dishName){
        Dish foundDishById = dishRepository.findByShopIdAndId(shopId, dishId).orElse(null);
        Dish foundDishByName = dishRepository.findByShopIdAndName(shopId, dishName).orElse(null);
        if(foundDishById == null && foundDishByName != null){
            return true;
        }
        else if(foundDishByName != null){
            return !foundDishById.getId().equals(foundDishByName.getId());
        }
        else return false;
    }

    public Dish updateDish(Dish updatingDish){
        Dish updatedDish = dishRepository.findById(updatingDish.getId()).orElse(null);
        updatedDish.setName(updatingDish.getName());
        updatedDish.setImgUrl(updatingDish.getImgUrl());
        updatedDish.setDesc(updatingDish.getDesc());
        updatedDish.setCategories(updatingDish.getCategories());
        updatedDish.setPrice(updatingDish.getPrice());

        return dishRepository.save(updatedDish);
    }

    public Optional<Dish> findByIdAndOwnerId(String id, String ownerId){
        return dishRepository.findByIdAndOwnerId(id, ownerId);
    }

    public Boolean deleteDish(String dishId){
        dishRepository.deleteById(dishId);
        return !dishRepository.existsById(dishId);
    }

}
