package fr.gildasfedevieille.library;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.gildasfedevieille.book.Book;
import fr.gildasfedevieille.exceptions.NoBookFound;
import fr.gildasfedevieille.user.User;
import fr.gildasfedevieille.utils.CustomHashSet;

public class Library
{
    // instance pour le singleton
    private static Library instance;

    // HashSet: Les livres sont uniques + Recherche et insertion rapide
    private CustomHashSet<Book> availableBooks;
    private CustomHashSet<Book> rentedBooks;

    // HashSet: Les utilisateurs sont uniques + Recherche et insertion rapide
    private CustomHashSet<User> users;

    // accesseur à l'instance unique de Library
    public static Library getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new Library();
        }
        return instance;
    }

    // construteur privé pour le singleton
    private Library() throws IOException
    {
        availableBooks = new CustomHashSet<>();
        rentedBooks = new CustomHashSet<>();
        users = new CustomHashSet<>();
        loadDatabase();
    }

    public boolean addBook(Book book)
    {
        return availableBooks.add(book);
    }

    public boolean removeBook(Book book)
    {
        return availableBooks.remove(book);
    }

    private void loadAvailableBooks() throws IOException
    {
        File file = new File(getClass().getResource("/availableBooks.json").getFile());

        String fileContent = Files.readString(file.toPath());

        Gson gson = new Gson();

        Type bookEntryListType = new TypeToken<CustomHashSet<Book>>(){}.getType();

        CustomHashSet<Book> books = gson.fromJson(fileContent, bookEntryListType);

        if (books != null && !books.isEmpty())
        {
            availableBooks.addAll(books);
        }
    }

    private void saveAvailableBooks() throws IOException
    {
        File file = new File(getClass().getResource("/availableBooks.json").getFile());

        Gson gson = new Gson();

        String json = gson.toJson(availableBooks);

        Files.writeString(file.toPath(), json);
    }

    private void loadRentedBooks() throws IOException
    {
        File file = new File(getClass().getResource("/rentedBooks.json").getFile());

        String fileContent = Files.readString(file.toPath());

        Gson gson = new Gson();

        Type bookEntryListType = new TypeToken<CustomHashSet<Book>>(){}.getType();

        CustomHashSet<Book> books = gson.fromJson(fileContent, bookEntryListType);

        if (books != null && !books.isEmpty())
        {
            rentedBooks.addAll(books);
        }
    }

    private void saveRentedBooks() throws IOException
    {
        File file = new File(getClass().getResource("/rentedBooks.json").getFile());

        Gson gson = new Gson();

        String json = gson.toJson(rentedBooks);

        Files.writeString(file.toPath(), json);
    }

    private void loadUsers() throws IOException
    {
        File file = new File(getClass().getResource("/users.json").getFile());

        String fileContent = Files.readString(file.toPath());

        Gson gson = new Gson();

        Type userListType = new TypeToken<CustomHashSet<User>>(){}.getType();

        CustomHashSet<User> loadedUsers = gson.fromJson(fileContent, userListType);

        if (loadedUsers != null && !loadedUsers.isEmpty())
        {
            users.addAll(loadedUsers);
        }
    }

    private void saveUsers() throws IOException
    {
        File file = new File(getClass().getResource("/users.json").getFile());

        Gson gson = new Gson();

        String json = gson.toJson(users);

        Files.writeString(file.toPath(), json);
    }

    final public void loadDatabase() throws IOException
    {
        loadAvailableBooks();
        loadRentedBooks();
        loadUsers();
    }

    final public void saveDatabase() throws IOException
    {
        saveAvailableBooks();
        saveRentedBooks();
        saveUsers();
    }

    public boolean addUser(User user)
    {
        return users.add(user);
    }

    public boolean removeUser(User user)
    {
        return users.remove(user);
    }

    public User getUser(String id)
    {
        return users.firstOrDefault(u -> u.getId().equals(id));
    }

    public boolean rentBook(User user, String ISBN) throws NoBookFound
    {
        Book book = availableBooks.firstOrDefault(b -> b.getISBN().equals(ISBN));

        if (book == null)
        {
            throw new NoBookFound();
        }

        if (user.canRentBook(book))
        {
            if (user.rentBook(book))
            {
                return availableBooks.remove(book) && rentedBooks.add(book);
            }
        }
        return false;
    }

    public boolean returnBook(User user, String ISBN) throws NoBookFound
    {
        Book book = rentedBooks.firstOrDefault(b -> b.getISBN().equals(ISBN));

        if (book == null)
        {
            throw new NoBookFound();
        }

        if (user.returnBook(book))
        {
            return rentedBooks.remove(book) && availableBooks.add(book);
        }
        return false;
    }

    public CustomHashSet<Book> getAvailableBooks()
    {
        return availableBooks;
    }

    public Book getBook(String ISBN)
    {
        return availableBooks.firstOrDefault(b -> b.getISBN().equals(ISBN));
    }

    public void exportCatalog(String path) throws IOException
    {
        File file = new File(path);

        Gson gson = new Gson();

        String json = gson.toJson(availableBooks);

        Files.writeString(file.toPath(), json);
    }
}
