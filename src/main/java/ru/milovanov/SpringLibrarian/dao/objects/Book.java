package ru.milovanov.SpringLibrarian.dao.objects;


import java.util.Objects;
public class Book {

    private String isbn;
    private String author,title,username;
    public Book(String isbn,String author,String title,String username){
        this.isbn=isbn;
        this.author=author;
        this.title=title;
        this.username=username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) &&
                Objects.equals(author, book.author) &&
                Objects.equals(title, book.title) &&
                Objects.equals(username, book.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, author, title, username);
    }

    @Override
    public String toString(){
        return author+" "+title+" "+isbn+" "+username;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }
    //@Autowired
    public void setUsername(String username) {
        this.username = username;
    }
}
