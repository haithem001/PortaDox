import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JFrame;
public class Fight1 extends Game implements ActionListener{
		
	Fight1(){
		this.initFight();
		this.initMap();

	}
	public void initFight(){
	
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		inventory = new Inventory();
		hud = new HUD();
		hud.setX(90);
		hud.setY(680);
		board = new Board();
		board.setFightSelector(dude.getIntoFight());

	}
	
	public void initMap() {
		ScreenData = new short[N_BLOCKS_X * N_BLOCKS_Y];

		Data = board.getleveldata(this.dude.getIntoMap());

		int i;
		for (i = 0; i < N_BLOCKS_X * N_BLOCKS_Y; i++) {

			ScreenData[i] = Data[i];
		}
		System.out.println(ScreenData.length);
	}
	
	
	public void actionPerformed(ActionEvent e) {

		repaint(hud.getX(), hud.getY(), hud.getH(), hud.getW());
		dude.move(ScreenData, N_BLOCKS_Y, N_BLOCKS_X, BLOCK_SIZE);
		repaint(dude.getX() - 2, dude.getY() - 2, dude.getWidth() + 3, dude.getHeight() + 3);
		repaint(0, 0, board.getW() + 3, board.getH() + 3);
		board.setSelector(dude.getIntoMap());

		
	}

	
}
