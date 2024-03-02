package edu.hogwarts.models;

import jakarta.persistence.*;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private String founder;

    private String colors;

    public House() {
    }

    public House(String name, String founder, String colors) {
        this.name = name;
        this.founder = founder;
        this.colors = colors;
    }

    // Constructor that accepts a String argument for name
    public House(String name) {
        this.name = name;
        // You can set default values for other fields if needed
    }

    // Getters and setters
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

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }
}

