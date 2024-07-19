package view;

import javax.swing.*;

import dessinables.Dessin;
import dessinables.elementsplan.ElementDuPlan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Formulaire {
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> comboBox;
    private JTextField nomField;
    private JTextField xField;
    private JTextField yField;
    private JTextField longueurField;
    private JTextField largeurField;
    private JButton submitButton;
    private String xmlSpecs;
    private String currentType;
    private ActionListener submitAction;

    public Formulaire() {
        initializeForm();
    }

    private void initializeForm() {
        frame = new JFrame("Formulaire");
        panel = new JPanel(new GridLayout(7, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);

        nomField = new JTextField();
        xField = new JTextField();
        yField = new JTextField();
        longueurField = new JTextField();
        largeurField = new JTextField();
        submitButton = new JButton("Soumettre");

        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("X:"));
        panel.add(xField);
        panel.add(new JLabel("Y:"));
        panel.add(yField);
        panel.add(new JLabel("Longueur:"));
        panel.add(longueurField);
        panel.add(new JLabel("Largeur:"));
        panel.add(largeurField);
        panel.add(new JLabel(""));
        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enregistrerSpecifications();
                if (submitAction != null) {
                    submitAction.actionPerformed(e);
                }
            }
        });
    }

    public void setFormFields(String type, List<Dessin> terrainsExistants, List<Dessin> maisonsExistantes) {
        panel.removeAll();
        currentType = type;
    
        if (type.equals("terrain")) {
            panel.add(new JLabel("Nom du terrain:"));
            nomField.setText("Terrain " + (terrainsExistants.size() + 1));
            panel.add(nomField);
        } else if (type.equals("maison")) {
            panel.add(new JLabel("Terrain:"));
            String[] terrains = new String[terrainsExistants.size()];
            for (int i = 0; i < terrainsExistants.size(); i++) {
                terrains[i] = ((ElementDuPlan) terrainsExistants.get(i)).getNom();
            }
            comboBox = new JComboBox<>(terrains);
            panel.add(comboBox);
            panel.add(new JLabel("Nom de la maison:"));
            nomField.setText("Maison " + (maisonsExistantes.size() + 1));
            panel.add(nomField);
        } else if (type.equals("piece")) {
            panel.add(new JLabel("Maison:"));
            String[] maisons = new String[maisonsExistantes.size()];
            for (int i = 0; i < maisonsExistantes.size(); i++) {
                maisons[i] = ((ElementDuPlan) maisonsExistantes.get(i)).getNom();
            }
            comboBox = new JComboBox<>(maisons);
            panel.add(comboBox);
            panel.add(new JLabel("Nom de la pièce:"));
            nomField.setText("Chambre " + (maisonsExistantes.size() + 1));
            panel.add(nomField);
        }
    
        panel.add(new JLabel("X:"));
        panel.add(xField);
        panel.add(new JLabel("Y:"));
        panel.add(yField);
        panel.add(new JLabel("Longueur:"));
        panel.add(longueurField);
        panel.add(new JLabel("Largeur:"));
        panel.add(largeurField);
        panel.add(new JLabel(""));
        panel.add(submitButton);
    
        frame.revalidate();
        frame.repaint();
    }

    private void enregistrerSpecifications() {
        String nom = nomField.getText();
        String x = xField.getText();
        String y = yField.getText();
        String longueur = longueurField.getText();
        String largeur = largeurField.getText();
        String parent = comboBox != null ? comboBox.getSelectedItem().toString() : null;

        StringBuilder builder = new StringBuilder();
        builder.append("<").append(currentType).append(">\n")
               .append("    <nom>").append(nom).append("</nom>\n")
               .append("    <x>").append(x).append("</x>\n")
               .append("    <y>").append(y).append("</y>\n")
               .append("    <longueur>").append(longueur).append("</longueur>\n")
               .append("    <largeur>").append(largeur).append("</largeur>\n");
        if (parent != null) {
            builder.append("    <parent>").append(parent).append("</parent>\n");
        }
        builder.append("</").append(currentType).append(">");
        
        xmlSpecs = builder.toString();
    }

    public void setSubmitAction(ActionListener submitAction) {
        this.submitAction = submitAction;
    }

    public String getSpecifications() {
        return xmlSpecs;
    }
}