package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JToolBar;

import outils.ManipList;

public class ToolBar {
    private JToolBar toolbar = new JToolBar();
    private List<JButton> buttons = new ArrayList<>();
    private Icones icones = new Icones();

    private JButton[] buttonsSet1 = {
        new JButton("Terrain", icones.getTerrain()),
        new JButton("Maison", icones.getMaison()),
        new JButton("Pièce", icones.getPiece()),
        new JButton("Porte", icones.getPorte()),
        new JButton("Fenetre", icones.getFenetre()),
        // new JButton("Ajout 6"),
        // new JButton("Ajout 7"),
        // new JButton("Ajout 8")
    };

    private JButton[] buttonsSet2 = {
        new JButton("Porte", icones.getPorte()),
        new JButton("Fenetre", icones.getFenetre()),
        new JButton("Plus", icones.getPlus()),
        new JButton("Moins", icones.getMoins())
    };


    public ToolBar(int ind) {
        if(ind == 1){
            initializeToolBar(buttonsSet1);
        }
        else if(ind == 2){
            initializeToolBar(buttonsSet2);
        }
    }

    private void initializeToolBar(JButton[] initialButtons) {
        for (JButton button : initialButtons) {
            buttons.add(button);
            toolbar.add(button);
        }
    }

    public void addButton(JButton button) {
        ManipList.addElement(buttons, button);
        toolbar.add(button);
    }

    public void removeButton(int index) {
        ManipList.removeElement(buttons, index);
        toolbar.remove(index);
    }

    public void modifyButton(int index, JButton newButton) {
        ManipList.modifyElement(buttons, index, newButton);
        toolbar.remove(index);
        toolbar.add(newButton, index);
    }

    public List<JButton> listButtons() {
        return buttons;
    }

    public JToolBar getToolBar() {
        return toolbar;
    }
}
