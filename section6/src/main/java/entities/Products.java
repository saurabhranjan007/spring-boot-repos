package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

//  Jpa entity annotation.. @Entity.
@Entity
public class Products implements Serializable {
//    Internally any cache framework will serialize our modals, entities and all to a file system (or in memory or to a database)
//    That's why we need to implement this "Serializable" interface for caching to work

    private static final long serialVersionUID = 1l;    // this is default serialization ID

//    Since these are auto increment fields in Database, so we need to mark it with @GeneratedValue.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    Here we're going to use some validator annotations provided by Spring Validation, to validate REST call
    @NotNull    // this will check and validate if the name passed in not null
    private String name;
    @Size(max = 100)    // this will check for the size of the description that shouldn't exceed the size of 100
    private String description;
    @Min(value = 1, message = "The price should be minimum 1")  // will check for the price & that is to be more than  1
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
