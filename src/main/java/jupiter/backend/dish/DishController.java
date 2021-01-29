package jupiter.backend.dish;

import jupiter.backend.lsyexception.LsyException;
import jupiter.backend.response.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1")
@RestController
@CrossOrigin(origins = "*")
public class DishController {

    @Autowired
    DishService dishService;

    @PostMapping("/shops/{shopId}/dishes")
    public ResponseEntity<ResponseBody> createDish(@PathVariable("shopId") String shopId,
                                                   @RequestBody Dish newDish){
        try{
            Dish dish = dishService.createDish(shopId, newDish);
            ResponseBody responseBody =
                    new ResponseBody(dish, "create new dish under " + shopId, null);
            return ResponseEntity.ok(responseBody);
        }
        catch (LsyException e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/shops/{shopId}/dishes/{dishId}")
    public ResponseEntity<ResponseBody> retrieveDish(@PathVariable("shopId") String shopId,
                             @PathVariable("dishId") String dishId){
        try {
            Dish dish = dishService.retrieveDish(shopId, dishId);
            ResponseBody responseBody =
                    new ResponseBody(dish, "get dish", null);
            return ResponseEntity.ok(responseBody);
        }
        catch (LsyException e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), null);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), null);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/shops/{shopId}/dishes")
    public ResponseEntity<ResponseBody> listShop(@PathVariable("shopId") String shopId){
        try{
            List<Dish> dishes = dishService.listDishes(shopId);
            ResponseBody responseBody =
                    new ResponseBody(dishes, "get dishes list", null);
            return ResponseEntity.ok(responseBody);
        }
        catch(LsyException e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
        catch(Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @PostMapping("/shops/{shopId}/dishes/{dishId}")
    public ResponseEntity<ResponseBody> updateDish(@PathVariable("shopId") String shopId,
                                                   @PathVariable("dishId") String dishId,
                                                   @RequestBody Dish dish){
        dish.set_id(dishId);
        try{
            Dish newDish = dishService.updateDish(shopId, dish);
            ResponseBody responseBody =
                    new ResponseBody(newDish, "update it", null);
            return ResponseEntity.ok(responseBody);
        }
        catch(LsyException e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), null);
            return ResponseEntity.ok(responseBody);
        }
        catch(Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), null);
            return ResponseEntity.ok(responseBody);
        }

    }

}
