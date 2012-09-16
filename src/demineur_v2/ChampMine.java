package demineur_v2;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class ChampMine extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int[][] grille;
	private int tailleX;
	private int tailleY;
	public int nbMines;
	public int nbDrapeaux;
	
	private final int VIDE = -1;
	private final int MINE = -2;
	
	private int nbCasesDecouvertes;
	
	private static final String cheminBase = "images/";
	
	private ImageIcon iconeCaseNormale;
	private ImageIcon iconeMarqueDoute;
	private ImageIcon iconeMarqueMine;
	private ImageIcon iconeCaseMinee;
	private ImageIcon iconeCaseExplosee;
	//private ImageIcon iconeFauxDrapeau;
	
	private ImageIcon[] iconesChiffres;
	private JButton[][] boutons;
	
	private ControleurJeu controleur;
	
	
	public ChampMine(int tailleX, int tailleY, int nbMines,
			         BoutonEtat barreEtat, BarreUtil barreUtil) {
		this.tailleX = tailleX;
		this.tailleY = tailleY;
		this.nbMines = nbMines;
		nbCasesDecouvertes = 0;
		setLayout(new GridLayout(tailleY, tailleX));
		
		controleur = new ControleurJeu(barreEtat, barreUtil, this);
		chargerIcones();
		creerBoutons();
		
	} // ChampMine() public
	
	private void chargerIcones() {
		iconeCaseNormale  = new ImageIcon(cheminBase +
								"case_normale.png");
		iconeMarqueDoute  = new ImageIcon(cheminBase +
								"case_marquee_douteuse.png");
		iconeMarqueMine   = new ImageIcon(cheminBase +
				     			"case_marquee_minee.png");
		iconeCaseMinee    = new ImageIcon(cheminBase +
								"case_mine.png");
		iconeCaseExplosee = new ImageIcon(cheminBase +
								"case_mine_explosee.png");
		//iconeFauxDrapeau  = new ImageIcon(cheminBase +
			//	                "case_faux_drapeau.png");
		
		iconesChiffres = new ImageIcon[9];
		for (int i = 0; i < iconesChiffres.length; ++i)
			iconesChiffres[i] = new ImageIcon
								(cheminBase + "case_" + i + ".png");
		
	} // chargerIcones()
	
	private void creerBoutons() {
		boutons = new JButton[tailleY][tailleX];

		for (int i = 0; i < tailleY; ++i) {
			for (int j = 0; j < tailleX; ++j) {
				boutons[i][j] = new JButton();
				boutons[i][j].setIcon(iconeCaseNormale);
				boutons[i][j].setRolloverEnabled(false);
				boutons[i][j].setPreferredSize(new Dimension
						                (iconeCaseNormale.getIconWidth(),
						                 iconeCaseNormale.getIconWidth()));
				boutons[i][j].setName(i + " " + j);
				boutons[i][j].addMouseListener(controleur);
				add(boutons[i][j]);
			}
		}
		
	} // creerBoutons()
	
	public int getLingeBouton(String commande) {
		return Integer.parseInt(commande.substring
	            				(0, commande.indexOf(' ')));
		
	} // getLingeBouton()
	
	public int getColonneBouton(String commande) {
		return Integer.parseInt(commande.substring
	         					(commande.indexOf(' ')+1, commande.length()));
		
	} // getColonneBouton()
	
	public void setMarquerDoute(int i, int j) {
		boutons[i][j].setIcon(iconeMarqueDoute);
		if (nbDrapeaux != 0) --nbDrapeaux;
		
	} // setMarqueeDoute()
	
	public void setMarquerMine(int i, int j) {
		boutons[i][j].setIcon(iconeMarqueMine);
		++nbDrapeaux;
		
	} // setMarquerMine()
	
	public void setMarquerNormale(int i, int j) {
		boutons[i][j].setIcon(iconeCaseNormale);
		
	} // setMarquerMine()
	
	public void setMarquerExplosee(int i, int j) {
		boutons[i][j].setIcon(iconeCaseExplosee);
		
	} // setMarquerExplisee()
	
	public boolean estMarqueeDoute(int i, int j) {
		return boutons[i][j].getIcon() == iconeMarqueDoute;
		
	} // estMarqueeMine()
	
	public boolean estMarqueeMine(int i, int j) {
		return boutons[i][j].getIcon() == iconeMarqueMine;
		
	} // estMarqueeMine()
	
	public boolean estCaseNormale(int i, int j) {
		return boutons[i][j].getIcon() == iconeCaseNormale;
		
	} // estMarqueeMine()
	
	public void setDecouverte(final int i, final int j) {

		if(boutons[i][j].getIcon() == iconeMarqueMine ||
		   boutons[i][j].getName() == "clic") return;
		
		if(estUneMine(i, j)) {
			controleur.partiePerdue(i, j);
			return;
		}
		
		++nbCasesDecouvertes;
		if (aGagne()) controleur.partieGagnee();
		if (nbMinesVoisines(i, j) == 0) decouvrirAlentour(i, j);
		boutons[i][j].setName("clic");
		boutons[i][j].removeMouseListener(controleur);
		boutons[i][j].addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					decouvrirAlentourMine(i, j);
			}
		});
		boutons[i][j].setIcon(iconesChiffres[nbMinesVoisines(i, j)]);
		
	} // setDecouverte()
	
	private void decouvrirAlentour(int i, int j) {
		boutons[i][j].setName("clic");
		if (coordOK(i+1, j  )) setDecouverte(i+1, j  );
		if (coordOK(i-1, j  )) setDecouverte(i-1, j  );
		if (coordOK(i  , j+1)) setDecouverte(i  , j+1);
		if (coordOK(i  , j-1)) setDecouverte(i  , j-1);
		if (coordOK(i+1, j+1)) setDecouverte(i+1, j+1);
		if (coordOK(i-1, j+1)) setDecouverte(i-1, j+1);
		if (coordOK(i+1, j-1)) setDecouverte(i+1, j-1);
		if (coordOK(i-1, j-1)) setDecouverte(i-1, j-1);
		
	} // decouvrirCase()
	
	private void decouvrirAlentourMine(int i, int j){
		final int nbMinesAutour = nbMinesVoisines(i, j);
		int nbDrapeaux = 0;
		
		if (coordOK(i+1, j  ) && estMarqueeMine(i+1, j  )) ++nbDrapeaux;
		if (coordOK(i-1, j  ) && estMarqueeMine(i-1, j  )) ++nbDrapeaux;
		if (coordOK(i  , j+1) && estMarqueeMine(i  , j+1)) ++nbDrapeaux;
		if (coordOK(i  , j-1) && estMarqueeMine(i  , j-1)) ++nbDrapeaux;
		if (coordOK(i+1, j+1) && estMarqueeMine(i+1, j+1)) ++nbDrapeaux;
		if (coordOK(i-1, j+1) && estMarqueeMine(i-1, j+1)) ++nbDrapeaux;
		if (coordOK(i+1, j-1) && estMarqueeMine(i+1, j-1)) ++nbDrapeaux;
		if (coordOK(i-1, j-1) && estMarqueeMine(i-1, j-1)) ++nbDrapeaux;
		
		if (nbDrapeaux == nbMinesAutour)
			decouvrirAlentour(i, j);
		
	} // decouvrirAlentourMine()
	
	public void decouvrirGrille() {
		for (int i = 0; i < tailleY; ++i) {
			for (int j = 0; j < tailleX; ++j) {
				if (boutons[i][j].getName() != "clic") {
					boutons[i][j].setName("clic");
					boutons[i][j].removeMouseListener(controleur);
						if (grille[i][j] == MINE) {
							if (boutons[i][j].getIcon() != iconeMarqueMine)
								boutons[i][j].setIcon(iconeCaseMinee);
						}
						else
							//if (boutons[i][j].getIcon() == iconeMarqueMine)
								//boutons[i][j].setIcon(iconeFauxDrapeau);
							//else
								boutons[i][j].setIcon(iconeCaseNormale);
				}
			}
		}
		
	} // decouvrirGrille()
	
	public void creerGrille(int x, int y) {
		grille = new int[tailleY][tailleX];
		
		for (int i = 0; i < tailleY; ++i)
			for (int j = 0; j < tailleX; ++j)
				grille[i][j] = VIDE;
		
		placerMines(x, y);
		
	} // creerGrille()
	
	private void placerMines(int y, int x) {
		int posX, posY;
		Random generateur = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < nbMines; ++i) {
			posX = generateur.nextInt(tailleX);
			posY = generateur.nextInt(tailleY);
			
			if (posX == x+1 && posY == y+1) { --i; continue; }
			if (posX == x+1 && posY == y  ) { --i; continue; }
			if (posX == x+1 && posY == y-1) { --i; continue; }
			if (posX == x   && posY == y+1) { --i; continue; }
			if (posX == x   && posY == y-1) { --i; continue; }
			if (posX == x-1 && posY == y+1) { --i; continue; }
			if (posX == x-1 && posY == y  ) { --i; continue; }
			if (posX == x-1 && posY == y-1) { --i; continue; }
			if (posX == x   && posY == y  ) { --i; continue; }
			
			if (grille[posY][posX] == MINE) --i;
			else
				grille[posY][posX] = MINE;
		}
		
	} // placerMines()
	
	public int nbMinesVoisines(int i, int j) {
		if (grille[i][j] == MINE) return MINE;
		int nb = 0;
		
		if (coordOK(i-1, j-1) && grille[i-1][j-1] == MINE) ++nb; 
		if (coordOK(i  , j-1) && grille[i  ][j-1] == MINE) ++nb;
		if (coordOK(i+1, j-1) && grille[i+1][j-1] == MINE) ++nb;
		if (coordOK(i+1, j  ) && grille[i+1][j  ] == MINE) ++nb;
		if (coordOK(i+1, j+1) && grille[i+1][j+1] == MINE) ++nb;
		if (coordOK(i  , j+1) && grille[i  ][j+1] == MINE) ++nb;
		if (coordOK(i-1, j+1) && grille[i-1][j+1] == MINE) ++nb;
		if (coordOK(i-1, j  ) && grille[i-1][j  ] == MINE) ++nb;
		
		return nb;
		
	} // nbMinesVoisines()
	
	private boolean coordOK (int i, int j) {
		return i >= 0 && i < tailleY && j >= 0 && j < tailleX;
		
	} // coordOK()
	
	public void mettreAJourVoisinage() {
		for (int i = 0; i < tailleY; ++i)
			for (int j = 0; j < tailleX; ++j)
				grille[i][j] = nbMinesVoisines(i, j);
		
	} // mettreAJourVoisinage()
	
	public boolean aGagne() {
		return nbCasesDecouvertes == tailleX*tailleY - nbMines;
		
	} // aGagne()
	
	public int getNbCasesDecouvertes() {
		return nbCasesDecouvertes;
		
	} // getNbCasesDecouvertes()
	
	public boolean estUneMine(int i, int j) {
		return grille[i][j] == MINE;
		
	} // estUneMine()
	
} // ChampMine
