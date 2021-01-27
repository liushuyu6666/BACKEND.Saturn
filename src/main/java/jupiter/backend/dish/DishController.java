package jupiter.backend.dish;

import jupiter.backend.lsyexception.LsyException;
import jupiter.backend.response.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
