package de.moamen.model;

import java.util.Objects;

public class Book implements Comparable{
    private static int counter=0;
    private int id;
    private String title;
    private int year;
    private Author author;

    public Book(String title, int year,Author author){
        this.id=counter++;
        this.title=title;
        this.year=year;
        this.author=author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", author=" + author +
                '}';
    }

    @Override
    public boolean equals(Object object){
        if (this==object) return true;
        if (!(object instanceof Book book)) return false;
        return this.id == book.getId();
    }

    @Override
    public int hashCode(){
        return Objects.hash(id,title,year);
    }


    @Override
    public int compareTo(Object o) {
        Book book=(Book) o;
        return Integer.compare(this.year,book.getYear());
    }
}
