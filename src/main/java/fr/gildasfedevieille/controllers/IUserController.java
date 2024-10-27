package fr.gildasfedevieille.controllers;

import fr.gildasfedevieille.models.IUser;

public interface IUserController {

    void handleConnexion();

    void handleRegistration();

    IUser getUser();

    void logout();

    boolean isLogged();

}