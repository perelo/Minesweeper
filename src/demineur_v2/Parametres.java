package demineur_v2;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.*;

public class Parametres extends JDialog {
	private static final long serialVersionUID = 1L;
	
	public int horiz, vert, mines;
	public boolean clicOK = false;
	
	private JTextField textHoriz;
	private JTextField textVert;
	private JTextField textMines;
	
	public Parametres(final FenetreDemineur pere) {
		super(pere, "Paramètres", Dialog.ModalityType.APPLICATION_MODAL);
		setLocation(pere.getLocation());

		GridLayout layout = new GridLayout(3, 2);
		layout.setHgap(20);
		layout.setVgap(5);
		JPanel panelTaille = new JPanel(layout);
		
		Border bTitled = BorderFactory.createTitledBorder("Taille");
		Border bEmpty  = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		panelTaille.setBorder(BorderFactory.createCompoundBorder
							 (bEmpty, bTitled));
		
		initialiserVar(pere);
		
		panelTaille.add(new JLabel("Horizontal"));
		panelTaille.add(textHoriz);
		panelTaille.add(new JLabel("Vertical"));
		panelTaille.add(textVert);
		panelTaille.add(new JLabel("Nombre de mines"));
		panelTaille.add(textMines);
		
		JPanel panelBoutons = new JPanel(new FlowLayout());
		
		JButton boutonOK   = new JButton("OK");
		JButton boutonStop = new JButton("Annuler");
		
		boutonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					horiz = Integer.parseInt(textHoriz.getText());
					vert  = Integer.parseInt(textVert.getText());
					mines = Integer.parseInt(textMines.getText());
					clicOK = true;
					dispose();
				}
				catch (Exception exc) {
					JOptionPane.showMessageDialog(new JFrame(),
									"Erreur de donnée");
				}

			}
		});
		
		boutonStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		panelBoutons.add(boutonOK);
		panelBoutons.add(boutonStop);
		
		add(panelTaille, BorderLayout.CENTER);
		add(panelBoutons, BorderLayout.SOUTH);
		
		pack();
		
	} // Parametres()
	
	private void initialiserVar(FenetreDemineur demin) {
		textHoriz = new JTextField(demin.tailleX + "");
		textVert  = new JTextField(demin.tailleY + "");
		textMines = new JTextField(demin.nbMines + "");
		
	} // initialiserVar()

} // Parametres
