package fr.gildasfedevieille;

import java.util.Scanner;

import fr.gildasfedevieille.controllers.AppController;
import fr.gildasfedevieille.controllers.IAppController;
import fr.gildasfedevieille.models.ILibrary;
import fr.gildasfedevieille.models.Library;
import fr.gildasfedevieille.views.AppView;
import fr.gildasfedevieille.views.IAppView;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ILibrary library = new Library();
        IAppView appView = new AppView(scanner);
        IAppController controller = new AppController(library, appView);

        try {
            controller.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
