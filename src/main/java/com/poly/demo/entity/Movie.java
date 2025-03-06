package com.poly.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String tags;
    private int duration;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private int view_count;
    private String country;
    private String producer;
    private String director;

    @Column(columnDefinition = "TEXT")
    private String actors;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnail;
    private String trailer;

    // 🛠 Constructors
    public Movie() {}

    public Movie(String name, String tags, int duration, LocalDate releaseDate, LocalDate endDate, int view_count, String country, String producer, String director, String actors, String description, String thumbnail, String trailer) {
        this.name = name;
        this.tags = tags;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.endDate = endDate;
        this.view_count = view_count;
        this.country = country;
        this.producer = producer;
        this.director = director;
        this.actors = actors;
        this.description = description;
        this.thumbnail = thumbnail;
        this.trailer = trailer;
    }

    // 🛠 Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public int getViews() { return view_count; }
    public void setViews(int view_count) { this.view_count = view_count; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getProducer() { return producer; }
    public void setProducer(String producer) { this.producer = producer; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getActors() { return actors; }
    public void setActors(String actors) { this.actors = actors; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public String getTrailer() { return trailer; }
    public void setTrailer(String trailer) { this.trailer = trailer; }
}
