package view;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import plan.Plan;

import java.awt.BorderLayout;
import java.awt.Image;

public class Fenetre extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MenuNavigation menu = new MenuNavigation();
	private Plan plan;

	public Fenetre() {
		this.setTitle("Plan designer");
		this.setSize(1020, 660);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("");
        Image iconImage = icon.getImage();
        Image resizedIcon = iconImage.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        this.setIconImage(resizedIcon);

		// Mise en place du panneau explorateur et de l'arbre cote a cote
		JSplitPane composantsCentre = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		// composantsCentre.setComponentPopupMenu();
        composantsCentre.setLeftComponent(new JScrollPane(new JPanel()));
		this.plan = new Plan();
        composantsCentre.setRightComponent(plan);
		composantsCentre.setDividerLocation(200);

		// Mise en place de la barre de menu et du toolbar au nord
		JPanel composantsNord = new JPanel();
        composantsNord.setLayout(new BorderLayout());
        composantsNord.add(menu.getMenu(), BorderLayout.NORTH);
        composantsNord.add(menu.getToolbar(),BorderLayout.CENTER);
		// composantsNord.add();

		this.setLayout(new BorderLayout());
		this.getContentPane().add(composantsCentre, BorderLayout.CENTER);
		this.getContentPane().add(composantsNord, BorderLayout.NORTH);

		this.setVisible(true);
	}
}
