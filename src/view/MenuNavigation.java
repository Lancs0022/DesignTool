package view;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class MenuNavigation {
    private JMenuBar BarreDeMenu = new JMenuBar();
    private JToolBar toolbar;
    private JTextField barreChemin = new JTextField(20);
    private JPanel champsTextuel;
    private JPopupMenu popupMenu = new JPopupMenu();

    // private JButton boutonRetour;

    private ImageIcon iconeNewfenetre = new ImageIcon("../assets/icons8-new-window-24.png");
    private ImageIcon iconeOpenPowershell = new ImageIcon("../assets/icons8-powershell-ise-24.png");
    private ImageIcon iconeFermer = new ImageIcon("../assets/icons8-close-24(1).png");
    // private ImageIcon iconeRetour = new ImageIcon("../assets/icons8-back-24-7.png");
    private ImageIcon iconeDossier = new ImageIcon("../assets/icons8-new-folder-24px.png");
    private ImageIcon iconeFichier = new ImageIcon("../assets/icons8-new-file-24px.png");
    private ImageIcon iconeCopie = new ImageIcon("../assets/icons8-copy-24.png");
    private ImageIcon iconeCouper = new ImageIcon("../assets/icons8-cut-24.png");
    private ImageIcon iconeColler = new ImageIcon("../assets/icons8-paste-24.png");
    private ImageIcon iconeRennomer = new ImageIcon("../assets/rename 24px.png");
    private ImageIcon iconeSuppression = new ImageIcon("../assets/delete(1).png");
    private ImageIcon iconePropriete = new ImageIcon("../assets/icons8-property-24.png");

    private JMenu option1 = new JMenu("Fichiers");
    private JMenu option2 = new JMenu("Edition");
    
    private JMenuItem[] item1 = {
        new JMenuItem("Ouvrir une nouvelle fenetre", iconeNewfenetre),
        new JMenuItem("Ouvrir Powershell", iconeOpenPowershell),
        new JMenuItem("Fermer", iconeFermer),
    };

    private JMenuItem[] item2 = {
        new JMenuItem("Nouveau dossier", iconeDossier),
        new JMenuItem("Nouveau fichier", iconeFichier),
        new JMenuItem("Copier", iconeCopie),
        new JMenuItem("Couper", iconeCouper),
        new JMenuItem("Coller", iconeColler),
        new JMenuItem("Renommer", iconeRennomer),
        new JMenuItem("Supprimer", iconeSuppression),
        new JMenuItem("Propriete", iconePropriete)
    };

    private JMenuItem[] item2Popup = {
        new JMenuItem("Nouveau dossier", iconeDossier),
        new JMenuItem("Nouveau fichier", iconeFichier),
        new JMenuItem("Copier", iconeCopie),
        new JMenuItem("Couper", iconeCouper),
        new JMenuItem("Coller", iconeColler),
        new JMenuItem("Renommer", iconeRennomer),
        new JMenuItem("Supprimer", iconeSuppression),
        new JMenuItem("Propriete", iconePropriete)
    };
    private JButton[] item2ToolBar = {
        new JButton("Nouveau dossier", iconeDossier),
        new JButton("Nouveau fichier", iconeFichier),
        new JButton("Copier", iconeCopie),
        new JButton("Couper", iconeCouper),
        new JButton("Coller", iconeColler),
        new JButton("Renommer", iconeRennomer),
        new JButton("Supprimer", iconeSuppression),
        new JButton("Propriete", iconePropriete)
    };

    public MenuNavigation(){
        setAllForJMenuBar();
        createToolbar();
        setAllForPopupMenu();
        
        // interactionSurItem1(this.item1, modedTree);
        // interactionSurItem(this.item2, modedTree);
        // interactionSurItem(this.item2Popup, modedTree);
        // interactionSurItem(this.item2ToolBar,modedTree);
    }

    private void createToolbar() {
        // this.boutonRetour = new JButton("Retour", iconeRetour);
        this.toolbar = new JToolBar();
        // this.toolbar.add(boutonRetour);

        for (JButton item : item2ToolBar) {
            // Pour chaque JMenuItem, on crée un JButton correspondant avec son icône
                this.toolbar.add(item);
        }
    }

    private void setAllForJMenuBar(){
        for(JMenuItem item : item1){
            this.option1.add(item);
        }
        for(JMenuItem item : item2){
            this.option2.add(item);
        }
        this.BarreDeMenu.add(option1);
        this.BarreDeMenu.add(option2);
    }

    private void setAllForPopupMenu(){
        for (JMenuItem item : item2Popup) {
            this.popupMenu.add(item);
        }
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
    public JMenuItem[] getItems2(){
        return this.item2;
    }
    public JMenuItem[] getItem2z() {
        return item2Popup;
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

/*----------------------------------------------------------- Methodes de manipulation de fichier --------------------------------------------------------------- */


    @SuppressWarnings("unused")
    private String demanderNomElement() {
        String nouveauNom = JOptionPane.showInputDialog("Nom du nouvel élément :");
        if (nouveauNom != null && nouveauNom.trim().isEmpty()) {
            afficherErreur("Le nom de l'élément ne peut pas être vide.");
            return null;
        }
        return nouveauNom;
    }
    
    @SuppressWarnings("unused")
    private void afficherMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    private void afficherErreur(String erreur) {
        JOptionPane.showMessageDialog(null, erreur, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}


// 
    // public void interactionSurItem(AbstractButton[] item){
    //     // Ajoute un listener pour créer un nouveau dossier
    //     item[0].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String folderPath = modedTree.getDerniereSelection();
    //             if (folderPath != null) {
    //                 creerNouveauDossier(folderPath);
    //             }
    //         }
    //     });

    //     // Ajoute un listener pour créer un nouveau fichier
    //     item[1].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String folderPath = modedTree.getDerniereSelection();
    //             if (folderPath != null) {
    //                 creerNouveauFichier(folderPath);
    //             }
    //         }
    //     });

    //     // Ajouter les écouteurs d'événements aux éléments du menu
    //     // Ajoute un listener pour COPIER un fichier
    //     item[2].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String filePath = modedTree.getDerniereSelection();
    //             if (filePath != null) {
    //                 copierFichier(filePath);
    //             }
    //         }
    //     });

    //     // Ajoute un listener pour COUPER un fichier
    //     item[3].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String filePath = modedTree.getDerniereSelection();
    //             if (filePath != null) {
    //                 couperFichier(filePath);
    //             }
    //         }
    //     });

    //     // Ajoute un listener pour COLLER un fichier
    //     item[4].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String folderPath = modedTree.getDerniereSelection();
    //             if (folderPath != null) {
    //                 collerFichier(folderPath);
    //             }
    //         }
    //     });

    //     // Ajoute un listener pour RENOMMER un fichier
    //     item[5].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String filePath = modedTree.getDerniereSelection();
    //             System.out.println(filePath);
    //             if (filePath != null) {
    //                 renommerFichier(filePath);
    //             }
    //         }
    //     });

    //     // Ajoute un listener pour SUPPRIMER un fichier
    //     item[6].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String filePath = modedTree.getDerniereSelection();
    //             if (filePath != null) {
    //                 supprimerFichier(filePath);
    //             }
    //         }
    //     });

    //     item[7].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String filePath = modedTree.getDerniereSelection();
    //             if (filePath != null) {
    //                 new FenetrePropriete(filePath);
    //             }
    //         }
    //     });
    // }

    // public void interactionSurItem1(AbstractButton[] item){
    //     // Ajoute un listener pour créer un nouveau dossier
    //     item[0].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             new Fenetre();
    //         }
    //     });

    //     // Ajoute un listener pour créer un nouveau fichier
    //     item[1].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             try {
    //                 // Ouvrir Windows PowerShell en utilisant le programme "powershell.exe"
    //                 Desktop.getDesktop().open(new java.io.File("C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe"));
    //             } catch (IOException ex) {
    //                 ex.printStackTrace();
    //             }
    //         }
    //     });

    //     // Ajouter les écouteurs d'événements aux éléments du menu
    //     // Ajoute un listener pour COPIER un fichier
    //     item[2].addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             System.exit(0);
    //         }
    //     });
    // }