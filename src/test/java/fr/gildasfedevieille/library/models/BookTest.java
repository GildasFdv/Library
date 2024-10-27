package fr.gildasfedevieille.library.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.gildasfedevieille.models.Book;
import fr.gildasfedevieille.models.IBook;

public class BookTest {
    private Book book;

    @BeforeEach
    public void createInstance() {
        this.book = new Book("isbn", "title", "description", "author", 20.0);
    }

    @Test
    public void testGetISBN() {
        Assertions.assertEquals("isbn", this.book.getISBN());
    }

    @Test
    public void testSetISBN() {
        this.book.setISBN("newIsbn");
        Assertions.assertEquals("newIsbn", this.book.getISBN());
    }

    @Test
    public void testGetTitle() {
        Assertions.assertEquals("title", this.book.getTitle());
    }

    @Test
    public void testSetTitle() {
        this.book.setTitle("newTitle");
        Assertions.assertEquals("newTitle", this.book.getTitle());
    }

    @Test
    public void testGetDescription() {
        Assertions.assertEquals("description", this.book.getDescription());
    }

    @Test
    public void testSetDescription() {
        this.book.setDescription("newDescription");
        Assertions.assertEquals("newDescription", this.book.getDescription());
    }

    @Test
    public void testGetAuthor() {
        Assertions.assertEquals("author", this.book.getAuthor());
    }

    @Test
    public void testSetAuthor() {
        this.book.setAuthor("newAuthor");
        Assertions.assertEquals("newAuthor", this.book.getAuthor());
    }

    @Test
    public void testGetPrice() {
        Assertions.assertEquals(20.0, this.book.getPrice());
    }

    @Test
    public void testSetPrice() {
        this.book.setPrice(30.0);
        Assertions.assertEquals(30.0, this.book.getPrice());
    }

    @Test
    public void testConstructor() {
        Book newBook = new Book("isbn", "title", "description", "author", 20.0);
        Assertions.assertEquals("isbn", newBook.getISBN());
        Assertions.assertEquals("title", newBook.getTitle());
        Assertions.assertEquals("description", newBook.getDescription());
        Assertions.assertEquals("author", newBook.getAuthor());
        Assertions.assertEquals(20.0, newBook.getPrice());
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("Book{ISBN='isbn', title='title', description='description', author='author', price=20.0}", this.book.toString());
    }

    @Test
    public void testEquals() {
        IBook newBook = new Book("isbn", "title", "description", "author", 20.0);
        Assertions.assertEquals(this.book, newBook);
    }

    @Test
    public void testNotEquals() {
        IBook newBook = new Book("newIsbn", "title", "description", "author", 20.0);
        Assertions.assertNotEquals(this.book, newBook);
    }

    @Test
    public void testHashCode() {
        IBook newBook = new Book("isbn", "title", "description", "author", 20.0);
        Assertions.assertEquals(this.book.hashCode(), newBook.hashCode());
    }

    @Test
    public void testNotHashCode() {
        IBook newBook = new Book("newIsbn", "title", "description", "author", 20.0);
        Assertions.assertNotEquals(this.book.hashCode(), newBook.hashCode());
    }

    @Test
    public void testNotEqualsNull() {
        Assertions.assertNotEquals(this.book, null);
    }

    @Test
    public void testNotEqualsDifferentClass() {
        Assertions.assertNotEquals(this.book, "string");
    }

    @Test
    public void testEqualsSameInstance() {
        Assertions.assertEquals(this.book, this.book);
    }

    @Test
    public void testEqualsDifferentInstance() {
        IBook newBook = new Book("isbn", "title", "description", "author", 20.0);
        Assertions.assertEquals(this.book, newBook);
    }

    @Test
    public void testNotEqualsDifferentISBN() {
        IBook newBook = new Book("newIsbn", "title", "description", "author", 20.0);
        Assertions.assertNotEquals(this.book, newBook);
    }

    @Test
    public void testToShortString() {
        Assertions.assertEquals("Book{ISBN='isbn', title='title'}", this.book.toShortString());
    }
}
