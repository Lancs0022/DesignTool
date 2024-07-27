package controleurs;

import dessinables.elementsplan.Conteneur;
import dessinables.elementsplan.Contenu;
import dessinables.elementsplan.ElementDuPlan;
import dessinables.elementsplan.Fenetre;
import dessinables.elementsplan.Maison;
import dessinables.elementsplan.Piece;
import dessinables.elementsplan.Terrain;
import dessinables.geometrie.Figure;
import dessinables.geometrie.Point;
import dessinables.geometrie.RectangleEpais;
import plan.Plan;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class Drawer implements Runnable {
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> comboBox;
    private JTextField nomField;
    private JTextField longueurField;
    private JTextField largeurField;
    private JButton submitButton;
    private String currentType;
    private List<ElementDuPlan> elements;
    private String typeFormulaire;
    private Plan localPlan;

    public Drawer(String typeFormulaire, List<ElementDuPlan> elements, Plan plan) {
        this.elements = elements;
        this.localPlan = plan;
        this.typeFormulaire = typeFormulaire;
        frame = new JFrame("Constructeur");
        panel = new JPanel(new GridLayout(6, 2));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 250);
        nomField = new JTextField();
        longueurField = new JTextField();
        largeurField = new JTextField();
        submitButton = new JButton("Soumettre");

        frame.add(panel);
    }

    public <T extends ElementDuPlan> List<T> getElements(Class<T> type, List<ElementDuPlan> elements) {
        return elements.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    @Override
    public void run() {
        switch (typeFormulaire) {
            case "Terrain":
                setFormFields("Terrain", getElements(Terrain.class, elements).size() + 1);
                break;
            case "Maison":
                setFormFields("Maison", getElements(Terrain.class, elements));
                break;
            case "Pièce":
                setFormFields("Pièce", getElements(Maison.class, elements));
                break;
            case "Porte":
                setFormFields("Porte", getElements(Piece.class, elements));
                break;
            case "Fenetre":
                setFormFields("Fenetre", getElements(Piece.class, elements));
                break;
            default:
                break;
        }
    }

    private void setFormFields(String type, int elementCount) {
        panel.removeAll();
        currentType = type;

        panel.add(new JLabel("Nom du " + type.toLowerCase() + ":"));
        nomField.setText(type + " " + elementCount);
        panel.add(nomField);

        addCommonFields();

        panel.add(submitButton);
        submitButton.addActionListener(new SubmitAction());

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void setFormFields(String type, List<? extends ElementDuPlan> parentElements) {
        panel.removeAll();
        currentType = type;

        if (!type.equals("Terrain")) {
            panel.add(new JLabel(type.equals("Maison") ? "Terrain:" : "Maison:"));
            String[] parents = parentElements.stream()
                    .map(ElementDuPlan::getNom)
                    .toArray(String[]::new);
            comboBox = new JComboBox<>(parents);
            panel.add(comboBox);
        }

        panel.add(new JLabel("Nom de la " + type.toLowerCase() + ":"));
        nomField.setText(type);
        panel.add(nomField);

        addCommonFields();

        panel.add(submitButton);
        submitButton.addActionListener(new SubmitAction());

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void addCommonFields() {
        panel.add(new JLabel("Longueur:"));
        panel.add(longueurField);
        panel.add(new JLabel("Largeur:"));
        panel.add(largeurField);
    }

    private class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nom = nomField.getText();
            double longueur, largeur;
            try {
                longueur = Double.parseDouble(longueurField.getText());
                largeur = Double.parseDouble(largeurField.getText());
            } catch (NumberFormatException ex) {
                showErrorDialog("Veuillez entrer des valeurs numériques valides pour la longueur et la largeur.");
                return;
            }
    
            Point ptDepart;
            Conteneur parent = null;
    
            if (!"Terrain".equals(currentType)) {
                parent = findParentElementByType(currentType);
                if (parent == null) {
                    showErrorDialog("Aucun parent sélectionné.");
                    return;
                }
            }

            ptDepart = determineStartingPoint((int) longueur, (int) largeur, parent);

            try {
                ElementDuPlan element = createElement(currentType, ptDepart, longueur, largeur, nom, parent);
                if (element != null) {
                    localPlan.ajouterDessin(element);
                    frame.dispose();
                }
            } catch (IllegalArgumentException ex) {
                showErrorDialog(ex.getMessage());
            }
        }
    }
    

    private Conteneur findParentElementByType(String elementType) {
        switch (elementType) {
            case "Maison":
                return findParentElement(Terrain.class);
            case "Pièce":
                return findParentElement(Maison.class);
            case "Porte":
                return findParentElement(Piece.class);
            case "Fenetre":
                return findParentElement(Piece.class);
            default:
                return null;
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public ElementDuPlan createElement(String type, Point ptDepart, double largeur, double hauteur, String nom, Conteneur parent) {
        largeur = largeur * localPlan.getParametres().getPixelsParMetre();
        hauteur = hauteur * localPlan.getParametres().getPixelsParMetre();
        try {
            switch (type) {
                case "Terrain":
                    return new Terrain(ptDepart, (int) largeur, (int) hauteur, nom);
                case "Maison":
                    Maison maison = new Maison(ptDepart, largeur, hauteur, nom, parent);
                    if (parent.ajouterElement(maison)) {
                        return maison;
                    } else {
                        throw new IllegalArgumentException(
                                "Impossible d'ajouter la maison, collision détectée ou hors des limites du terrain.");
                    }
                case "Pièce":
                    Piece piece = new Piece(ptDepart, largeur, hauteur, nom, parent);
                    if ((parent.ajouterElement(piece))) {
                        return piece;
                    } else {
                        throw new IllegalArgumentException(
                                "Impossible d'ajouter la pièce, collision détectée ou hors des limites de la maison.");
                    }
                case "Fenetre":
                    Fenetre fenetre = new Fenetre(ptDepart, largeur, hauteur, nom, parent);
                    if (parent.ajouterElement(fenetre)) {
                        return fenetre;
                    } else {
                        throw new IllegalArgumentException(
                                "Impossible d'ajouter la fenêtre, hors des limites de la pièce.");
                    }
                default:
                    throw new IllegalArgumentException("Type d'élément inconnu: " + type);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private <T extends ElementDuPlan> T findParentElement(Class<T> type) {
        if (comboBox == null)
            return null;
        String parentName = comboBox.getSelectedItem().toString();
        return getElements(type, elements).stream()
                .filter(element -> element.getNom().equals(parentName))
                .findFirst()
                .orElse(null);
    }

    private Point determineStartingPoint(int longueur, int largeur, Conteneur parent) {
        int step = 10;
        RectangleEpais parentRect;
        if(parent != null){
            parentRect = parent.getRectangle();
        }
        else{
            parentRect = new RectangleEpais(new Point(0, 0), 10000000, 10000000, 1.0f);
        }

        for (int x = parentRect.getMinX(); x <= parentRect.getMaxX() - longueur; x += step) {
            for (int y = parentRect.getMinY(); y <= parentRect.getMaxY() - largeur; y += step) {
                RectangleEpais rect = new RectangleEpais(new Point(x, y), longueur, largeur, 1.0f);
                if(parent == null){
                    if (isPositionFreeForTerrain(rect)) {
                        return new Point(x, y);
                    }
                }
                else
                    if (isPositionFree(rect, (Conteneur) parent)) {
                        return new Point(x, y);
                    }
            }
        }
        return new Point(parentRect.getMinX(), parentRect.getMinY()); // Position par défaut en cas d'échec
    }

    public static boolean isPositionFree(RectangleEpais rect, Conteneur parent) {
        for (Contenu contenu : parent.getElementsFilles()) {
            if (contenu instanceof ElementDuPlan) {
                ElementDuPlan element = (ElementDuPlan) contenu;
                for (Figure figure : element.getFigures()) {
                    if (figure instanceof RectangleEpais && ((RectangleEpais) figure).intersects(rect)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isPositionFreeForTerrain(RectangleEpais rect) {
        for (ElementDuPlan element : elements) {
            if (element instanceof Terrain) {
                for (Figure figure : element.getFigures()) {
                    if (figure instanceof RectangleEpais && ((RectangleEpais) figure).intersects(rect)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}