package testSystem.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject {
    private Integer id;

    private String name;

    private String description;

    private Set<Test> tests;

    private byte[] pic;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
        this.tests = new HashSet<>();
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

    @OneToMany(mappedBy = "subject")
    public Set<Test> getTests() {
        return this.tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "picture", nullable = false)
    public byte[] getPic() {
        return this.pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
}
