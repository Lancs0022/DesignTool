package view;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToolBar;



public class MenuNavigation{
    private JMenuBar BarreDeMenu = new JMenuBar();
    private JToolBar toolbar;
    private JTextField barreChemin = new JTextField(20);
    private JPanel champsTextuel;
    private JPopupMenu popupMenu = new JPopupMenu();
    private Icones icones = new Icones();

    private JMenu option1 = new JMenu("Fichiers");

    private JMenuItem[] item1 = {
        new JMenuItem("Ouvrir une nouvelle fenetre", icones.getNouvelleFenetre()),
        new JMenuItem("Nouveau", icones.getNouveau()),
        new JMenuItem("Ouvrir", icones.getOuvrir()),
        new JMenuItem("Sauvegarder", icones.getSauvegarder()),
        new JMenuItem("Fermer", icones.getFermer()),
    };

    public MenuNavigation(){
        setAllForJMenuBar();
        createToolbar();
    }

    private void createToolbar() {
        this.toolbar = new JToolBar();
    }

    private void setAllForJMenuBar(){
        for(JMenuItem item : item1){
            this.option1.add(item);
        }
        this.BarreDeMenu.add(option1);
    }

    public JMenuBar getMenu(){
        return this.BarreDeMenu;
    }

    public JToolBar getToolbar() {
        return this.toolbar;
    }

    public JTextField getTextField() {
        return this.barreChemin;
    }

    public JPanel getChampsTextuel() {
        return champsTextuel;
    }

    public JPopupMenu getPopupMenu(){
        return this.popupMenu;
    }

    public JMenuItem[] getItems1(){
        return this.item1;
    }

    public String toString(JMenuItem[] item){
        String details = "";
        for(JMenuItem i : item)
        {
            details = details.concat(i.getText());
            details = details.concat("\n");
        }
        return details;
    }
}