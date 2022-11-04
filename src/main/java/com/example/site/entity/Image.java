package com.example.site.entity;
import javax.persistence.*;

@Entity
@Table(name = "IMAGES")
public class Image {
    public static final String TABLE_NAME="IMAGES";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="IMAGE_TITLE", length=50, nullable=false, unique=false)
    private String title;

    @Column(name="IMAGE_SRC", length=50, nullable=false, unique=false)
    private String src;




}
