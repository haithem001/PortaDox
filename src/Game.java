import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import NPCS.*;
import NPCS.Buildo;
import NPCS.Dude;
import NPCS.Portalo;
import NPCS.Sello;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {
	private volatile int screenX;
	private volatile int screenY;
	private InteractionPanel faces ;
	private Timer timer;
	private Animations anim;
	public Board board;
	public Dude dude;
	private Princess princess;
	private Buildo buildo;
	private Portalo portalo;
	private Sello sello;
	public HUD hud;
	public Inventory inventory;
	private final int DELAY = 60;
	private Graphics2D G;
	private final int DIALOGUE_ANIM_DELAY = 3500;
	private final int DIALOGUE_ANIM_COUNT = 2;
	private int dialogueAnimCount = DIALOGUE_ANIM_DELAY;
	private int dialogueAnimDir = 1;
	private int dialogueAnimPos = 0;

	private final int DUDE_ANIM_DELAY = DELAY*7;
	private final int DUDE_ANIM_COUNT = 2;
	private int dudeAnimCount = DUDE_ANIM_DELAY;
	private int dudeAnimDir = 1;
	private int dudeAnimPos = 0;

	public final int MAP_ANIM_DELAY = 360;
	public  final int MAP_ANIM_COUNT = 2;
	public  int mapAnimCount = MAP_ANIM_DELAY;
	public int mapAnimDir = 1;
	public int mapAnimPos = 0;
	public final int N_BLOCKS_X = 66;
	public final int N_BLOCKS_Y = 51;

	public final int BLOCK_SIZE = 12;

	public short[] ScreenData;
	public short[] Data;

	public Rectangle solidarea;
	public  Rectangle shadow;
	private int X,Y;
	public Game() {
		
		initGame();
		initMap();

	}

	public void initMap() {
		ScreenData = new short[N_BLOCKS_X * N_BLOCKS_Y];

		Data = board.getleveldata(dude.getIntoMap());

		int i;
		/*for (i = 0; i < N_BLOCKS_X * N_BLOCKS_Y; i++) {

			ScreenData[i] = Data[i];
		}*/
		ScreenData=Data;

	}

	public void initGame() {
	
		
		
		this.addKeyListener(new TAdapter());
		this.setBackground(Color.black);
		this.setFocusable(true);

		this.solidarea = new Rectangle();



		inventory = new Inventory();
		hud = new HUD();
		hud.setX(90);
		hud.setY(680);
		
		sello = new Sello();
		portalo = new Portalo();
		buildo = new Buildo();
		dude = new Dude();
		princess = new Princess();
		board = new Board();
		
		board.setSelector(dude.getIntoMap());

		dude.setX(252);
		dude.setY(252);
		faces=new InteractionPanel();

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

	private void dodialogAnim() {
		if (dude.getInteract()) {
			dialogueAnimCount--;

			if (dialogueAnimCount <= 0) {

				dialogueAnimCount = DIALOGUE_ANIM_DELAY;
				dialogueAnimPos = dialogueAnimPos + dialogueAnimDir;

				if (dialogueAnimPos == (DIALOGUE_ANIM_COUNT)) {


					dialogueAnimPos = 5;
					dude.setInteract(false);
					dude.setTalkmessage(false);


				}
				else if (dude.Talkmessage()==true){
					dude.setInteract(true);

					dialogueAnimPos=0;
				}
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


		DrawBoard(g);
		if (board.getSelector() == 9) {
			DrawNPC(g);
			princess.setX(310);
			princess.setY(340);
			DrawInteraction(g);


			
		}
		if (board.getSelector() == 1) {


			DrawNPC(g);
			portalo.setX(400);
			portalo.setY(200);
			DrawInteraction(g);


		}
		if (board.getSelector() == 5) {
			DrawNPC(g);
			sello.setX(60);
			sello.setY(300);
			DrawInteraction(g);
		}
		if (board.getSelector() == 4) {
			DrawNPC(g);
			buildo.setX(680);
			buildo.setY(200);
			DrawInteraction(g);
		}

		DrawPlayer(g);
		DrawHUD(g);
		if (dude.isClimbing() == true) {

			doAnim(4);

		} else if (dude.isWalking() == true) {

			doAnim(2);
		}

		if (board.getSelector() == 2) {
			DrawArea(g);
		}
		DrawInteractions(g);
		DrawFunctional(g);
		Toolkit.getDefaultToolkit().sync();
		
	}

	private void DrawArea(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		if (solidarea != null) {

			Color c = new Color(69, 66, 68);
			g2d.setColor(c);
			g2d.fillRect(612, 200, (int) dude.getWidth() / 2 - 4, (int) dude.getHeight() * 2 - 5);

		}
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
				if (hud.GetInventoryOpen() == true) {
					if (inventory.items.getItemX(0, 0) < x + 20 && inventory.items.getItemX(0, 0) + 50 > x) {
						if (inventory.items.getItemY(0, 0) < y + 20 && inventory.items.getItemY(0, 0) + 40 > y) {
							
							screenX = e.getXOnScreen();
							screenY = e.getYOnScreen();
							inventory.items.setItemXY(0, 0, x, y);
						}
					}
					if (inventory.items.getItemX(0, 1) < x + 100 && inventory.items.getItemX(0, 1) + 130 > x) {
						if (inventory.items.getItemY(0, 1) < y + 20 && inventory.items.getItemY(0, 1) + 40 > y) {
							
							screenX = e.getXOnScreen();
							screenY = e.getYOnScreen();
							inventory.items.setItemXY(0, 1, x, y);
						}
					}
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
				int deltaX = e.getXOnScreen() - screenX + inventory.items.getX();
				int deltaY = e.getYOnScreen() - screenY + inventory.items.getY();
				if (hud.GetInventoryOpen() == true) {
					if (inventory.items.getItemX(0, 0) < x + 20 && inventory.items.getItemX(0, 0) + 50 > x) {
						if (inventory.items.getItemY(0, 0) < y + 20 && inventory.items.getItemY(0, 0) + 40 > y) {

							inventory.items.setItemXY(0, 0, deltaX, deltaY);

							System.out.println("{" + deltaX + ',' + deltaY + "}");

						}
					}
					 if (inventory.items.getItemX(0, 1) < x + 100 && inventory.items.getItemX(0, 1) + 100 > x) {
						if (inventory.items.getItemY(0, 1) < y + 20 && inventory.items.getItemY(0, 1) + 40 > y) {

							inventory.items.setItemXY(0, 1, deltaX, deltaY);

							System.out.println("{" + deltaX + ',' + deltaY + "}");

						}
					}
					
				}

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});
	}

	private void DrawInventoryItems(Graphics2D g2d) {

		
		g2d.drawImage(inventory.items.getItemImage(0, 0), inventory.items.getItemX(0, 0),
				inventory.items.getItemY(0, 0), this);
		g2d.drawImage(inventory.items.getItemImage(0, 1), inventory.items.getItemX(0, 1),
				inventory.items.getItemY(0, 1), this);

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
		if (hud.GetInventoryOpen() == false) {
			g2d.drawImage(hud.getBagImage(0), hud.getX() + hud.getW() - 75, hud.getY() + 11, this);
		} else if (hud.GetInventoryOpen() == true) {
			g2d.drawImage(hud.getBagImage(1), hud.getX() + hud.getW() - 75, hud.getY() + 11, this);
			g2d.drawImage(inventory.getInventoryImage(), board.getX() + 80, board.getY() + 60, this);
			// System.out.println("items:" + inventory.items.showItemsExisted());
			DrawInventoryItems(g2d);
		}

	}
	private void DrawInteractions(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(dude.getInteract()==true){


			switch (mapAnimPos) {
				case 0:
					g2d.drawImage(faces.getImage(1),faces.getX(), faces.getY(), this);
					break;
				case 1:
					g2d.drawImage(faces.getImage(0),faces.getX(), faces.getY(), this);
					break;

				default:
					g2d.drawImage(faces.getImage(0),faces.getX(), faces.getY(), this);

			}


		}
		else{
			g2d.drawImage(faces.getImage(0),faces.getX(), faces.getY(), this);

		}
		this.repaint();


	}
	private void DrawInteraction(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		// HUD placement
		g2d.setFont(new Font("PixellettersFull", Font.BOLD, 20));
		g2d.setColor(new Color(132, 117, 119));

		int x = portalo.getX() + 60;
		int y = portalo.getY() - 40;

		if (dude.getInteract() == true ) {
			

			dodialogAnim();
			switch (dialogueAnimPos) {

			case 0:




				for (String line : portalo.loadDialogue(0).split("\n")) {

					g.drawString(line, x, y);
					y = y + 20;
				}

				break;
			case 1:

				for (String line : portalo.loadDialogue(1).split("\n")) {
					g.drawString(line, x, y);
					y = y + 20;
				}

			}
			if(dialogueAnimPos==dialogueAnimCount){
				dude.setInteract(false);
				for (String line : portalo.loadDialogue(5).split("\n")) {
					g.drawString(line, x, y);
					y = y + 20;
				}


			}

			
			

			
			}



	}
	private void DrawFunctional(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		// HUD placement
		g2d.setFont(new Font("PixellettersFull", Font.BOLD, 20));
		g2d.setColor(new Color(255, 222, 0));

		int x = 400;
		int y = 500;

		if (dude.Talkmessage() == true) {
				g2d.drawString("( Press F )", x, y);
		}
		else{

		}

	}


	private void DrawBoard(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		switch (mapAnimPos) {
		case 0:
			g2d.drawImage(board.getImage(0), board.getX(), board.getY(), this);
			break;
		case 1:
			g2d.drawImage(board.getImage(1), board.getX(), board.getY(), this);
			break;

		default:
			g2d.drawImage(board.getImage(0), board.getX(), board.getY(), this);

		}

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

	private void DrawNPC(Graphics g) {

		Graphics2D NPCG = (Graphics2D) g;
		// playerArea.setColor(Color.blue);
		// playerArea.fillRect(X, Y, dude.solidarea.width, dude.solidarea.height);
		if (board.getSelector() == 1) {
			switch (mapAnimPos) {
			case 0:
				NPCG.drawImage(portalo.getImage(0, dude.getX()), portalo.getX(), portalo.getY(), this);
				NPCG.drawImage(faces.getImage(0),faces.getX(), faces.getY(), this);

				break;
			case 1:
				NPCG.drawImage(portalo.getImage(1, dude.getX()), portalo.getX(), portalo.getY(), this);
				NPCG.drawImage(faces.getImage(1),faces.getX(), faces.getY(), this);

				break;

			default:
				NPCG.drawImage(portalo.getImage(0, dude.getX()), portalo.getX(), portalo.getY(), this);
			}
		}
		if (board.getSelector() == 5) {
			NPCG.drawImage(sello.getImage(), sello.getX(), sello.getY(), this);
			NPCG.drawImage(sello.getMarketImage(), sello.getX() + 75, sello.getY() - 17, this);
		}
		if (board.getSelector() == 4) {
			NPCG.drawImage(buildo.getImage(), buildo.getX(), buildo.getY(), this);
		}
		if (board.getSelector() == 9) {
			NPCG.drawImage(princess.getImage(), princess.getX(), princess.getY(), this);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint(hud.getX(), hud.getY(), hud.getH(), hud.getW());
		dude.move(ScreenData, N_BLOCKS_Y, N_BLOCKS_X, BLOCK_SIZE);
		
		repaint(0, 0, board.getW() + 3, board.getH() + 3);
		board.setSelector(dude.getIntoMap());

		if (board.getSelector() == 1) {

			initMap();

			//repaint(portalo.getX() - 2, portalo.getY() - 2, portalo.getWidth() + 3, portalo.getHeight() + 3);

		}
		if (board.getSelector() == 2) {

			initMap();
			//repaint(solidarea.x, solidarea.y, solidarea.width, solidarea.height);

		}
		if (board.getSelector() == 3) {

			initMap();

		}
		if (board.getSelector() == 4) {

			initMap();
			repaint(buildo.getX() - 2, buildo.getY() - 2, buildo.getWidth() + 3, buildo.getHeight() + 3);

		}

		if (board.getSelector() == 5) {

			initMap();
			repaint(sello.getX() - 2, sello.getY() - 2, sello.getWidth() + 3, sello.getHeight() + 3);

		}

		if (board.getSelector() == 6) {

			initMap();

		}
		if (board.getSelector() == 7 || board.getSelector() == 8) {

			initMap();

		}
		if (board.getSelector() == 9 ) {

			initMap();
			repaint(princess.getX() - 2, princess.getY() - 2, princess.getWidth() + 3, princess.getHeight() + 3);

		}

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
