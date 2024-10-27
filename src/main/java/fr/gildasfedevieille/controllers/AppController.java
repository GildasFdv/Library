package fr.gildasfedevieille.controllers;

import java.io.IOException;

import fr.gildasfedevieille.models.ILibrary;
import fr.gildasfedevieille.views.IAppView;

public class AppController implements IAppController {

    private IUserController userController;
    private IBookController bookController;
    private ILibrary library;
    private IAppView view;

    public AppController(ILibrary library, IAppView view) {
        this.library = library;
        this.view = view;
        this.userController = new UserController(library, view);
        this.bookController = new BookController(library, view);
    }

    @Override
    public void run() throws IllegalStateException {
        int response;

        try {
            library.loadDatabase();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        do {
            if (userController.isLogged()) {
                view.displayLoggedMenu();
                response = view.getLoggedMenuChoice();
                handleLoggedMenu(response);
            } else {
                view.displayUnloggedMenu();
                response = view.getUnloggedMenuChoice();
                handleUnloggedMenu(response);
            }
        } while (response != 0);

        saveLibraryData();
        view.displayGoodbyeMessage();
    }

    private void handleUnloggedMenu(int response) throws IllegalStateException {
        switch (response) {
            case 1:
                userController.handleConnexion();
                break;
            case 2:
                userController.handleRegistration();
                break;
            case 0:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + response);
        }
    }

    private void handleLoggedMenu(int response) throws IllegalStateException {
        switch (response) {
            case 1 ->
                bookController.handleShowAvailableBooks();
            case 2 ->
                bookController.handleShowDetails();
            case 3 ->
                bookController.handleRentBook(userController.getUser());
            case 4 ->
                bookController.handleReturnBook(userController.getUser());
            case 5 ->
                bookController.handleExport();
            case 0 ->
                userController.logout();
            default ->
                throw new IllegalStateException("Unexpected value: " + response);
        }
    }

    private void saveLibraryData() {
        try {
            library.saveDatabase();
        } catch (IOException e) {
            view.displaySaveFailedMessage();
        }
    }
}
