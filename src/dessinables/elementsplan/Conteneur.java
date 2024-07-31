package dessinables.elementsplan;

import java.util.List;

import dessinables.geometrie.RectangleEpais;
import dessinables.geometrie.Vecteur;
public interface Conteneur {
    List<Contenu> getElementsFilles();
    boolean ajouterContenu(ElementDuPlan element);
    boolean ajouterOuverture(Ouverture ouverture);
    RectangleEpais getRectangle();
    void mettreAJourOuverture(Ouverture ouverture);

    default Vecteur trouverFace(String face) {
        Vecteur faceVecteur;
        switch (face.toLowerCase()) {
            case "nord":
                faceVecteur = getRectangle().getFaceNord();
                break;
            case "est":
                faceVecteur = getRectangle().getFaceEst();
                break;
            case "sud":
                faceVecteur = getRectangle().getFaceSud();
                break;
            case "ouest":
                faceVecteur = getRectangle().getFaceOuest();
                break;
            default:
                throw new IllegalArgumentException("Face inconnue : " + face);
        }
        return faceVecteur;
    }
    // List<Ouverture> getOuvertures(String face);
    
    default String getNom(){
        return this.getNom();
    }
}