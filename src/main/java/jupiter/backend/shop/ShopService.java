package jupiter.backend.shop;


import jupiter.backend.address.Address;
import jupiter.backend.dish.Dish;
import jupiter.backend.lsyexception.LsyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    ShopRepository shopRepository;

    public Shop createShop(Shop newShop) throws Exception{
        Shop Shop = shopRepository.findShopByName(newShop.getName());
        if(Shop != null){
            throw new LsyException("shop name existed");
        }
        try{
            Shop savingShop = new Shop();
            savingShop.setName(newShop.getName());
            savingShop.setDesc(newShop.getDesc());
            savingShop.setImgUrl(newShop.getImgUrl());
            savingShop.setCategories(newShop.getCategories());
            savingShop.setAddress(newShop.getAddress());
            savingShop.setDishes(newShop.getDishes());
            savingShop.setOwners(newShop.getOwners());
            return shopRepository.save(savingShop);
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }

    public Shop retrieveShop(String shopId) throws LsyException{
        Shop shop = shopRepository.findBy_id(shopId);
        if(shop == null){
            throw new LsyException("no such shop");
        }
        try{
            return shop;
        }
        catch (Exception e){
            throw e;
        }
    }

    public List<Shop> listShop() throws Exception{
        try{
            return shopRepository.findAll();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public Shop updateShop(String shopId, Shop updateShop) throws LsyException{
        Shop newShop = shopRepository.findBy_id(shopId);
        if(newShop == null){
            throw new LsyException("no such shop");
        }
        else if(shopRepository.findShopByName(updateShop.getName()) != null){
            throw new LsyException("new name existed");
        }
        try{
            newShop.setName(updateShop.getName());
            newShop.setDesc(updateShop.getDesc());
            newShop.setImgUrl(updateShop.getImgUrl());
            newShop.setCategories(updateShop.getCategories());
            newShop.setOwners(updateShop.getOwners());
            newShop.setAddress(updateShop.getAddress());
            return shopRepository.save(newShop);
        }
        catch (Exception e){
            throw e;
        }
    }

    public Boolean deleteShop(String shopId) throws Exception{
        Shop shop = shopRepository.findBy_id(shopId);
        if(shop == null){
            throw new LsyException("no such shop");
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
