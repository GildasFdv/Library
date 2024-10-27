package fr.gildasfedevieille.controllers;

import java.io.IOException;

import fr.gildasfedevieille.models.IBook;
import fr.gildasfedevieille.models.ILibrary;
import fr.gildasfedevieille.models.IUser;
import fr.gildasfedevieille.views.IAppView;

public class BookController implements IBookController {

    private final ILibrary library;
    private final IAppView view;

    public BookController(ILibrary library, IAppView view) {
        this.library = library;
        this.view = view;
    }

    @Override
    public void handleShowAvailableBooks() {
        for (IBook book : library.getAvailableBooks()) {
            view.displayBookInsight(book);
        }
    }

    @Override
    public void handleShowDetails() {
        String isbn = view.getISBN();
        IBook book = library.getBook(isbn);
        if (book != null) {
            view.displayBookDetails(book);
        } else {
            view.displayUnknownBookMessage();
        }
    }

    @Override
    public void handleRentBook(IUser user) {
        String isbn = view.getISBN();
        try {
            if (library.rentBook(user, isbn)) {
                view.displayBookRentedMessage();
            } else {
                view.displayBookNotRentedMessage();
            }
        } catch (Exception e) {
            view.displayUnknownBookMessage();
        }
    }

    @Override
    public void handleReturnBook(IUser user) {
        String isbn = view.getISBN();
        try {
            if (library.returnBook(user, isbn)) {
                view.displayBookReturnedMessage();
            } else {
                view.displayBookNotRentedMessage();
            }
        } catch (Exception e) {
            view.displayUnknownBookMessage();
        }
    }

    @Override
    public void handleExport() {
        try {
            String path = view.getExportPath();
            library.exportCatalog(path);
            view.displayExportSuccessMessage();
        } catch (IOException e) {
            view.displayExportFailedMessage();
        }
    }
}
