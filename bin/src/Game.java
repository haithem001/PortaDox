import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import NPCS.Dude;
import org.w3c.dom.css.Rect;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {
	private volatile int screenX;
	private volatile int screenY;
	private Timer timer;
	private Animations anim;
	private int DUDE_ANIM_DELAY;
	private int mapAnimPos;
	private int mapAnimCount;
	private int MAP_ANIM_DELAY;
	private int MAP_ANIM_COUNT;
	private int mapAnimDir;
	private int map;
	private int dudeAnimPos;
	private int dudeAnimCount;
	private int dudeAnimDir;
	private int DELAY=30;
	public Dude dude;
	Rectangle solidarea;
	public HUD hud;

	public Game() {
		
		initGame();
		initMap();

	}

	private void initMap() {
	}


	public void initGame() {
	
		
		
		this.addKeyListener(new TAdapter());
		this.setBackground(Color.black);
		this.setFocusable(true);





		hud = new HUD();
		hud.setX(90);
		hud.setY(680);
		

		dude = new Dude();

		


		dude.setX(252);
		dude.setY(252);

		timer = new Timer(DELAY, this);
		timer.start();

		MouseEvents();
	}

	private void doAnim(int AnimCount) {

		dudeAnimCount--;

		if (dudeAnimCount <= 0) {

			dudeAnimCount = DUDE_ANIM_DELAY;
			dudeAnimPos = dudeAnimPos + dudeAnimDir;

			if (dudeAnimPos == (AnimCount - 1) || dudeAnimPos == 0) {
				dudeAnimDir = -dudeAnimDir;
			}
		}

	}



	private void MapAnim() {

		mapAnimCount--;

		if (mapAnimCount <= 0) {

			mapAnimCount = MAP_ANIM_DELAY;
			mapAnimPos = mapAnimPos + mapAnimDir;

			if (mapAnimPos == (MAP_ANIM_COUNT - 1) || mapAnimPos == 0) {
				mapAnimDir = -mapAnimDir;
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		

		super.paintComponent(g);
		// board

		MapAnim();

		DrawArea(g);


		Toolkit.getDefaultToolkit().sync();
		
	}

	private void MouseEvents() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {

				int x = e.getX();
				int y = e.getY();
				if (hud.getX() + hud.getW() - 15 >= x && x >= hud.getX() + hud.getW() - 75 && y >= hud.getY() + 11
						&& y <= hud.getY() + 81 && hud.GetInventoryOpen() == false) {
					hud.SetInventoryOpen(true);
				} else if (hud.getX() + hud.getW() - 15 >= x && x >= hud.getX() + hud.getW() - 75
						&& y >= hud.getY() + 11 && y <= hud.getY() + 81 && hud.GetInventoryOpen() == true) {
					hud.SetInventoryOpen(false);
				}

				

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				final Point pos = e.getPoint();
				int x = pos.x;
				int y = pos.y;

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				if (hud.getX() + hud.getW() - 15 >= x && x >= hud.getX() + hud.getW() - 75 && y >= hud.getY() + 11
						&& y <= hud.getY() + 81) {
					hud.SetInventoryOpen(false);
				}

			}

		});
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int deltaX = e.getXOnScreen() - screenX ;
				int deltaY = e.getYOnScreen() - screenY ;

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});
	}
	private void DrawArea(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		if (solidarea != null) {

			Color c = new Color(69, 66, 68);
			g2d.setColor(c);
			g2d.fillRect(612, 200, (int) dude.x, (int) dude.y);

		}
	}


	private void DrawHUD(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		// HUD placement
		g2d.setFont(new Font("PixellettersFull", Font.BOLD, 30));
		g2d.setColor(new Color(132, 117, 119));
		g2d.drawImage(hud.getImage(), hud.getX(), hud.getY(), this);
		for (int i = 1; i <= dude.gethealth(); i++) {
			g2d.drawImage(hud.getHeartImage(), hud.getX() + i * 25, hud.getY() + 20, this);

		}
		g2d.drawImage(hud.getItemsImage(), hud.getX() + 140, hud.getY() + 16, this);

		// COINS ANIM
		switch (mapAnimPos) {
		case 0:
			g2d.drawImage(hud.getCoinImage(0), hud.getX() + 25, hud.getY() + 50, this);
			break;
		case 1:
			g2d.drawImage(hud.getCoinImage(1), hud.getX() + 25, hud.getY() + 50, this);
			break;
		default:
			g2d.drawImage(hud.getCoinImage(0), hud.getX() + 25, hud.getY() + 50, this);
		}

		g2d.drawString(hud.getCoins(), hud.getX() + 55, hud.getY() + 67);
		g2d.drawString(":", hud.getX() + 43, hud.getY() + 65);

		// Bag and Inventory


	}







	private void DrawPlayer(Graphics g) {

		Graphics2D playerG = (Graphics2D) g;

		// playerArea.setColor(Color.blue);
		// playerArea.fillRect(X, Y, dude.solidarea.width, dude.solidarea.height);
		if (dude.isWalking()) {
			switch (dudeAnimPos) {
			case 0:
				playerG.drawImage(dude.getImage(0), dude.getX(), dude.getY(), this);
				break;
			case 1:
				playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);
				break;

			default:
				playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);
			}
		} else if (dude.isClimbing()) {

			switch (dudeAnimPos) {
			case 0:
				playerG.drawImage(dude.getImage(0), dude.getX(), dude.getY(), this);
				break;
			case 1:
				playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);
				break;
			case 2:
				playerG.drawImage(dude.getImage(2), dude.getX(), dude.getY(), this);
				break;
			case 3:
				playerG.drawImage(dude.getImage(3), dude.getX(), dude.getY(), this);
				break;

			default:
				playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);
			}
		}

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		dude.move();

		


	}

	public class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			dude.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			dude.keyPressed(e);
		}

	}

}
