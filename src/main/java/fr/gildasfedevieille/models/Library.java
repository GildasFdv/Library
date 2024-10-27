package fr.gildasfedevieille.models;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.gildasfedevieille.exceptions.NoBookFound;
import fr.gildasfedevieille.utils.CustomHashSet;

public class Library implements ILibrary {

    // HashSet: Les livres sont uniques + Recherche et insertion rapide
    private CustomHashSet<IBook> availableBooks;
    private CustomHashSet<IBook> rentedBooks;

    // HashSet: Les utilisateurs sont uniques + Recherche et insertion rapide
    private CustomHashSet<IUser> users;

    public Library() {
        availableBooks = new CustomHashSet<>();
        rentedBooks = new CustomHashSet<>();
        users = new CustomHashSet<>();
    }

    public boolean addBook(Book book) {
        return availableBooks.add(book);
    }

    public boolean removeBook(IBook book) {
        return availableBooks.remove(book);
    }

    private void loadAvailableBooks() throws IOException {
        File file = new File(getClass().getResource("/availableBooks.json").getFile());

        String fileContent = Files.readString(file.toPath());

        Gson gson = new Gson();

        Type bookEntryListType = new TypeToken<CustomHashSet<Book>>() {
        }.getType();

        CustomHashSet<Book> books = gson.fromJson(fileContent, bookEntryListType);

        if (books != null && !books.isEmpty()) {
            availableBooks.addAll(books);
        }
    }

    private void saveAvailableBooks() throws IOException {
        File file = new File(getClass().getResource("/availableBooks.json").getFile());

        Gson gson = new Gson();

        String json = gson.toJson(availableBooks);

        Files.writeString(file.toPath(), json);
    }

    private void loadRentedBooks() throws IOException {
        File file = new File(getClass().getResource("/rentedBooks.json").getFile());

        String fileContent = Files.readString(file.toPath());

        Gson gson = new Gson();

        Type bookEntryListType = new TypeToken<CustomHashSet<Book>>() {
        }.getType();

        CustomHashSet<IBook> books = gson.fromJson(fileContent, bookEntryListType);

        if (books != null && !books.isEmpty()) {
            rentedBooks.addAll(books);
        }
    }

    private void saveRentedBooks() throws IOException {
        File file = new File(getClass().getResource("/rentedBooks.json").getFile());

        Gson gson = new Gson();

        String json = gson.toJson(rentedBooks);

        Files.writeString(file.toPath(), json);
    }

    private void loadUsers() throws IOException {
        File file = new File(getClass().getResource("/users.json").getFile());

        String fileContent = Files.readString(file.toPath());

        Gson gson = new Gson();

        Type userListType = new TypeToken<CustomHashSet<User>>() {
        }.getType();

        CustomHashSet<IUser> loadedUsers = gson.fromJson(fileContent, userListType);

        if (loadedUsers != null && !loadedUsers.isEmpty()) {
            users.addAll(loadedUsers);
        }
    }

    private void saveUsers() throws IOException {
        File file = new File(getClass().getResource("/users.json").getFile());

        Gson gson = new Gson();

        String json = gson.toJson(users);

        Files.writeString(file.toPath(), json);
    }

    @Override
    final public void loadDatabase() throws IOException {
        loadAvailableBooks();
        loadRentedBooks();
        loadUsers();
    }

    @Override
    final public void saveDatabase() throws IOException {
        saveAvailableBooks();
        saveRentedBooks();
        saveUsers();
    }

    @Override
    public boolean addUser(IUser user) {
        return users.add(user);
    }

    public boolean removeUser(IUser user) {
        return users.remove(user);
    }

    @Override
    public IUser getUser(String id) {
        return users.firstOrDefault(u -> u.getId().equals(id));
    }

    @Override
    public boolean rentBook(IUser user, String ISBN) throws NoBookFound {
        IBook book = availableBooks.firstOrDefault(b -> b.getISBN().equals(ISBN));

        if (book == null) {
            throw new NoBookFound();
        }

        if (user.canRentBook(book)) {
            if (user.rentBook(book)) {
                return availableBooks.remove(book) && rentedBooks.add(book);
            }
        }
        return false;
    }

    @Override
    public boolean returnBook(IUser user, String ISBN) throws NoBookFound {
        IBook book = rentedBooks.firstOrDefault(b -> b.getISBN().equals(ISBN));

        if (book == null) {
            throw new NoBookFound();
        }

        if (user.returnBook(book)) {
            return rentedBooks.remove(book) && availableBooks.add(book);
        }
        return false;
    }

    @Override
    public CustomHashSet<IBook> getAvailableBooks() {
        return availableBooks;
    }

    @Override
    public IBook getBook(String ISBN) {
        return availableBooks.firstOrDefault(b -> b.getISBN().equals(ISBN));
    }

    @Override
    public void exportCatalog(String path) throws IOException {
        File file = new File(path);

        Gson gson = new Gson();

        String json = gson.toJson(availableBooks);

        Files.writeString(file.toPath(), json);
    }
}
