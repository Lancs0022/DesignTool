package controleurs;

import view.ToolBar;
import plan.Plan;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controleur {
    private ToolBar toolbar;
    private ToolBar toolBar2;
    private Plan plan;

    public Controleur(Plan plan, ToolBar toolBar, ToolBar toolBar2) {
        this.toolbar = toolBar;
        this.toolBar2 = toolBar2;
        this.plan = plan;

        initializeActions();
    }

    private void initializeActions() {
        List<JButton> buttons = toolbar.listButtons();
        List<JButton> buttons2 = toolBar2.listButtons();
        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Un bouton a été appuyé : " + e.getActionCommand());
                    handleButtonAction(e.getActionCommand());
                }
            });
        }
        for(JButton button : buttons2){
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    System.out.println("Un bouton a été appuyé : " + e.getActionCommand());
                    handleButtonAction2(e.getActionCommand());
                }
            });
        }
    }

    private void handleButtonAction(String actionCommand) {
        System.out.println("Le nouveau thread pour le drawer va être lancé...");
        Thread ajoutElemnThread = new Thread(new Drawer(actionCommand, plan.getElements(), this.plan));
        ajoutElemnThread.start();
        System.out.println("Le thread devrait normalement fonctionner à ce point");
    }

    private void handleButtonAction2(String actionCommand) {
        int pixelsParMetre = this.plan.getParametres().getPixelsParMetre();
        switch (actionCommand) {
            case "Zoom -":
                if(pixelsParMetre == 5){
                    break;
                }
                else{
                    this.plan.getParametres().setPixelsParMetre(pixelsParMetre - 5);
                    this.plan.repaint();
                }
            break;
            
            case "Zoom +":
            if(pixelsParMetre > 100){
                break;
            }
            else{
                this.plan.getParametres().setPixelsParMetre(pixelsParMetre + 5);
                this.plan.repaint();
            }
            default:
                break;
        }
    }
}
