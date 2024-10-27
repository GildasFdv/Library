package fr.gildasfedevieille.controllers;

import fr.gildasfedevieille.models.IUser;

public interface IBookController {

    void handleShowAvailableBooks();

    void handleShowDetails();

    void handleRentBook(IUser user);

    void handleReturnBook(IUser user);

    void handleExport();

}