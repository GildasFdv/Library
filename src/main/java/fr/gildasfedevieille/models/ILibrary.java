package fr.gildasfedevieille.models;

import java.io.IOException;

import fr.gildasfedevieille.exceptions.NoBookFound;
import fr.gildasfedevieille.utils.CustomHashSet;

public interface ILibrary {

    void loadDatabase() throws IOException;

    void saveDatabase() throws IOException;

    boolean addUser(IUser user);

    IUser getUser(String id);

    boolean rentBook(IUser user, String ISBN) throws NoBookFound;

    boolean returnBook(IUser user, String ISBN) throws NoBookFound;

    CustomHashSet<IBook> getAvailableBooks();

    IBook getBook(String ISBN);

    void exportCatalog(String path) throws IOException;

}