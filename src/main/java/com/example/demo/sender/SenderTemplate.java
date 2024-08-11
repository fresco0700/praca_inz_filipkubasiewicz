package com.example.demo.sender;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class SenderTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private LocalDateTime lastSend;
    private LocalDateTime createdDate;
    private String creator;
    private String lastEditor;
    private LocalDateTime lastEdited;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "sender_template_contact",
            joinColumns = @JoinColumn(name = "sender_template_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<Contact> contacts;
    @Column(columnDefinition = "TEXT")
    private String contentSms;
    @Column(columnDefinition = "TEXT")
    private String contentMail;

    public String getContentSms() {
        return contentSms;
    }

    public void setContentSms(String contentSms) {
        this.contentSms = contentSms;
    }

    public String getContentMail() {
        return contentMail;
    }

    public void setContentMail(String contentMail) {
        this.contentMail = contentMail;
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastEditor() {
        return lastEditor;
    }

    public void setLastEditor(String lastEditor) {
        this.lastEditor = lastEditor;
    }

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

    public LocalDateTime getLastSend() {
        return lastSend;
    }

    public void setLastSend(LocalDateTime lastSend) {
        this.lastSend = lastSend;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
