package fr.gildasfedevieille.user;

import java.util.HashSet;
import fr.gildasfedevieille.models.Book;

public class User
{
    private final HashSet<Book> rentedBooks = new HashSet<Book>();

    public User(final String id)
    {
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean rentBook(Book book)
    {
        return rentedBooks.add(book);
    }

    public boolean returnBook(Book book)
    {
        return rentedBooks.remove(book);
    }

    public boolean canRentBook(Book book)
    {
        return rentedBooks.size() < 3 && !rentedBooks.contains(book);
    }
}
