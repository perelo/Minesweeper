package demineur_v2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BarreUtil extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton param;
	private JLabel  labelDraps;
	
	private String prefix = "Drapeaux : ";
	private int nbMines;
	
	public BarreUtil(final int nbMinesUser, final FenetreDemineur fenetreDem) {
		setLayout(new BorderLayout());
		
		this.nbMines = nbMinesUser;
		
		param      = new JButton("Paramètres");
		labelDraps = new JLabel();
		updateDrapeaux(0, nbMines);
		
		param.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Parametres cadre = new Parametres(fenetreDem);
					cadre.setVisible(true);
					
					if (! cadre.clicOK) return;
					fenetreDem.tailleX = cadre.horiz;
					fenetreDem.tailleY = cadre.vert;
					fenetreDem.nbMines = cadre.mines;
					nbMines = cadre.mines;
					
					fenetreDem.recommancerJeu(fenetreDem.tailleX,
											  fenetreDem.tailleY,
											  fenetreDem.nbMines);
			}
		});
		
		add(param, BorderLayout.WEST);
		add(labelDraps, BorderLayout.EAST);
		
	} // BarreUtil()
	
	public void updateDrapeaux(final int nbDraps, final int nbMines) {
		labelDraps.setText(prefix + nbDraps + "/" + nbMines);
		
	} // mettreAJour()

} // barreUtil
