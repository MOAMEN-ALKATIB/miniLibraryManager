package de.moamen.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Author implements Comparable<Author>, Serializable {
    @Serial
    private static final long serialVersionUID=213123194L;
    private static int counter = 0;
    private int id;
    private String name;
    private String bio;
    public Author() {}

    public Author(String name) {
        this.id = counter++;
        this.name = name;
    }

    public Author(String name, String bio) {
        this(name);
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
    public int compareTo(Author author) {
        return this.name.compareTo(author.getName());
    }
}
