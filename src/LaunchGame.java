
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

public class LaunchGame extends JFrame implements ActionListener {

LaunchGame(){

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();
	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	this.setUndecorated(true);


	this. setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	Game game = new Game();
	JPanel panel=new JPanel();
	JLayeredPane lpane = new JLayeredPane();

	this.setVisible(true);
	game.setPreferredSize(new Dimension(1000, this.getHeight()));
	this.setBackground(Color.BLACK);
	this.setPreferredSize(new Dimension((int)width, (int)height));
	this.setLayout(new BorderLayout());
	this.add(lpane, BorderLayout.CENTER);
	lpane.setBounds(0, 0, this.getWidth(), this.getHeight());
	panel.setBackground(Color.black);
	panel.setBounds(0, 0, 4000, 1500);
	panel.setOpaque(true);
	game.setBackground(Color.BLACK);
	game.setBounds(0, 0, 1500, 1000);
	game.setOpaque(true);

	lpane.add(panel, JLayeredPane.DEFAULT_LAYER, 0);
	lpane.add(game,  JLayeredPane.PALETTE_LAYER, 0);
	this.pack();
	this.setVisible(true);



}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
