package controleurs;

import dessinables.elementsplan.Conteneur;
import dessinables.elementsplan.Contenu;
import dessinables.elementsplan.ElementDuPlan;
import dessinables.elementsplan.Fenetre;
import dessinables.elementsplan.Maison;
import dessinables.elementsplan.Piece;
import dessinables.elementsplan.Porte;
import dessinables.elementsplan.Terrain;
import dessinables.geometrie.Figure;
import dessinables.geometrie.Point;
import dessinables.geometrie.RectangleEpais;
import dessinables.geometrie.Vecteur;
import plan.ParametresPlan;
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
import java.util.ArrayList;
import java.util.List;

public class Drawer implements Runnable {
    private JFrame frame;
    private JPanel panel;

    JTextField nombreElement = new JTextField("1");
    private JComboBox<String> comboBox;
    private JTextField nomField;
    private JTextField longueurField;
    private JTextField largeurField;
    JComboBox<String> faceComboBox;
    private JButton submitButton;

    private String typeFormulaire;
    private Plan localPlan;
    private String currentType;
    private List<ElementDuPlan> elements;

    public Drawer(String typeFormulaire, List<ElementDuPlan> elements, Plan plan) {
        this.elements = elements;
        this.localPlan = plan;
        this.typeFormulaire = typeFormulaire;
        this.frame = new JFrame("Constructeur");
        this.panel = new JPanel(new GridLayout(6, 2));
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(300, 250);
        this.nomField = new JTextField();
        this.longueurField = new JTextField();
        this.largeurField = new JTextField();
        this.submitButton = new JButton("Soumettre");
        this.frame.add(panel);
    }

    // Méthode pour obtenir les éléments d'un type donné
    public <T extends ElementDuPlan> List<T> getElements(Class<T> type, List<ElementDuPlan> elements) {
        List<T> result = new ArrayList<>();
        for (ElementDuPlan element : elements) {
            if (type.isInstance(element)) {
                result.add(type.cast(element));
            }
        }
        return result;
    }

    @Override
    public void run() {
        switch (typeFormulaire) {
            case "Terrain":
                setFormFields1("Terrain", getElements(Terrain.class, elements).size() + 1);
                break;
            case "Maison":
                setFormFields2("Maison", getElements(Terrain.class, elements));
                break;
            case "Pièce":
                setFormFields2("Pièce", getElements(Maison.class, elements));
                break;
            case "Porte":
                setFormFields3("Porte", getElements(Piece.class, elements));
                break;
            case "Fenetre":
                setFormFields3("Fenetre", getElements(Piece.class, elements));
                break;
            default:
                break;
        }
    }

