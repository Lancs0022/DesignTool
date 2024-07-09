package dessinables.elementsplan;

import java.awt.Graphics;
import java.util.List;

public class Conteneur extends ElementDuPlan {
    List<ElementDuPlan> contenus;

    public void ajouterContenus(){

    }
    public List<ElementDuPlan> getContenu(){
        return this.contenus;
    }

    public void setContenu(List<ElementDuPlan> contenus){
        this.contenus = contenus;
    }

    @Override
    public void howToDraw(Graphics g) {
        // Logique pour dessiner un conteneur
    }
}
