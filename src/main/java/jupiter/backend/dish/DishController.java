package jupiter.backend.dish;

import jupiter.backend.jwt.JWT;
import jupiter.backend.lsyexception.LsyException;
import jupiter.backend.response.ResponseBody;
import jupiter.backend.user.UserService;
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

    @Autowired
    UserService userService;

    @Autowired
    JWT jwt;

    @PostMapping("/shops/{shopId}/dishes")
    public ResponseEntity<ResponseBody> createDish(@PathVariable("shopId") String shopId,
                                                   @RequestBody Dish newDish,
                                                   @RequestHeader("token") String token){
        try{
            String loginName = jwt.isOwner(token);
            if(loginName != null){
                String ownerId = userService.find_Id(loginName, "owner");
                Dish dish = dishService.createDish(shopId, ownerId, newDish);
                ResponseBody responseBody =
                        new ResponseBody(dish, "create new dish under " + shopId, null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only owner can create dish", null);
                return ResponseEntity.ok(responseBody);
            }

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
                                                     @PathVariable("dishId") String dishId,
                                                     @RequestHeader("token") String token){
        try {
            String loginName = jwt.isOwner(token);
            if(loginName != null) {
                String ownerId = userService.find_Id(loginName, "owner");
                Dish dish = dishService.retrieveDish(shopId, dishId, ownerId);
                ResponseBody responseBody =
                        new ResponseBody(dish, "get dish", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                Dish dish = dishService.retrieveDish(shopId, dishId);
                ResponseBody responseBody =
                        new ResponseBody(dish, "get dish", null);
                return ResponseEntity.ok(responseBody);
            }
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
    public ResponseEntity<ResponseBody> listShop(@PathVariable("shopId") String shopId,
                                                 @RequestHeader("token") String token){
        try{
            String loginName = jwt.isOwner(token);
            if(loginName != null) {
                String ownerId = userService.find_Id(loginName, "owner");
                List<Dish> dishes = dishService.listDishes(shopId, ownerId);
                ResponseBody responseBody =
                        new ResponseBody(dishes, "get dishes list", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                List<Dish> dishes = dishService.listDishes(shopId);
                ResponseBody responseBody =
                        new ResponseBody(dishes, "get dishes list", null);
                return ResponseEntity.ok(responseBody);
            }
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
                                                   @RequestBody Dish dish,
                                                   @RequestHeader("token") String token){
        dish.set_id(dishId);
        try{
            String loginName = jwt.isOwner(token);
            if(loginName != null) {
                String ownerId = userService.find_Id(loginName, "owner");
                Dish newDish = dishService.updateDish(shopId, ownerId, dish);
                ResponseBody responseBody =
                        new ResponseBody(newDish, "update it", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only owner can update", null);
                return ResponseEntity.ok(responseBody);
            }
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

    @DeleteMapping("/shops/{shopId}/dishes/{dishId}")
    public ResponseEntity<ResponseBody> deleteDish(@PathVariable("shopId") String shopId,
                                                   @PathVariable("dishId") String dishId,
                                                   @RequestHeader("token") String token){
        try{
            String loginName = jwt.isOwner(token);
            if(loginName != null) {
                String ownerId = userService.find_Id(loginName, "owner");
                boolean isDelete = dishService.deleteDish(shopId, dishId, ownerId);
                ResponseBody responseBody =
                        new ResponseBody(isDelete, "delete it", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only owner can delete it", null);
                return ResponseEntity.ok(responseBody);
            }
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

}
