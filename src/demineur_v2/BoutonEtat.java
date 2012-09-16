package demineur_v2;

import javax.swing.*;

public class BoutonEtat extends JButton {
	private static final long serialVersionUID = 1L;

	private static final String cheminBase = "images/";

    private static final ImageIcon iconePartieEnCours = 
            new ImageIcon(cheminBase + "partie_en-cours.png");
   
    private static final ImageIcon iconePartieGagnee  = 
            new ImageIcon(cheminBase + "partie_gagnee.png");
   
    private static final ImageIcon iconePartiePerdue  = 
            new ImageIcon(cheminBase + "partie_perdue.png");
    
    public BoutonEtat() {
    	setHorizontalAlignment(CENTER);
    	setIconTextGap(30);
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	setPartieEnCours();
    	
    } // BarreEtat()
    
    public void setPartieEnCours() {
    	setIcon(iconePartieEnCours);
    	setText("Jouez...");
    	
    } // setPartieEnCours()
    
    public void setPartieGagnee() {
    	setIcon(iconePartieGagnee);
    	setText("Gagné !");
    	
    } // setPartieGagnee()
    
    public void setPartiePerdue() {
    	setIcon(iconePartiePerdue);
    	setText("Perdu !");
    	
    } // setPartiePerdue

} // BarreEtat
