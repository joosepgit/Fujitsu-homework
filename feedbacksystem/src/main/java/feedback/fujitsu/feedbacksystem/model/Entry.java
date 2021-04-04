package feedback.fujitsu.feedbacksystem.model;


import javax.persistence.*;
import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "ENTRY")
public class Entry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "text")
    private String text;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "ENTRY_CATEGORY",
        joinColumns = { @JoinColumn(name = "ENTRY_ID")},
        inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID")})
    private Set<Category> categories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category){
        this.getCategories().add(category);
        category.getEntries().add(this);
    }

    public void removeCategory(Category category){
        this.getCategories().remove(category);
        category.getEntries().remove(this);
    }

    public void removeCategories() {
        for (Category category : new HashSet<>(categories)){
            removeCategory(category);
        }
    }
}
