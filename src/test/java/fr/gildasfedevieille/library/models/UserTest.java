package fr.gildasfedevieille.library.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.gildasfedevieille.models.Book;
import fr.gildasfedevieille.models.IUser;
import fr.gildasfedevieille.models.User;

public class UserTest {

    private User user;

    @BeforeEach
    public void createInstance() {
        this.user = new User("test");
    }

    @Test
    public void testGetId() {
        Assertions.assertEquals("test", this.user.getId());
    }

    @Test
    public void testSetId() {
        this.user.setId("test2");
        Assertions.assertEquals("test2", this.user.getId());
    }
    
    @Test
    public void testToString() {
        Assertions.assertEquals("User{id='test'}", this.user.toString());
    }

    @Test
    public void testEquals() {
        IUser user2 = new User("test");
        Assertions.assertEquals(this.user, user2);
    }

    @Test
    public void testNotEquals() {
        IUser user2 = new User("test2");
        Assertions.assertNotEquals(this.user, user2);
    }

    @Test
    public void testHashCode() {
        IUser user2 = new User("test");
        Assertions.assertEquals(this.user.hashCode(), user2.hashCode());
    }

    @Test
    public void testNotHashCode() {
        IUser user2 = new User("test2");
        Assertions.assertNotEquals(this.user.hashCode(), user2.hashCode());
    }

    @Test
    public void testRentBook() {
        Assertions.assertTrue(this.user.rentBook(new Book("isbn", "title", "description", "author", 10.0)));
    }

    @Test
    public void testRentBookTwiceSameBook() {
        Book book = new Book("isbn", "title", "description", "author", 10.0);
        this.user.rentBook(book);
        Assertions.assertFalse(this.user.rentBook(book));
    }

    @Test
    public void testReturnBook() {
        Book book = new Book("isbn", "title", "description", "author", 10.0);
        this.user.rentBook(book);
        Assertions.assertTrue(this.user.returnBook(book));
    }

    @Test
    public void testCanRentBook() {
        Book book = new Book("isbn", "title", "description", "author", 10.0);
        this.user.rentBook(book);
        Assertions.assertFalse(this.user.canRentBook(book));
    }

    @Test
    public void testCanRentBookThreeTimes() {
        this.user.rentBook(new Book("isbn", "title", "description", "author", 10.0));
        this.user.rentBook(new Book("isbn2", "title2", "description2", "author2", 20.0));
        this.user.rentBook(new Book("isbn3", "title3", "description3", "author3", 30.0));
        Assertions.assertFalse(this.user.canRentBook(new Book("isbn4", "title4", "description4", "author4", 40.0)));
    }

    @Test
    public void testCanRentBookTwice() {
        Book book = new Book("isbn", "title", "description", "author", 10.0);
        this.user.rentBook(book);
        Assertions.assertTrue(this.user.canRentBook(new Book("isbn2", "title2", "description2", "author2", 20.0)));
    }

    @Test
    public void testCanRentBookTwiceSameBook() {
        Book book = new Book("isbn", "title", "description", "author", 10.0);
        this.user.rentBook(book);
        Assertions.assertFalse(this.user.canRentBook(book));
    }
}
