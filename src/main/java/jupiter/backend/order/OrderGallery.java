package jupiter.backend.order;

import java.util.Date;

public class OrderGallery {

    private String shopId;

    private String shopName;

    private String dishId;

    private String dishName;

    private Integer amount;

    public OrderGallery(String shopId, String shopName, String dishId, String dishName, Integer amount) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.dishId = dishId;
        this.dishName = dishName;
        this.amount = amount;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
