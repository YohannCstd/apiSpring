package com.example.site.entity;
import javax.persistence.*;

@Entity
@Table(name = "IMAGES")
public class Post {
    public static final String TABLE_NAME="POST";


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="IMAGE_TITLE", length=50, nullable=false, unique=false)
    private String title;

    @Column(name = "IMAGE_NOTE", length = 255, nullable = true, unique = false)
    private String note;

    @Column(name="IMAGE_SRC", length=50, nullable=false, unique=false)
    private String src;
    public Post(){}


    public Post(String title, String note, String src)
    {
        this.title = title;
        this.note = note;
        this.src = src;
    }

    public long getPostId() {
        return id;
    }
    public void setPostId(long id) {
        this.id = id;
    }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note;}

    public String getSrc() { return src;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return this.getPostId() + " " + this.getTitle() + " " + this.getNote() + " " + this.getSrc();
    }

}
