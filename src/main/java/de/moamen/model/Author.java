package de.moamen.model;

import java.util.Objects;

public class Author implements Comparable {
    private static int counter = 0;
    private int id;
    private String name;
    private String bio;

    public Author(String name) {
        this.id = counter++;
        this.name = name;
    }

    public Author(String name, String bio) {
        this(name);
        this.bio = bio;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return id == author.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public int compareTo(Object o) {
        Author author = (Author) o;
        return this.name.compareTo(author.getName());
    }
}
