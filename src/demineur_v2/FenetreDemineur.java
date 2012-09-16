package demineur_v2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FenetreDemineur extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ChampMine  champMine;
	private BoutonEtat barreEtat;
	private BarreUtil  barreUtil;
	
	public int tailleX, tailleY, nbMines;
	
	public FenetreDemineur(final int tailleXUser, final int tailleYUser,
			               int nbMinesUser) {
		setTitle("Démineur");
		setLayout(new BorderLayout());
		
		this.tailleX = tailleXUser;
		this.tailleY = tailleYUser;
		this.nbMines = verifMines(tailleX, tailleY, nbMinesUser);
		
		barreEtat = new BoutonEtat();
		barreUtil = new BarreUtil(nbMines, this);
		champMine = new ChampMine (tailleX, tailleY, nbMines,
				                   barreEtat, barreUtil);
		
		barreUtil.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		
		barreEtat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recommancerJeu(tailleX, tailleY, nbMines);
			}
		});
		
		add(barreEtat, BorderLayout.NORTH);
		add(champMine, BorderLayout.CENTER);
		add(barreUtil, BorderLayout.SOUTH);
		
		pack();
		
	} // FenetreDemineur()
	
	private int verifMines(final int tailleX, final int tailleY,
						   final int nbMines) {
		int maxMines = tailleX * tailleY - 10;
		return nbMines < maxMines ? nbMines : maxMines;
		
	} // verifMines()
	
	public void recommancerJeu(int tailleX, int tailleY, int nbMines) {
		nbMines = verifMines(tailleX, tailleY, nbMines);
		remove(champMine);
		champMine = new ChampMine(tailleX, tailleY, nbMines,
				                  barreEtat, barreUtil);
		add(champMine);
		barreEtat.setPartieEnCours();
		barreUtil.updateDrapeaux(0, nbMines);
		validate();
		pack();
		
	} // recommancerJeu()

	public static void main(String[] args) {
		FenetreDemineur fenetre = new FenetreDemineur(30, 16, 99);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setResizable(false);
		
		fenetre.setVisible(true);
		
	} // main()

} // FenetreDemineur
