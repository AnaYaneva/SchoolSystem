package testSystem.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tags")
public class Tag {
    private Integer id;

    private String name;

    private Set<Test> tests;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
        this.tests =new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "tags")
    public Set<Test> getTests() {
        return this.tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }
}
