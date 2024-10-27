package fr.gildasfedevieille.views;

import fr.gildasfedevieille.models.IBook;

public interface IAppView {

    void displayUnloggedMenu();

    int getUnloggedMenuChoice();

    void displayLoggedMenu();

    int getLoggedMenuChoice();

    String getUserId();

    String getISBN();

    String getExportPath();

    void displayNotFoundUserMessage();

    void displayUserAlreadyExistsMessage();

    void displayBookInsight(IBook book);

    void displayBookDetails(IBook book);

    void displayUnknownBookMessage();

    void displayBookRentedMessage();

    void displayBookReturnedMessage();

    void displayBookNotRentedMessage();

    void displayBookNotReturnedMessage();

    void displayExportFailedMessage();

    void displayExportSuccessMessage();

    void displayGoodbyeMessage();

    void displaySaveFailedMessage();

}