package dessinables.elementsplan;

import dessinables.Dessin;
import dessinables.geometrie.Figure;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;

public abstract class ElementDuPlan extends Dessin {
    protected List<Figure> figures;
    protected List<ElementDuPlan> contenus;
    
    public ElementDuPlan() {
        this.figures = new ArrayList<>();
        this.contenus = new ArrayList<>();
    }

    public abstract void howToDraw(Graphics g);

    public void ajouterFigure(Figure figure) {
        this.figures.add(figure);
    }

    public void ajouterContenu(ElementDuPlan contenu) {
        this.contenus.add(contenu);
    }

    @Override
    public void dessiner(Graphics g) {
        for (Figure figure : figures) {
            figure.dessiner(g);
        }
        for (ElementDuPlan contenu : contenus) {
            contenu.dessiner(g);
        }
    }
}