package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopupMenu {
    private JPopupMenu popupMenu = new JPopupMenu();
    private List<JMenuItem> popupItems = new ArrayList<>();

    public PopupMenu() {
        initializePopupMenu();
    }

    private void initializePopupMenu() {
        popupItems.add(new JMenuItem("Nouveau dossier"));
        popupItems.add(new JMenuItem("Nouveau fichier"));
        popupItems.add(new JMenuItem("Copier"));
        popupItems.add(new JMenuItem("Couper"));
        popupItems.add(new JMenuItem("Coller"));
        popupItems.add(new JMenuItem("Renommer"));
        popupItems.add(new JMenuItem("Supprimer"));
        popupItems.add(new JMenuItem("Propriété"));

        for (JMenuItem item : popupItems) {
            popupMenu.add(item);
        }
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public List<JMenuItem> getPopupItems() {
        return popupItems;
    }
}
