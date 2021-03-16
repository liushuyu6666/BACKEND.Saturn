package jupiter.backend.shop;

import jupiter.backend.core.AuthenticationService;
import jupiter.backend.dump.shop.DumpShop;
import jupiter.backend.dump.shop.DumpShopService;
import jupiter.backend.exception.FailToDeleteDocument;
import jupiter.backend.exception.IllegalFormat;
import jupiter.backend.exception.NoSuchDocument;
import jupiter.backend.exception.RedundantIssueException;
import jupiter.backend.payload.response.MessageResponse;
import jupiter.backend.role.ERole;
import jupiter.backend.role.Role;
import jupiter.backend.role.RoleRepository;
import jupiter.backend.security.userDetails.UserDetailsImpl;
import jupiter.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import jupiter.backend.payload.response.ResponseBody;

import java.util.*;

@RequestMapping("/v1/jupiter")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShopController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ShopService shopService;

    @Autowired
    DumpShopService dumpShopService;

    @PostMapping("/shops")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<?> createShop(
            Authentication authentication,
            @RequestBody Shop newShop){
        if(newShop.getShopName().trim().equals("")){
            return IllegalFormat.badRequest("shop's name can't be empty or whitespace");
        }
        if(shopService.existsByShopName(newShop.getShopName())){
            return RedundantIssueException.ok("shop's name existed");
        }
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        Shop savedShop = shopService.createShop(newShop, userId);
        ResponseBody responseBody
            = new ResponseBody(savedShop,
                "create shop successfully",
                null);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/shops/{id}")
    public ResponseEntity<?> retrieveShop(
            @PathVariable("id") String shopId
    ){
        Shop retrievedShop = shopService.retrieveShop(shopId).orElse(null);
        if(retrievedShop == null){
            return NoSuchDocument.ok("shop");
        }
        ResponseBody responseBody
                = new ResponseBody(retrievedShop,
                String.format("retrieve shop id: %s", shopId),
                null);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/manage/shops/{shopId}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<?> retrieveShopUnderOwner(
            Authentication authentication,
            @PathVariable("shopId") String shopId
    ){
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        Shop retrievedShop = shopService
                .retrieveShopUnderOwner(shopId, userId)
                .orElse(null);
        if(retrievedShop == null){
            return NoSuchDocument
                    .ok(String.format("no such shop under user: %s", userId));
        }
        ResponseBody responseBody
                = new ResponseBody(retrievedShop,
                String.format("retrieve shop id under user id: %s, %s", shopId, userId),
                null);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/shops")
    public ResponseEntity<?> listShop(){
        List<Shop> listShop = shopService.listShop();
        ResponseBody responseBody
                = new ResponseBody(listShop, "list shops", null);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/manage/shops")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<?> listShopUnderOwner(
            Authentication authentication
    ){
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        List<Shop> listShop = shopService.listShopUnderOwner(userId);
        ResponseBody responseBody
                = new ResponseBody(listShop,
                String.format("list shops under %s",userId),
                null);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/shops/{shopId}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<?> updateShop(
            Authentication authentication,
            @PathVariable("shopId") String shopId,
            @RequestBody Shop updatingShop
    ){
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        Shop targetShop = shopService.findShopByIdAndOwnerId(shopId, userId).orElse(null);
        if(targetShop == null){
            return NoSuchDocument.ok("no such shop under the owner");
        }
        if(updatingShop.getShopName().trim().equals("")){
            return NoSuchDocument.ok("new shop's name can't be empty or whitespace");
        }
        if(shopService.otherExistsByShopName(shopId, updatingShop.getShopName())){
            return RedundantIssueException.ok("new shop's name existed");
        }
        updatingShop.setId(shopId);
        Shop shop = shopService.updateShop(updatingShop, userId);
        ResponseBody responseBody = new ResponseBody(shop, "update it", null);
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("shops/{shopId}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<?> deleteShop(
            Authentication authentication,
            @PathVariable("shopId") String shopId
    ){
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        Shop targetShop = shopService.findShopByIdAndOwnerId(shopId, userId).orElse(null);
        if(targetShop == null){
            return NoSuchDocument.ok("no such shop under the owner");
        }
        if(shopService.deleteShop(shopId)){
            DumpShop savedDumpShop = dumpShopService.createDumpShop(targetShop);
            ResponseBody responseBody
                    = new ResponseBody(
                            savedDumpShop,
                    "delete successfully",
                    null);
            return ResponseEntity.ok(responseBody);
        }
        else return FailToDeleteDocument
                .ok(String
                        .format("userId: %s, targetShop: %s", userId, targetShop)
                );

    }
}
