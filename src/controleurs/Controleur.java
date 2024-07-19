package controleurs;

import view.*;

import javax.swing.*;

import plan.Plan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controleur {
    private ToolBar toolbar;
    private Formulaire formulaire;
    private Drawer drawer;
    private Plan plan;

    public Controleur() {
        this.toolbar = new ToolBar(1);
        this.formulaire = new Formulaire();
        this.drawer = new Drawer();
        this.plan = new Plan();

        initializeActions();
    }

    public Controleur(Plan plan, ToolBar toolBar, Drawer drawer) {
        this.toolbar = toolBar;
        this.drawer = drawer;
        this.plan = plan;

        initializeActions();
    }

    private void initializeActions() {
        List<JButton> buttons = toolbar.listButtons();
        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonAction(e.getActionCommand());
                }
            });
        }

        formulaire.setSubmitAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}
        });
    }

    private void handleButtonAction(String actionCommand) {
        switch (actionCommand) {
            case "terrain":
                formulaire.setFormFields("terrain", plan.getTerrains(), plan.getMaisons());
                break;
            case "maison":
                formulaire.setFormFields("maison", plan.getTerrains(), plan.getMaisons());
                break;
            case "piece":
                formulaire.setFormFields("piece", plan.getTerrains(), plan.getMaisons());
                break;
            // Ajouter des cas pour "porte" et "fenetre" si nécessaire
            default:
                break;
        }
    }

    public Formulaire getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(Formulaire formulaire) {
        this.formulaire = formulaire;
    }

    public static void main(String[] args) {
        Controleur cont = new Controleur();
        cont.getFormulaire().setFormFields("Terrain", null, null);
    }
}