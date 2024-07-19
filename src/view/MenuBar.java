package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar {
    private JMenuBar barreDeMenu = new JMenuBar();
    private List<JMenuItem> itemsMenu1 = new ArrayList<>();
    private List<JMenuItem> itemsMenu2 = new ArrayList<>();

    private JMenu option1 = new JMenu("Fichiers");
    private JMenu option2 = new JMenu("Edition");

    public MenuBar() {
        initialiserMenu();
    }

    private void initialiserMenu() {
        itemsMenu1.add(new JMenuItem("Ouvrir une nouvelle fenêtre"));
        itemsMenu1.add(new JMenuItem("Nouveau"));
        itemsMenu1.add(new JMenuItem("Ouvrir"));
        itemsMenu1.add(new JMenuItem("Sauvegarder"));
        itemsMenu1.add(new JMenuItem("Fermer"));

        itemsMenu2.add(new JMenuItem("Nouveau dossier"));
        itemsMenu2.add(new JMenuItem("Nouveau fichier"));
        itemsMenu2.add(new JMenuItem("Copier"));
        itemsMenu2.add(new JMenuItem("Couper"));
        itemsMenu2.add(new JMenuItem("Coller"));
        itemsMenu2.add(new JMenuItem("Renommer"));
        itemsMenu2.add(new JMenuItem("Supprimer"));
        itemsMenu2.add(new JMenuItem("Propriété"));

        for (JMenuItem item : itemsMenu1) {
            option1.add(item);
        }
        for (JMenuItem item : itemsMenu2) {
            option2.add(item);
        }

        barreDeMenu.add(option1);
        barreDeMenu.add(option2);
    }

    public JMenuBar getMenuBar() {
        return barreDeMenu;
    }

    public List<JMenuItem> getItemsMenu1() {
        return itemsMenu1;
    }

    public List<JMenuItem> getItemsMenu2() {
        return itemsMenu2;
    }
}
