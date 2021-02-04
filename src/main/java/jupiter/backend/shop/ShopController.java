package jupiter.backend.shop;

import jupiter.backend.address.Address;
import jupiter.backend.jwt.JWT;
import jupiter.backend.lsyexception.LsyException;
import jupiter.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jupiter.backend.response.ResponseBody;

import java.util.*;

@RequestMapping("/v1")
@RestController
@CrossOrigin(origins = "*")
public class ShopController {

    @Autowired
    ShopService shopService;

    @Autowired
    UserService userService;

    @Autowired
    JWT jwt;

    @PostMapping("/shops")
    public ResponseEntity<ResponseBody> createShop(@RequestBody Shop shopBody,
                                                   @RequestHeader String token){
        try{
            String loginName = jwt.isOwner(token);
            if(loginName != null){
                Shop newShop = shopService.createShop(shopBody, loginName);
                ResponseBody responseBody =
                        new ResponseBody(newShop, "new shop is created", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                        new ResponseBody(null, "only owner can create", null);
                return ResponseEntity.ok(responseBody);
            }
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
    public ResponseEntity<ResponseBody> retrieveShop(@PathVariable("Id") String shopId,
                                                     @RequestHeader("token") String token){
        try{
            if(jwt.isOwner(token) != null){
                String loginId = userService.find_Id(jwt.isOwner(token), "owner");
                Shop findShop = shopService.retrieveShop(shopId, loginId);
                ResponseBody responseBody =
                        new ResponseBody(findShop, "find it", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                Shop findShop = shopService.retrieveShop(shopId);
                ResponseBody responseBody =
                        new ResponseBody(findShop, "find it", null);
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

    @GetMapping("/shops")
    public ResponseEntity<ResponseBody> listShop(@RequestHeader("token") String token){
        try{
            if(jwt.isOwner(token) != null){
                String loginId = userService.find_Id(jwt.isOwner(token), "owner");
                List<Shop> findShop = shopService.listShop(loginId);
                ResponseBody responseBody =
                        new ResponseBody(findShop, "get list", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                List<Shop> shopList = shopService.listShop();
                ResponseBody responseBody =
                        new ResponseBody(shopList, "get list", null);
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

    @PostMapping("/shops/{Id}")
    public ResponseEntity<ResponseBody> updateShop(@PathVariable("Id") String shopId,
                                                   @RequestBody Shop newShop,
                                                   @RequestHeader("token") String token) {
        try{
            String loginName = jwt.isOwner(token);
            if(loginName != null){
                String loginId = userService.find_Id(loginName, "owner");
                Shop updatedShop = shopService.updateShop(shopId, loginId, newShop);
                ResponseBody responseBody =
                        new ResponseBody(updatedShop, "update it", null);
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody =
                    new ResponseBody(null, "only owner can update", null);
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

    @DeleteMapping("/shops/{Id}")
    public ResponseEntity<ResponseBody> deleteShop(@PathVariable("Id") String shopId,
                                                   @RequestHeader("token") String token){
        try{
            String loginName = jwt.isOwner(token);
            if(loginName != null){
                String ownerId = userService.find_Id(loginName, "owner");
                boolean deleteShop = shopService.deleteShop(shopId, ownerId);
                ResponseBody responseBody;
                if(deleteShop){
                    responseBody = new ResponseBody(true, "delete successfully", null);
                }
                else{
                    responseBody = new ResponseBody(false, "delete failure", null);
                }
                return ResponseEntity.ok(responseBody);
            }
            else{
                ResponseBody responseBody
                        = new ResponseBody(null, "only owner can delete", null);
                return ResponseEntity.ok(responseBody);
            }
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
