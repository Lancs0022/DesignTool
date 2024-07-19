package controleurs;

import dessinables.Dessin;
import dessinables.elementsplan.ElementDuPlan;
import dessinables.elementsplan.Maison;
import dessinables.elementsplan.Piece;
import dessinables.elementsplan.Terrain;
import dessinables.geometrie.Figure;
import dessinables.geometrie.Point;
import dessinables.geometrie.RectangleEpais;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Drawer implements Runnable {
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> comboBox;
    private JTextField nomField;
    private JTextField xField;
    private JTextField yField;
    private JTextField longueurField;
    private JTextField largeurField;
    private JButton submitButton;
    private String currentType;
    private ActionListener submitAction;

    public Drawer() {
        initializeForm();
    }

    @Override
    public void run() {
        frame.setVisible(true);
    }

    private void initializeForm() {
        frame = new JFrame("Constructeur");
        panel = new JPanel(new GridLayout(7, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);

        nomField = new JTextField();
        xField = new JTextField();
        yField = new JTextField();
        longueurField = new JTextField();
        largeurField = new JTextField();
        submitButton = new JButton("Soumettre");

        frame.add(panel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (submitAction != null) {
                    submitAction.actionPerformed(e);
                }
            }
        });
    }

    public void setTerrainFormFields(List<ElementDuPlan> terrainsExistants) {
        panel.removeAll();
        currentType = "terrain";

        panel.add(new JLabel("Nom du terrain:"));
        nomField.setText("Terrain " + (terrainsExistants.size() + 1));
        panel.add(nomField);

        addCommonFields();

        panel.add(submitButton);

        frame.revalidate();
        frame.repaint();
    }

    public void setMaisonFormFields(List<ElementDuPlan> terrainsExistants, List<ElementDuPlan> maisonsExistantes) {
        panel.removeAll();
        currentType = "maison";

        panel.add(new JLabel("Terrain:"));
        String[] terrains = new String[terrainsExistants.size()];
        for (int i = 0; i < terrainsExistants.size(); i++) {
            terrains[i] = terrainsExistants.get(i).getNom();
        }
        comboBox = new JComboBox<>(terrains);
        panel.add(comboBox);

        panel.add(new JLabel("Nom de la maison:"));
        nomField.setText("Maison " + (maisonsExistantes.size() + 1));
        panel.add(nomField);

        addCommonFields();

        panel.add(submitButton);

        frame.revalidate();
        frame.repaint();
    }

    public void setPieceFormFields(List<ElementDuPlan> maisonsExistantes) {
        panel.removeAll();
        currentType = "piece";

        panel.add(new JLabel("Maison:"));
        String[] maisons = new String[maisonsExistantes.size()];
        for (int i = 0; i < maisonsExistantes.size(); i++) {
            maisons[i] = maisonsExistantes.get(i).getNom();
        }
        comboBox = new JComboBox<>(maisons);
        panel.add(comboBox);

        panel.add(new JLabel("Nom de la pièce:"));
        nomField.setText("Chambre " + (maisonsExistantes.size() + 1));
        panel.add(nomField);

        addCommonFields();

        panel.add(submitButton);

        frame.revalidate();
        frame.repaint();
    }

    private void addCommonFields() {
        panel.add(new JLabel("X:"));
        panel.add(xField);
        panel.add(new JLabel("Y:"));
        panel.add(yField);
        panel.add(new JLabel("Longueur:"));
        panel.add(longueurField);
        panel.add(new JLabel("Largeur:"));
        panel.add(largeurField);
    }

    public void setSubmitAction(ActionListener submitAction) {
        this.submitAction = submitAction;
    }

    // Méthode pour créer les objets correspondants directement
    public ElementDuPlan createElement(List<ElementDuPlan> existants) {
        String nom = nomField.getText();
        int x = Integer.parseInt(xField.getText());
        int y = Integer.parseInt(yField.getText());
        int longueur = Integer.parseInt(longueurField.getText());
        int largeur = Integer.parseInt(largeurField.getText());

        switch (currentType) {
            case "terrain":
                return createTerrain(nom, x, y, longueur, largeur, existants);
            case "maison":
                String terrainParent = comboBox.getSelectedItem().toString();
                return createMaison(nom, x, y, longueur, largeur, terrainParent, existants);
            case "piece":
                String maisonParent = comboBox.getSelectedItem().toString();
                return createPiece(nom, x, y, longueur, largeur, maisonParent, existants);
            default:
                return null;
        }
    }

    private Terrain createTerrain(String nom, int x, int y, int longueur, int largeur, List<ElementDuPlan> existants) {
        Point ptDepart = new Point(100, 100);
        while (isSuperposed(ptDepart, longueur, largeur, existants)) {
            ptDepart.translate(largeur + 10, 0); // Déplace vers la droite
        }
        return new Terrain(ptDepart, longueur, largeur, nom);
    }

    private Maison createMaison(String nom, int x, int y, int longueur, int largeur, String parentName, List<ElementDuPlan> existants) {
        ElementDuPlan parent = findByName(parentName, existants);
        if (parent != null && parent instanceof Terrain) {
            Point ptDepart = new Point(x, y);
            while (isSuperposed(ptDepart, longueur, largeur, ((Terrain) parent).getMaisons())) {
                ptDepart.translate(largeur + 10, 0); // Déplace vers la droite
            }
            return new Maison(ptDepart, longueur, largeur, nom, (Terrain) parent);
        }
        return null;
    }

    private Piece createPiece(String nom, int x, int y, int longueur, int largeur, String parentName, List<ElementDuPlan> existants) {
        ElementDuPlan parent = findByName(parentName, existants);
        if (parent != null && parent instanceof Maison) {
            Point ptDepart = new Point(x, y);
            while (isSuperposed(ptDepart, longueur, largeur, ((Maison) parent).getPieces())) {
                ptDepart.translate(largeur + 10, 0); // Déplace vers la droite
            }
            return new Piece(ptDepart, longueur, largeur, nom, (Maison) parent);
        }
        return null;
    }

    private boolean isSuperposed(Point ptDepart, int longueur, int largeur, List<? extends ElementDuPlan> elements) {
        RectangleEpais newRect = new RectangleEpais(ptDepart, longueur, largeur, 1.0f);
        for (ElementDuPlan element : elements) {
            for (Figure figure : element.getFigures()) {
                if (figure instanceof RectangleEpais && newRect.intersects((RectangleEpais) figure)) {
                    return true;
                }
            }
        }
        return false;
    }

    private ElementDuPlan findByName(String name, List<ElementDuPlan> elements) {
        for (ElementDuPlan element : elements) {
            if (element.getNom().equals(name)) {
                return element;
            }
        }
        return null;
    }
}
