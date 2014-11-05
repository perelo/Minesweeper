package demineur_v2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class ControleurJeu implements MouseListener {

    private BoutonEtat barreEtat;
    private BarreUtil  barreUtil;
    private ChampMine  champMine;

    private int i, j;
    private boolean estDessus;

    ControleurJeu (BoutonEtat barreEtat, BarreUtil barreUtil,
                   ChampMine champMine) {

        this.barreEtat = barreEtat;
        this.barreUtil = barreUtil;
        this.champMine = champMine;

    } // ControleurJeu()

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) return;
        if (! estDessus) return;

        JButton bouton = (JButton) e.getSource();
        i = champMine.getLingeBouton(bouton.getName());
        j = champMine.getColonneBouton(bouton.getName());

        if (champMine.getNbCasesDecouvertes() == 0) {
            champMine.creerGrille(i, j);
            champMine.mettreAJourVoisinage();
        }

        if (champMine.estMarqueeMine(i, j)) return;
        if (champMine.estUneMine(i, j)) {
            partiePerdue(i, j);
        }
        champMine.setDecouverte(i, j);
        barreUtil.updateDrapeaux(champMine.nbDrapeaux, champMine.nbMines);

    } // mouseReleased()

    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON3) return;

        JButton bouton = (JButton) e.getSource();
        i = champMine.getLingeBouton(bouton.getName());
        j = champMine.getColonneBouton(bouton.getName());

        if (champMine.estCaseNormale(i, j))
            champMine.setMarquerMine(i, j);
        else if (champMine.estMarqueeMine(i, j))
            champMine.setMarquerDoute(i, j);
        else if (champMine.estMarqueeDoute(i, j))
            champMine.setMarquerNormale(i, j);

        barreUtil.updateDrapeaux(champMine.nbDrapeaux, champMine.nbMines);

    } // mousePressed()

    public void partiePerdue(int i, int j) {
        champMine.decouvrirGrille();
        champMine.setMarquerExplosee(i, j);
        barreEtat.setPartiePerdue();

    } // partiePerdue()

    public void partieGagnee() {
        champMine.decouvrirGrille();
        barreEtat.setPartieGagnee();

    } // partieGagnee()

    public void mouseEntered(MouseEvent e) { estDessus = true; }
    public void mouseExited (MouseEvent e) { estDessus = false; }

    public void mouseClicked(MouseEvent e) {}

} // ControleurJeu
