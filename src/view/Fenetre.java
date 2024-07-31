package view;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import controleurs.Controleur;
import plan.Plan;

import java.awt.BorderLayout;
import java.awt.Image;

public class Fenetre extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MenuBar barreMenu = new MenuBar();
	private ToolBar barreOutils = new ToolBar(1);
	private ToolBar barreOutils2 = new ToolBar(2);
	private Plan plan = new Plan();

	public Fenetre() {
		new Controleur(plan, barreOutils, barreOutils2);
		this.setTitle("Plan designer");
		this.setSize(1020, 660);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("assets\\icons8-app-48.png");
        Image iconImage = icon.getImage();
        Image resizedIcon = iconImage.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        this.setIconImage(resizedIcon);

		// Mise en place du panneau explorateur et de l'arbre cote a cote
		JSplitPane composantsCentre = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		// composantsCentre.setComponentPopupMenu();
        composantsCentre.setLeftComponent(new JScrollPane(this.plan));
        composantsCentre.setRightComponent(plan);
		composantsCentre.setDividerLocation(200);

		// Mise en place de la barre de menu et du toolbar au nord
		JPanel composantsNord = new JPanel();
        composantsNord.setLayout(new BorderLayout());
        composantsNord.add(barreMenu.getMenuBar(), BorderLayout.NORTH);
        composantsNord.add(barreOutils.getToolBar(),BorderLayout.CENTER);
		composantsNord.add(barreOutils2.getToolBar(), BorderLayout.EAST);
		// composantsNord.add();

		this.setLayout(new BorderLayout());
		this.getContentPane().add(composantsCentre, BorderLayout.CENTER);
		this.getContentPane().add(composantsNord, BorderLayout.NORTH);

		this.setVisible(true);
	}
}
