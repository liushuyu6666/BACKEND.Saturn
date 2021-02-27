package jupiter.backend.shop;

import jupiter.backend.address.Address;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Document(collection = "shop")
public class Shop {

    @Id
    private String id;

    @NotBlank
    @Size(max=50)
    private String shopName;

    @Size(max=100)
    private String desc;

    private String imgUrl;

    private Set<String> categories;

    // manual reference
    private String ownerId;

    private Address address;

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private Date modifiedAt;

    public Shop() {
    }

    public Shop(@NotBlank @Size(max = 50) String shopName,
                @Size(max = 100) String desc,
                String imgUrl,
                Set<String> categories,
                String ownerId,
                Address address) {
        this.shopName = shopName;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.categories = categories;
        this.ownerId = ownerId;
        this.address = address;
    }

    public Shop(String id,
                @NotBlank @Size(max = 50) String shopName,
                @Size(max = 100) String desc,
                String imgUrl,
                Set<String> categories,
                String ownerId,
                Address address) {
        this.id = id;
        this.shopName = shopName;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.categories = categories;
        this.ownerId = ownerId;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
