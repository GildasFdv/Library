package fr.gildasfedevieille.models;

import java.util.HashSet;

public class User implements IUser {

    private final HashSet<IBook> rentedBooks = new HashSet<IBook>();

    public User(final String id) {
        this.id = id;
    }

    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean rentBook(IBook book) {
        return rentedBooks.add(book);
    }

    @Override
    public boolean returnBook(IBook book) {
        return rentedBooks.remove(book);
    }

    @Override
    public boolean canRentBook(IBook book) {
        return rentedBooks.size() < 3 && !rentedBooks.contains(book);
    }

    @Override
    public String toString() {
        return "User{id='" + id + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
