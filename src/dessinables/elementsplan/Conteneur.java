package dessinables.elementsplan;

import java.util.List;

import dessinables.geometrie.RectangleEpais;

public interface Conteneur {
    List<Contenu> getElementsFilles();
    boolean ajouterElement(ElementDuPlan element);
    RectangleEpais getRectangle();
    
}
