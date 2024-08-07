package com.example.demo.dao;

import javax.persistence.*;

@Entity
@Table(name = "fashion_data")
public class FashionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String p_id;
    private String p_name;
    private String colour;
    private String brand;
    private String img;
    private Integer ratingcount;
    private Double avg_rating;
    private String description;
    private Double price;
    private String p_attributes;
    private Integer rating_count;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getRatingcount() {
        return ratingcount;
    }

    public void setRatingcount(Integer ratingcount) {
        this.ratingcount = ratingcount;
    }

    public Double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(Double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getP_attributes() {
        return p_attributes;
    }

    public void setP_attributes(String p_attributes) {
        this.p_attributes = p_attributes;
    }

    public Integer getRating_count() {
        return rating_count;
    }

    public void setRating_count(Integer rating_count) {
        this.rating_count = rating_count;
    }
}
