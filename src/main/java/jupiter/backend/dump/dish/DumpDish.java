package jupiter.backend.dump.dish;

import jupiter.backend.review.ShortReview;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(collection = "dumpDish")
public class DumpDish {

    @Id
    private String id;

    @NotBlank
    private String shopId;

    @NotBlank
    private String ownerId;

    @NotBlank
    @Size(max=50)
    private String name;

    private String imgUrl;

    @Size(max=100)
    private String desc;

    private Set<String> categories;

    private List<ShortReview> top3Reviews;

    private Float price;

    @CreatedDate
    private Date deleteAt;

    @LastModifiedDate
    private Date modifiedAt;

    public DumpDish() {
    }

    public DumpDish(
            @NotBlank String shopId,
            @NotBlank String ownerId,
            @NotBlank @Size(max = 50) String name,
            String imgUrl,
            @Size(max = 100) String desc,
            Set<String> categories,
            List<ShortReview> top3Reviews,
            Float price) {
        this.shopId = shopId;
        this.ownerId = ownerId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.desc = desc;
        this.categories = categories;
        this.top3Reviews = top3Reviews;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public List<ShortReview> getTop3Reviews() {
        return top3Reviews;
    }

    public void setTop3Reviews(List<ShortReview> top3Reviews) {
        this.top3Reviews = top3Reviews;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
