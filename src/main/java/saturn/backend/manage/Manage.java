package saturn.backend.manage;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "manage")
public class Manage {

    @Id
    private String id;

    private String tableName;

    private Integer amountOfEntries;

    public Manage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getAmountOfEntries() {
        return amountOfEntries;
    }

    public void setAmountOfEntries(Integer amountOfEntries) {
        this.amountOfEntries = amountOfEntries;
    }
}
