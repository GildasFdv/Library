package fr.gildasfedevieille.models;

public interface IUser {

    String getId();

    boolean rentBook(IBook book);

    boolean returnBook(IBook book);

    boolean canRentBook(IBook book);

}