package jupiter.backend.shop;


import jupiter.backend.address.Address;
import jupiter.backend.dish.Dish;
import jupiter.backend.lsyexception.LsyException;
import jupiter.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopService {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    UserRepository userRepository;

    public Shop createShop(Shop newShop, String loginName) throws LsyException{
        Shop Shop = shopRepository.findShopByName(newShop.getName());
        if(Shop != null){
            throw new LsyException("shop name existed");
        }
        try{
            // convert all username into id
            Set<String> userIdSet = new HashSet<>();
            newShop.getOwners().add(loginName);
            for(String username: newShop.getOwners()){
                String convertId = userRepository.findUserByUsernameAndRole(username, "owner").getId();
                if (convertId != null) userIdSet.add(convertId);
            }
            Shop savingShop = new Shop();
            savingShop.setName(newShop.getName());
            savingShop.setDesc(newShop.getDesc());
            savingShop.setImgUrl(newShop.getImgUrl());
            savingShop.setCategories(newShop.getCategories());
            savingShop.setAddress(newShop.getAddress());
            savingShop.setDishes(newShop.getDishes());
            savingShop.setOwners(new ArrayList<>(userIdSet));
            return shopRepository.save(savingShop);
        }
        catch(Exception e){
            throw e;
        }
    }

    public Shop retrieveShop(String shopId) throws LsyException{
        Shop shop = shopRepository.findBy_id(shopId);
        if(shop == null){
            throw new LsyException("no such shop");
        }
        try{
            List<String> ownersName = new ArrayList<>();
            for(String ownersId: shop.getOwners()){
                ownersName.add(userRepository.findBy_IdSafely(ownersId).getUsername());
            }
            shop.setOwners(ownersName);
            return shop;
        }
        catch (Exception e){
            throw e;
        }
    }

    public Shop retrieveShop(String shopId, String ownerId) throws LsyException{
        Shop shop = shopRepository.findBy_idAndOwnerId(shopId, ownerId);
        if(shop == null){
            throw new LsyException("no such shop under this login owner");
        }
        try{
            List<String> ownersName = new ArrayList<>();
            for(String ownersId: shop.getOwners()){
                ownersName.add(userRepository.findBy_IdSafely(ownersId).getUsername());
            }
            shop.setOwners(ownersName);
            return shop;
        }
        catch (Exception e){
            throw e;
        }
    }

    public List<Shop> listShop() throws Exception{
        try{
            List<Shop> shoplist = shopRepository.findAll();
            for(Shop shop : shoplist){
                List<String> ownersName = new ArrayList<>();
                for(String ownersId: shop.getOwners()){
                    ownersName.add(userRepository.findBy_IdSafely(ownersId).getUsername());
                }
                shop.setOwners(ownersName);
            }
            return shoplist;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Shop> listShop(String ownerId) throws Exception{
        try{
            List<Shop> shoplist = shopRepository.findAllByOwner(ownerId);
            for(Shop shop : shoplist){
                List<String> ownersName = new ArrayList<>();
                for(String ownersId: shop.getOwners()){
                    ownersName.add(userRepository.findBy_IdSafely(ownersId).getUsername());
                }
                shop.setOwners(ownersName);
            }
            return shoplist;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public Shop updateShop(String shopId, String ownerId, Shop updateShop) throws LsyException{
        Shop newShop = shopRepository.findBy_idAndOwnerId(shopId, ownerId);
        if(newShop == null){
            throw new LsyException("no such shop under the login owner");
        }
        else if(shopRepository.findShopByName(updateShop.getName()) != null
        && !shopRepository.findShopByName(updateShop.getName()).get_id().equals(shopId)){
            throw new LsyException("new name existed");
        }
        try{
            // convert all username into id
            Set<String> userIdSet = new HashSet<>();
            for(String username: updateShop.getOwners()){
                String convertId = userRepository.findUserByUsernameAndRole(username, "owner").getId();
                if (convertId != null) userIdSet.add(convertId);
            }
            newShop.setName(updateShop.getName());
            newShop.setDesc(updateShop.getDesc());
            newShop.setImgUrl(updateShop.getImgUrl());
            newShop.setCategories(updateShop.getCategories());
            newShop.setOwners(new ArrayList<>(userIdSet));
            newShop.setAddress(updateShop.getAddress());
            return shopRepository.save(newShop);
        }
        catch (Exception e){
            throw e;
        }
    }

    public Boolean deleteShop(String shopId, String ownerId) throws Exception{
        Shop shop = shopRepository.findBy_idAndOwnerId(shopId, ownerId);
        if(shop == null){
            throw new LsyException("no such shop under the login owner");
        }
        try{
            shopRepository.deleteById(shopId);
            return shopRepository.findShopByName(shopId) == null;
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }

}
