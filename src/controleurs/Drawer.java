package controleurs;

import dessinables.elementsplan.Conteneur;
import dessinables.elementsplan.Contenu;
import dessinables.elementsplan.ElementDuPlan;
import dessinables.elementsplan.Fenetre;
import dessinables.elementsplan.Maison;
import dessinables.elementsplan.Ouverture;
import dessinables.elementsplan.Piece;
import dessinables.elementsplan.Porte;
import dessinables.elementsplan.Terrain;
import dessinables.geometrie.Figure;
import dessinables.geometrie.Point;
import dessinables.geometrie.RectangleEpais;
import dessinables.geometrie.Vecteur;
import outils.CalculsVectoriels;
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
    // Attributs
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> comboBox;
    private JComboBox<String> faceComboBox;
    private JTextField nomField;
    private JTextField longueurField;
    private JTextField largeurField;
    private JTextField nombreElement = new JTextField("1");
    private JButton submitButton;
    private String currentType;
    private List<ElementDuPlan> elements;
    private String typeFormulaire;
    private Plan localPlan;

    // Constructeur
    public Drawer(String typeFormulaire, List<ElementDuPlan> elements, Plan plan) {
        this.elements = elements;
        this.localPlan = plan;
        this.typeFormulaire = typeFormulaire;
        frame = new JFrame("Constructeur");
        panel = new JPanel(new GridLayout(6, 2));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 250);
        faceComboBox = new JComboBox<String>();
        nomField = new JTextField();
        longueurField = new JTextField();
        largeurField = new JTextField();
        submitButton = new JButton("Soumettre");

        frame.add(panel);
    }

    // Lancement du thread
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

    private String genererNomAutomatique(String type) {
        int count = 0;
        for (ElementDuPlan element : elements) {
            if (element.getClass().getSimpleName().equals(type)) {
                count++;
            }
        }
        return type + " " + (count + 1);
    }

    /*---------------------------------   Set de formulaires   -------------------------------------*/
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

    private void setFormFields3(String type, List<? extends ElementDuPlan> parentElements) {
        panel.removeAll();
        currentType = type;

        panel.add(new JLabel("Pièces:"));
        String[] parents = getParentNames(parentElements);
        comboBox = new JComboBox<>(parents);
        panel.add(comboBox);

        panel.add(new JLabel("Largeur:"));
        panel.add(largeurField);

        panel.add(new JLabel("Ajouter à la face :"));
        faceComboBox = new JComboBox<>(new String[] { "Nord", "Sud", "Est", "Ouest" });
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

    /*
     * -----------------------------------------------------------------------------
     * ---------------------------------
     */

    /*
     * ------------------------------------- Ce qui se passe lors d'un submit
     * -------------------------------------
     */
    private class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Soumission des spécifications");
            String nom = nomField.getText();
            double longueur = 0, largeur;
            Point ptDepart = new Point();
            Conteneur parent = null;
            int nombre;
    
            // Récupération de la valeur des champs de formulaire et gestion d'exception
            try {
                // Si une porte et une fenêtre n'ont pas de longueur
                if (!"Porte".equals(currentType) && !"Fenetre".equals(currentType)) {
                    longueur = Double.parseDouble(longueurField.getText());
                }
                largeur = Double.parseDouble(largeurField.getText());
            } catch (NumberFormatException ex) {
                showErrorDialog("Veuillez entrer des valeurs numériques valides pour la longueur et la largeur.");
                return;
            }
    
            // On initialise le nombre d'éléments à générer
            try {
                nombre = Integer.parseInt(nombreElement.getText());
            } catch (NumberFormatException ex) {
                showErrorDialog("Veuillez entrer une valeur numérique valide pour le nombre.");
                return;
            }
    
            // Tous les éléments à part le terrain ont obligatoirement un parent
            if (!"Terrain".equals(currentType)) {
                parent = findParentElementByType(currentType);
                if (parent == null) {
                    showErrorDialog("Aucun parent sélectionné.");
                    return;
                }
            }
    
            for (int i = 1; i <= nombre; i++) {
                // Détermination du point de départ de l'élément à créer
                System.out.println("Création d'élément");
                ptDepart = determineStartingPoint((int) longueur, (int) largeur, parent);
    
                // Création de l'élément ou de l'ouverture avec les données recueillies
                try {
                    if ("Porte".equals(currentType) || "Fenetre".equals(currentType)) {
                        // Récupérer la face sélectionnée
                        String face = (String) (faceComboBox.getSelectedItem());
                        ptDepart = trouverPointDeDepartIdeal((Piece) parent, face, largeur);
                        createOuvertures(currentType, ptDepart, largeur, nom, parent, face);
                    } else {
                        ElementDuPlan element = createElement(currentType, ptDepart, longueur, largeur, nom, parent);
                        if (element != null) {
                            localPlan.ajouterDessin(element);
                            frame.dispose();
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    showErrorDialog(ex.getMessage());
                }
            }
        }
    }

    public ElementDuPlan createElement(String type, Point ptDepart, double largeur, double hauteur, String nom,
            Conteneur parent) {
        largeur = largeur * ParametresPlan.getPixelsParMetre();
        hauteur = hauteur * ParametresPlan.getPixelsParMetre();
        try {
            switch (type) {
                case "Terrain":
                    return new Terrain(ptDepart, (int) largeur, (int) hauteur, nom);
                case "Maison":
                    Maison maison = new Maison(ptDepart, largeur, hauteur, nom, parent);
                    if (parent.ajouterContenu(maison)) {
                        return maison;
                    } else {
                        throw new IllegalArgumentException(
                                "Impossible d'ajouter la maison, collision détectée ou hors des limites du terrain.");
                    }
                case "Pièce":
                    Piece piece = new Piece(ptDepart, largeur, hauteur, nom, parent);
                    if ((parent.ajouterContenu(piece))) {
                        return piece;
                    } else {
                        throw new IllegalArgumentException(
                                "Impossible d'ajouter la pièce, collision détectée ou hors des limites de la maison.");
                    }
                default:
                    throw new IllegalArgumentException("Type d'élément inconnu: " + type);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void createOuvertures(String type, Point ptDepart, double largeur, String nom, Conteneur parent, String face) {
        try {
            Ouverture ouverture = null;
            switch (type) {
                case "Fenetre":
                    ouverture = new Fenetre(ptDepart, largeur, face , nom, parent);
                    break;
                case "Porte":
                    ouverture = new Porte(ptDepart, largeur, face , nom, parent);
                    break;
                default:
                    throw new IllegalArgumentException("Type d'ouverture inconnu: " + type);
            }
    
            if (ouverture != null) {
                System.out.println("Vecteur ouverture créé : " + ouverture.getVecteur().toString());
                boolean ajoutReussi = genererOuverture(parent, face, ouverture);
            if (ajoutReussi) {
                System.out.println("");
                localPlan.ajouterDessin((ElementDuPlan) ouverture);
                frame.dispose();
            } else {
                throw new IllegalArgumentException("Impossible de placer l'ouverture.");
            }
        }
    } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(frame, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    }

    private Point determineStartingPoint(int longueur, int largeur, Conteneur parent) {
        int step = 10;
        RectangleEpais parentRect;
        if (parent != null) {
            parentRect = parent.getRectangle();
        } else {
            parentRect = new RectangleEpais(new Point(0, 0), 10000000, 10000000, 1.0f);
        }
        for (int y = parentRect.getMinY(); y <= parentRect.getMaxY() - largeur; y += step) {
            for (int x = parentRect.getMinX(); x <= parentRect.getMaxX() - longueur; x += step) {

                RectangleEpais rect = new RectangleEpais(new Point(x, y), longueur, largeur, 1.0f);
                if (parent == null) {
                    if (isPositionFreeForTerrain(rect)) {
                        return new Point(x, y);
                    }
                } else if (isPositionFree(rect, (Conteneur) parent)) {
                    return new Point(x, y);
                }
            }
        }
        return new Point(parentRect.getMinX(), parentRect.getMinY()); // Position par défaut en cas d'échec
    }

    private boolean isPositionFree(RectangleEpais rect, Conteneur parent) {
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

    public Point trouverPointDeDepartIdeal(Piece parent, String face, double largeurOuverture) {
    List<Ouverture> ouvertures = parent.getOuvertures(face);
    RectangleEpais rectangle = parent.getRectangle();

    Point pointDeDepart;
    Point p1, p2;
    switch (face.toLowerCase()) {
        case "nord":
            p1 = rectangle.getP1();
            p2 = rectangle.getP2();
            break;
        case "sud":
            p1 = rectangle.getP4();
            p2 = rectangle.getP3();
            // System.out.println("Pour le cas sud : " + new Vecteur(p1, p2).toString());
            break;
        case "ouest":
            p1 = rectangle.getP1();
            p2 = rectangle.getP4();
            break;
        case "est":
            p1 = rectangle.getP2();
            p2 = rectangle.getP3();
            break;
        default:
            throw new IllegalArgumentException("Face inconnue : " + face);
    }

    Vecteur vecteurFace = new Vecteur(p1, p2);
    System.out.println("Coordonnées de la face : " + vecteurFace);
    double largeurFace = vecteurFace.getLongueur();

    if (ouvertures.isEmpty()) {
        // Pas d'ouvertures existantes, positionner à 10% après le début de la face
        pointDeDepart = vecteurFace.getP1().decaler(largeurFace * 0.10, vecteurFace.getDirection());
    } else {
        // Trouver la dernière ouverture sur la face
        Ouverture derniereOuverture = ouvertures.get(ouvertures.size() - 1);
        Point p2DerniereOuverture = derniereOuverture.getVecteur().getP2();

        // Positionner à 5% après le point p2 de la dernière ouverture
        pointDeDepart = p2DerniereOuverture.decaler(largeurFace * 0.05, vecteurFace.getDirection());
    }

    // // Si l'ouverture dépasse la longueur de la face, ajuster sa position pour qu'elle soit entièrement sur la face
    // if (CalculsVectoriels.mesurerVecteur(new Vecteur(p1, pointDeDepart)) + largeurOuverture > largeurFace) {
    //     pointDeDepart = vecteurFace.getP1().decaler(largeurFace - largeurOuverture, vecteurFace.getDirection());
    // }

    System.out.println("Face: " + face + " - Point de départ: " + pointDeDepart);
    return pointDeDepart;
}

    public boolean genererOuverture(Conteneur parent, String face, Ouverture ouverture) {
        // Trouver le point de départ idéal pour l'ouverture sur la face spécifiée
        Point pointDeDepart = trouverPointDeDepartIdeal((Piece) parent, face, CalculsVectoriels.mesurerVecteur(ouverture.getVecteur()));
    
        int largeurOuverture = (int) ouverture.getVecteur().getLongueur();
    
        // Ajuster le calcul du point de fin en fonction de la direction
        Point pointDeFin;
        if ("sud".equalsIgnoreCase(face) || "nord".equalsIgnoreCase(face)) {
            // Pour les faces horizontales, modifier seulement la coordonnée X
            pointDeFin = new Point(pointDeDepart.getX() + largeurOuverture, pointDeDepart.getY());
        } else {
            // Pour les faces verticales, modifier seulement la coordonnée Y
            pointDeFin = new Point(pointDeDepart.getX(), pointDeDepart.getY() + largeurOuverture);
        }
    
        // Mettre à jour le vecteur de l'ouverture
        ouverture.getVecteur().setP1(pointDeDepart);
        ouverture.getVecteur().setP2(pointDeFin);
    
        // Vérifier si l'ouverture peut être ajoutée
        if (((Piece) parent).peutPlacerOuverture(ouverture, parent, face)) {
            if (parent.ajouterOuverture(ouverture)) {
                System.out.println("Ouverture ajoutée avec succès.");
                return true;
            } else {
                System.out.println("Impossible d'ajouter l'ouverture au conteneur.");
            }
        } else {
            System.out.println("L'ouverture ne peut pas être placée sur la face spécifiée.");
        }
        return false;
    }

    public boolean peutPlacerOuverture(Ouverture ouverture, Conteneur parent, String face) {
        Vecteur vecteurOuverture = ouverture.getVecteur();
        Vecteur faceVecteur = parent.trouverFace(face);

        // Vérifie que l'ouverture est dans les limites de la face
        return CalculsVectoriels.vectSontAlignes(vecteurOuverture, faceVecteur) &&
                vecteurOuverture.getP1().getX() >= faceVecteur.getP1().getX() &&
                vecteurOuverture.getP2().getX() <= faceVecteur.getP2().getX() &&
                vecteurOuverture.getP1().getY() >= faceVecteur.getP1().getY() &&
                vecteurOuverture.getP2().getY() <= faceVecteur.getP2().getY();
    }

    /*
     * ---------------------------------------- Méthodes de recherche
     * ---------------------------------------------
     */
    // Méthode pour obtenir les noms des éléments parent
    private String[] getParentNames(List<? extends ElementDuPlan> parentElements) {
        String[] names = new String[parentElements.size()];
        for (int i = 0; i < parentElements.size(); i++) {
            names[i] = parentElements.get(i).getNom();
        }
        return names;
    }

    // Méthode pour trouver un élément parent par nom
    private <T extends ElementDuPlan> T findParentElement(Class<T> type) {
        if (comboBox == null)
            return null;
        String parentName = (String) comboBox.getSelectedItem();
        for (T element : getElements(type, elements)) {
            if (element.getNom().equals(parentName)) {
                return element;
            }
        }
        return null;
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
    /*
     * -----------------------------------------------------------------------------
     * ---------------------------------
     */

    // Boîte de dialogue simple pour afficher une erreur
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}