    private void setFormFields1(String type, int elementCount) {
        panel.removeAll();
        currentType = type;

        panel.add(new JLabel("Nom du " + type.toLowerCase() + ":"));
        nomField.setText(type + " " + elementCount);
        panel.add(nomField);

        addCommonFields();
        panel.add(new JLabel("Nombre :"));
        panel.add(nombreElement);

        panel.add(submitButton);
        submitButton.addActionListener(new SubmitAction());

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void setFormFields2(String type, List<? extends ElementDuPlan> parentElements) {
        panel.removeAll();
        currentType = type;

        if (!type.equals("Terrain")) {
            panel.add(new JLabel(type.equals("Maison") ? "Terrain:" : "Maison:"));
            String[] parents = getParentNames(parentElements);
            comboBox = new JComboBox<>(parents);
            panel.add(comboBox);
        }

        panel.add(new JLabel("Nom de la " + type.toLowerCase() + ":"));

        // Calcul du nombre d'éléments de ce type
        int count = 0;
        for (ElementDuPlan element : elements) {
            if (element.getClass().getSimpleName().equals(type)) {
                count++;
            }
        }
        nomField.setText(type + " " + (count + 1));
        panel.add(nomField);

        addCommonFields();
        panel.add(new JLabel("Nombre :"));
        panel.add(nombreElement);

        panel.add(submitButton);
        submitButton.addActionListener(new SubmitAction());

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void setFormFields3(String type, List<? extends ElementDuPlan> parentElements){
        panel.removeAll();
        currentType = type;

        panel.add(new JLabel("Pièces:"));
        String[] parents = getParentNames(parentElements);
        comboBox = new JComboBox<>(parents);
        panel.add(comboBox);

        panel.add(new JLabel("Largeur:"));
        panel.add(largeurField);

        panel.add(new JLabel("Ajouter à la face :"));
        faceComboBox = new JComboBox<>(new String[]{"Nord", "Sud", "Est", "Ouest"});
        panel.add(faceComboBox);

        panel.add(new JLabel("Nombre :"));
        panel.add(nombreElement);

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
            double longueur = 0, largeur;
            int nombre;
            Point ptDepart;
            Conteneur parent = null;
            try {
                if (!"Porte".equals(currentType) && !"Fenetre".equals(currentType)) {
                    longueur = Double.parseDouble(longueurField.getText());
                }
                largeur = Double.parseDouble(largeurField.getText());
            } catch (NumberFormatException ex) {
                showErrorDialog("Veuillez entrer des valeurs numériques valides pour la longueur et la largeur.");
                return;
            }

            try {
                nombre = Integer.parseInt(nombreElement.getText());
            } catch (NumberFormatException ex) {
                showErrorDialog("Veuillez entrer une valeur numérique valide pour le nombre.");
                return;
            }

            if (!"Terrain".equals(currentType)) {
                parent = findParentElementByType(currentType);
                if (parent == null) {
                    showErrorDialog("Aucun parent sélectionné.");
                    return;
                }
            }

            if ("Porte".equals(currentType) || "Fenetre".equals(currentType)) {
                try {
                    String face = (String) faceComboBox.getSelectedItem();
                    genererElements(currentType, nombre, largeur, face, parent);
                } catch (Exception ex) {
                    showErrorDialog(ex.getMessage());
                }
                frame.dispose();
                return;
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
        largeur = largeur * ParametresPlan.getPixelsParMetre();
        hauteur = hauteur * ParametresPlan.getPixelsParMetre();
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
                    Fenetre fenetre = new Fenetre(ptDepart, largeur, nom, (Piece) parent);
                    if (parent.ajouterElement(fenetre)) {
                        return fenetre;
                    } else {
                        throw new IllegalArgumentException(
                                "Impossible d'ajouter la fenêtre, hors des limites de la pièce.");
                    }
                case "Porte":
                    Porte porte = new Porte(ptDepart, largeur, nom, (Piece) parent);
                    if (parent.ajouterElement(porte)) {
                        return porte;
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

        // Méthode pour trouver un élément parent par nom
    private <T extends ElementDuPlan> T findParentElement(Class<T> type) {
        if (comboBox == null) return null;
        String parentName = (String) comboBox.getSelectedItem();
        for (T element : getElements(type, elements)) {
            if (element.getNom().equals(parentName)) {
                return element;
            }
        }
        return null;
    }

    // Méthode pour obtenir les noms des éléments parent
    private String[] getParentNames(List<? extends ElementDuPlan> parentElements) {
        String[] names = new String[parentElements.size()];
        for (int i = 0; i < parentElements.size(); i++) {
            names[i] = parentElements.get(i).getNom();
        }
        return names;
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

    public void genererElements(String type, int nombre, double largeur, String face, Conteneur parent) throws Exception {
        Vecteur faceVecteur;
        switch (face) {
            case "Nord":
                faceVecteur = parent.getRectangle().getFaceNord();
                break;
            case "Est":
                faceVecteur = parent.getRectangle().getFaceEst();
                break;
            case "Sud":
                faceVecteur = parent.getRectangle().getFaceSud();
                break;
            case "Ouest":
                faceVecteur = parent.getRectangle().getFaceOuest();
                break;
            default:
                throw new IllegalArgumentException("Face inconnue : " + face);
        }
    
        double espaceTotal = faceVecteur.getP1().distance(faceVecteur.getP2());
        int nombreDeSegments = nombre + 1;
        double espaceParSegment = espaceTotal / (nombreDeSegments + nombre * largeur);
    
        if (espaceParSegment < 0) {
            throw new Exception("Pas assez d'espace sur la face " + face + " pour ajouter " + nombre + " éléments.");
        }
    
        double position = espaceParSegment;
        for (int i = 0; i < nombre; i++) {
            Point pointDepart = calculerPointDepart(faceVecteur, position, largeur);
            System.out.println("Position calculée pour l'élément : " + pointDepart);
            System.out.println("Position evalué : " + position);
            System.out.println("largeur : " + largeur);
            ElementDuPlan element;
            if (type.equalsIgnoreCase("porte")) {
                element = new Porte(pointDepart, largeur, face, parent);
            } else if (type.equalsIgnoreCase("fenetre")) {
                element = new Fenetre(pointDepart, largeur, face, parent);
            } else {
                throw new IllegalArgumentException("Type d'élément inconnu : " + type);
            }
            System.out.println("Peut ajouter Element sur face ? " + parent.peutAjouterElementSurFace((Contenu) element, face));
            System.out.println(element.toString());
            if(parent.peutAjouterElementSurFace((Contenu) element, face)){
                parent.ajouterElement(element);
                localPlan.ajouterDessin(element);
            }
            position += largeur + espaceParSegment;
        }
    }

    private Point calculerPointDepart(Vecteur faceVecteur, double position, double largeur) {
        double x = faceVecteur.getP1().getX() + position;
        double y = faceVecteur.getP1().getY();
    
        if (faceVecteur.getP1().getX() == faceVecteur.getP2().getX()) { // Vecteur Vertical
            x = faceVecteur.getP1().getX();
            y = faceVecteur.getP1().getY() + position;
        }
        return new Point((int) x, (int) y);
    }
}