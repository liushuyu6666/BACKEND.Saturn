package jupiter.backend.shop;

import jupiter.backend.address.Address;
import jupiter.backend.lsyexception.LsyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jupiter.backend.response.ResponseBody;

import java.util.List;

@RequestMapping("/v1")
@RestController
@CrossOrigin(origins = "*")
public class ShopController {

    @Autowired
    ShopService shopService;

    @PostMapping("/shops")
    public ResponseEntity<ResponseBody> createShop(@RequestBody Shop shopBody){
        try{
            Shop newShop = shopService.createShop(shopBody);
            ResponseBody responseBody =
                    new ResponseBody(newShop, "new shop is created", null);
            return ResponseEntity.ok(responseBody);
        }
        catch (LsyException e){
            ResponseBody responseBody = new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            ResponseBody responseBody = new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/shops/{Id}")
    public ResponseEntity<ResponseBody> retrieveShop(@PathVariable("Id") String shopId){
        try{
            Shop findShop = shopService.retrieveShop(shopId);
            ResponseBody responseBody =
                    new ResponseBody(findShop, "find it", null);
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

    @GetMapping("/shops")
    public ResponseEntity<ResponseBody> listShop(){
        try{
            List<Shop> shopList = shopService.listShop();
            ResponseBody responseBody =
                    new ResponseBody(shopList, "get list", null);
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

    @PostMapping("/shops/{Id}")
    public ResponseEntity<ResponseBody> updateShop(@PathVariable("Id") String shopId,
                                                   @RequestBody Shop newShop) {
        try{
            Shop updatedShop = shopService.updateShop(shopId, newShop);
            ResponseBody responseBody =
                    new ResponseBody(updatedShop, "update it", null);
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

    @DeleteMapping("/shops/{Id}")
    public ResponseEntity<ResponseBody> deleteShop(@PathVariable("Id") String shopId){
        try{
            boolean deleteShop = shopService.deleteShop(shopId);
            ResponseBody responseBody;
            if(deleteShop){
                responseBody = new ResponseBody(true, "delete successfully", null);
            }
            else{
                responseBody = new ResponseBody(false, "delete failure", null);
            }
            return ResponseEntity.ok(responseBody);
        }
        catch (LsyException e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
        catch(Exception e){
            ResponseBody responseBody = new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }
}
