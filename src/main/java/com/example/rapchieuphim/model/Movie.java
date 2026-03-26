package com.example.rapchieuphim.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Movies")
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title; // Tên phim
    
    @Column(length = 1000)
    private String description; // Nội dung phim (để chiều dài 1000 ký tự)
    
    private String poster; // Link ảnh bìa của phim
    
    private Integer duration; // Thời lượng (phút)
    
    private String genre; // Thể loại (Hành động, Tình cảm...)

    private boolean active = true;

    // Constructors
    public Movie() {
    }

    public Movie(String title, String description, String poster, Integer duration, String genre, boolean active) {
        this.title = title;
        this.description = description;
        this.poster = poster;
        this.duration = duration;
        this.genre = genre;
        
    }

    // Getters và Setters
    public Long getId() { return id; }
    public void setId(Long  id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}