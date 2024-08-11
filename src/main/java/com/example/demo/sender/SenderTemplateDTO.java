package com.example.demo.sender;

import java.time.LocalDateTime;
import java.util.List;

public class SenderTemplateDTO {
    private Long id;
    private String name;
    private LocalDateTime lastSend;
    private LocalDateTime createdDate;
    private String creator;
    private String lastEditor;
    private LocalDateTime lastEdited;
    private List<Contact> contacts;
    private String contentSms;
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

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
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

    public void setContactNames(List<String> collect) {
    }
}
