package fr.gildasfedevieille.controllers;

import fr.gildasfedevieille.models.ILibrary;
import fr.gildasfedevieille.models.IUser;
import fr.gildasfedevieille.models.User;
import fr.gildasfedevieille.views.IAppView;

public class UserController implements IUserController {

    private final ILibrary library;
    private final IAppView view;
    private IUser user;

    public UserController(ILibrary library, IAppView view) {
        this.library = library;
        this.view = view;
    }

    @Override
    public void handleConnexion() {
        String id = view.getUserId();
        user = library.getUser(id);
        if (user == null) {
            view.displayNotFoundUserMessage();
        }
    }

    @Override
    public void handleRegistration() {
        String id = view.getUserId();
        if (!library.addUser(new User(id))) {
            view.displayUserAlreadyExistsMessage();
        }
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public void logout() {
        this.user = null;
    }

    @Override
    public boolean isLogged() {
        return user != null;
    }
}
