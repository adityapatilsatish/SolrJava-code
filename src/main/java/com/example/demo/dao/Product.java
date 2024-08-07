package com.example.demo.dao;


import javax.persistence.Id;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "ecommerce")
public class Product {
   
    @Id
    @Indexed(name = "id", type = "int")
    private int id;

    @Indexed(name = "name", type = "string")
    private String name;

    // Constructors, getters, and setters

    public Product() {}

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}