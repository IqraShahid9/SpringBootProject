package com.example.demo.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_sequence")
    @SequenceGenerator(name = "document_sequence", sequenceName = "document_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(length = 512, nullable = false)
    private String name;

    private Long size;

    private Date uploadTime;

    @Lob
    private byte[] content;

    public Document(Long id, String name, Long size, byte[] content) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.content = content;
    }

    public Document(String name, Long size, byte[] content) {
        this.name = name;
        this.size = size;
        this.content = content;
    }

    public Document() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
