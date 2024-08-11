package com.example.demo.ShiftRaport;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Table
public class ShiftPost extends Post{
    public static int maxTextChar = 1_500_000;
    private String autor;
    private String title;
    private String dayornight;
    private String state;
    private String editedBy;
    private LocalDateTime editedDate;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String  localDate;
    @ElementCollection
    private List<String> files;
    @ElementCollection
    private List<Like> likes = new ArrayList<>();
    @Column(name = "visible", nullable = true)
    private boolean visible;

    public ShiftPost(String autor, String title, String content, String localDate, String dayornight, String state, String editedBy, LocalDateTime editedDate) {
        this.autor = autor;
        this.title = title;
        this.content = content;
        if (content.length()>maxTextChar) throw new IllegalStateException("Za dużo znaków w treści wpisu");
        this.localDate = localDate;
        this.dayornight = dayornight;
        this.state = state;
        this.editedBy = editedBy;
        this.editedDate = editedDate;
        this.visible = true;
    }

    public ShiftPost(String autor, String title, String content, String localDate, List<String> files, String dayornight, String state, String editedBy, LocalDateTime editedDate) {
        this.autor = autor;
        this.title = title;
        this.content = content;
        if (content.length()>maxTextChar) throw new IllegalStateException("Za dużo znaków w treści wpisu");
        this.localDate = localDate;
        this.files = files;
        this.dayornight = dayornight;
        this.state = state;
        this.editedBy = editedBy;
        this.editedDate = editedDate;
        this.visible = true;
    }

    public boolean addLike(String username) {
        boolean alreadyLiked = likes.stream().anyMatch( like -> like.getUsername().equals(username));
        if (!alreadyLiked) {
            likes.add(new Like(username));
            return true;
        }
        return false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        visible = visible;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDayornight() {
        return dayornight;
    }

    public void setDayornight(String dayornight) {
        this.dayornight = dayornight;
    }

    public List<String> getFiles() {
        if (this.files == null) {
            this.files = new ArrayList<>();
        }
        return this.files;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
    public void addFiles(List<String> newFiles) {
        if (this.files == null) {
            this.files = new ArrayList<>();
        }
        this.files.addAll(newFiles);
    }
    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedDate(LocalDateTime editedDate) {
        this.editedDate = editedDate;
    }

    public LocalDateTime getEditedDate() {
        return editedDate;
    }

    public String getFormattedEditedDate() {
        if (editedDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return editedDate.format(formatter);
        } else {
            return null;
        }
    }
    @Override
    public String toString() {
        return "ShiftPost{" +
                "id=" + id +
                ", autor='" + autor + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", localDate='" + localDate + '\'' +
                '}';
    }
}