package pl.iledasz.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Advertisement {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private String item;

    public Advertisement() {
    }

    public Advertisement(Long id, String title, String description, String item) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
