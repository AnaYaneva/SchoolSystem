package testSystem.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tests")
public class Test {
    private Integer id;

    private String title;

    private String content;

    private User author;

    private Subject subject;

    private Set<Tag> tags;

    public Test() {
    }

    public Test(String title, String content, User author, Subject subject, HashSet<Tag> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.subject = subject;
        this.tags=tags;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name="subjectId")
    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @ManyToMany()
    @JoinColumn(table="articles_tags")
    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Transient
    public String getSummary(){
        return this.getContent().substring(0, this.getContent().length() / 2) + "...";
    }
}
