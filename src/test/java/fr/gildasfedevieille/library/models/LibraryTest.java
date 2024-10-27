package fr.gildasfedevieille.library.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.gildasfedevieille.exceptions.NoBookFound;
import fr.gildasfedevieille.models.Book;
import fr.gildasfedevieille.models.IUser;
import fr.gildasfedevieille.models.Library;
import fr.gildasfedevieille.models.User;

public class LibraryTest {
    private Library library;

    @BeforeEach
    public void createInstance() {
        try{
            this.library = new Library();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddBook() {
        this.library.addBook(new Book("isbn", "title", "description", "author", 20.0));
        Assertions.assertEquals(1, this.library.getAvailableBooks().size());
    }

    @Test
    public void testRentBook() {
        this.library.addBook(new Book("isbn", "title", "description", "author", 20.0));
        IUser user = new User("test");

        try
        {
            Assertions.assertTrue(this.library.rentBook(user, "isbn"));
            Assertions.assertEquals(0, this.library.getAvailableBooks().size());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testRentBookNotAvailable() {
        IUser user = new User("test");
        IUser user2 = new User("test2");

        library.addBook(new Book("isbn", "title", "description", "author", 20.0));

        try
        {
            library.rentBook(user2, "isbn");
            Assertions.assertThrows(NoBookFound.class, () -> this.library.rentBook(user, "isbn"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testRentBookNotExisting() {
        IUser user = new User("test");

        try
        {
            Assertions.assertThrows(NoBookFound.class, () -> this.library.rentBook(user, "isbn"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testRentAlreadyRentedBook() {
        IUser user = new User("test");
        
        library.addBook(new Book("isbn", "title", "description", "author", 20.0));

        try
        {
            library.rentBook(user, "isbn");
            Assertions.assertThrows(NoBookFound.class, () -> this.library.rentBook(user, "isbn"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testReturnBook() {
        IUser user = new User("test");

        library.addBook(new Book("isbn", "title", "description", "author", 20.0));

        try
        {
            library.rentBook(user, "isbn");
            Assertions.assertTrue(this.library.returnBook(user, "isbn"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testReturnNotRentedBook() {
        IUser user = new User("test");

        library.addBook(new Book("isbn", "title", "description", "author", 20.0));

        try
        {
            Assertions.assertThrows(NoBookFound.class, () -> this.library.returnBook(user, "isbn"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testReturnNotExistingBook() {
        IUser user = new User("test");

        try
        {
            Assertions.assertThrows(NoBookFound.class, () -> this.library.returnBook(user, "isbn"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testReturnAlreadyReturnedBook() {
        IUser user = new User("test");

        library.addBook(new Book("isbn", "title", "description", "author", 20.0));

        try
        {
            library.rentBook(user, "isbn");
            library.returnBook(user, "isbn");
            Assertions.assertThrows(NoBookFound.class, () -> this.library.returnBook(user, "isbn"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAvailableBooks() {
        library.addBook(new Book("isbn", "title", "description", "author", 20.0));
        Assertions.assertEquals(1, this.library.getAvailableBooks().size());
    }

    @Test
    public void testRentMoreThanThreeBooks() {
        IUser user = new User("test");

        library.addBook(new Book("isbn", "title", "description", "author", 20.0));
        library.addBook(new Book("isbn2", "title", "description", "author", 20.0));
        library.addBook(new Book("isbn3", "title", "description", "author", 20.0));
        library.addBook(new Book("isbn4", "title", "description", "author", 20.0));

        try
        {
            library.rentBook(user, "isbn");
            library.rentBook(user, "isbn2");
            library.rentBook(user, "isbn3");
            Assertions.assertFalse(this.library.rentBook(user, "isbn4"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }    
}
