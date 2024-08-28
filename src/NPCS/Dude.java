package NPCS;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public class Dude {

	private int dx;
	private int dy;
	public int x;
	public int y;
	private boolean pushTalk =false;
	private boolean stopwalk;
	private int lastdir;
	private boolean resetanim;
	private boolean stopclimb = true;
	private int w;
	private int h;
	private int health = 3;
	private boolean interact = false;
	private boolean climbing = false;
	private boolean interactPressed = false;
	private boolean walking = true;
	private int intomap = 1;
	private int intofight = 1;
	private boolean GameOrFight=true;
	private Image[] WRIGHT = new Image[2];
	private Image[] WLEFT = new Image[2];
	private int Velocity = 10;
	public boolean JUMP = false;
	public boolean on_ground = true;
	private int jump_height = 15;


	private boolean Quit=false;
	private Image[] Climb = new Image[4];
	private Image[] Swalk = new Image[2];
	private Image[] SwalkL = new Image[2];
	private boolean dark = false;
	public Rectangle solidarea;
	private boolean allowWalk = false;

	public Dude() {

		loadPlayerIms();

	}

	private void loadPlayerIms() {

		ImageIcon ii1 = new ImageIcon("src/player1.png");
		ImageIcon ii2 = new ImageIcon("src/Player2.png");
		ImageIcon ii3 = new ImageIcon("src/player1L.png");
		ImageIcon ii4 = new ImageIcon("src/Player2L.png");
		ImageIcon climb1 = new ImageIcon("src/playerClimb1.png");
		ImageIcon climb2 = new ImageIcon("src/playerClimb2.png");
		ImageIcon climb3 = new ImageIcon("src/playerClimb3.png");
		ImageIcon climb4 = new ImageIcon("src/playerClimb4.png");
		ImageIcon Swalk1 = new ImageIcon("src/PlayerShadowwalk1.png");
		ImageIcon Swalk2 = new ImageIcon("src/PlayerShadowwalk2.png");
		ImageIcon Swalk3 = new ImageIcon("src/PlayerShadowwalk1L.png");
		ImageIcon Swalk4 = new ImageIcon("src/PlayerShadowwalk2L.png");

		this.Climb[0] = climb1.getImage();
		this.Climb[1] = climb2.getImage();
		this.Climb[2] = climb3.getImage();
		this.Climb[3] = climb4.getImage();
		this.WRIGHT[0] = ii1.getImage();
		this.WRIGHT[1] = ii2.getImage();
		this.WLEFT[0] = ii3.getImage();
		this.WLEFT[1] = ii4.getImage();
		this.Swalk[0] = Swalk1.getImage();
		this.Swalk[1] = Swalk2.getImage();
		this.SwalkL[0] = Swalk3.getImage();
		this.SwalkL[1] = Swalk4.getImage();

		w = WRIGHT[0].getWidth(null);
		h = WRIGHT[0].getHeight(null);

	}
	public boolean isQuit() {
		return Quit;
	}
	public int getdx() {

		return this.dx;
	}

	public int getdy() {

		return this.dy;
	}

	public void setX(int x) {

		this.x = x;
	}

	public int gethealth() {
		return this.health;
	}

	public void setY(int y) {

		this.y = y;
	}

	public int getX() {

		return this.x;
	}

	public int getY() {

		return this.y;
	}

	public int getWidth() {

		return w;
	}

	public int getHeight() {

		return h;
	}

	public int getIntoMap() {

		return intomap;
	}
	public int getIntoFight() {

		return intofight;
	}

	public boolean getInteract() {

		return interact;
	}

	public boolean isClimbing() {

		return climbing;
	}

	public boolean isWalking() {

		return walking;
	}
	public boolean Talkmessage() {
		return pushTalk;
	}
	public void setTalkmessage(boolean pushTalk){
		this.pushTalk=pushTalk;
	}


	public void setInteract(boolean inter) {
			this.interact = inter;




	}

	public void setDark(boolean dark) {
		this.dark = dark;
	}

	public boolean getDark() {
		return this.dark;
	}

	public Image getImage(int p) {

		if (climbing) {
			if (dy == 0) {
				return Climb[dx];
			}
			return Climb[p];
		}
		if (walking) {
			if (dx == 0 && dy == 0) {
				if (lastdir == -1) {
					return (dark == false) ? WLEFT[dx] : SwalkL[dx];
				}
				if (lastdir == 1) {
					return (dark == false) ? WRIGHT[dx] : Swalk[dx];
				}
			}
			if (dx >= 1) {
				stopwalk = true;
				lastdir = dx;
				return (dark == false) ? WRIGHT[p] : Swalk[p];

			} else if (dx <= -1) {
				stopwalk = true;
				lastdir = dx;
				return (dark == false) ? WLEFT[p] : SwalkL[p];

			}

			if (dy >= 1 || dy <= -1) {

				if (stopwalk == true) {

					return (!dark) ? WRIGHT[p] : Swalk[p];
				} else {
					return (!dark) ? WLEFT[p] : SwalkL[p];
				}

			}

		}
		return (stopwalk == true) ? WRIGHT[dx] : WLEFT[dx];
	}

	public boolean isPushTalk() {
		return pushTalk;
	}

	public boolean isGameOrFight() {
		return GameOrFight;
	}
	private void PixelsCheck(short[] ScreenData, int nblocks_x, int nblocks_y, int blocksize,int H,int W){
		System.out.printf("(%d,%d)\n)",this.x, this.y);
		int pos, Npos, Ppos, Rpos, Upos, UP, LEFT, RIGHT, DOWN;
		int NextX, NextY, PrevX, PrevY;
		int ch, Nch, Pch, Uch, Rch;
		NextX = this.x + (dx * blocksize);
		NextY = this.y + (dy * blocksize);

		RIGHT = this.x + (2 * dx * blocksize);
		LEFT = this.x - blocksize;
		DOWN = this.y + blocksize;
		UP = this.y - (dy * blocksize);

		if (this.x % blocksize == 0 && this.y % blocksize == 0) {

			pos = (int) (this.x / blocksize) + nblocks_y * (int) (this.y / blocksize);
			Npos = (int) (NextX / blocksize) + nblocks_y * (int) (NextY / blocksize);
			Upos = (int) (RIGHT / blocksize) + nblocks_y * (int) (UP / blocksize);
			Rpos = (int) (RIGHT / blocksize) + nblocks_y * (int) (NextY / blocksize);

			ch = ScreenData[pos];
			Nch = ScreenData[Npos];
			Uch = ScreenData[Upos];
			Rch = ScreenData[Rpos];
			System.out.println("(" + this.x + "," + this.y + ")");
			System.out.println("pos: " + pos);
			System.out.println("Nch: " + Nch);
			System.out.println("ch: " + (ch & 4));
			if ((ch & 1) == 1 && (ch != 0) && (Nch != 0)) {
				this.x = this.x + (dx * blocksize);
				this.y = this.y + (dy * blocksize);
				intofight++;
				walking=true;
				interact = false;
				interactPressed=false;
				pushTalk=false;
				GameOrFight=false;

			}
			if ((ch & 4) == 4 && (ch != 0) && Nch != 0) {
				walking = true;
				interact = false;
				climbing = false;
				pushTalk=false;
				interactPressed=false;
				GameOrFight=true;

				if ((intomap == 6 || intomap == 7) && Rch == 0) {
					this.x = this.x + (2 * dx * blocksize);
					this.y = this.y - (dx * blocksize);
				} else {
					this.x = this.x + (dx * blocksize);
					this.y = this.y + (dy * blocksize);
				}

			}




			if ((ch & 5) == 5 && (ch != 0) && (Nch != 0)) {
				interact = false;
				pushTalk=false;
				interactPressed=false;
				GameOrFight=true;

				if (intomap == 1) {
					intomap++;
					this.x = 24;

				} else if (intomap == 2) {

					this.x = 24;
					intomap++;

				} else if (intomap == 3) {

					if (this.x > 600) {
						this.x = 24;
						intomap++;
					} else {

						this.x = 276;
						this.y = 444;
						intomap = 6;
						this.dark = true;
					}
				} else if (intomap == 4) {
					this.x = 396;
					this.y = 20;
					intomap++;

				} else if (intomap == 6) {
					this.x = 36;
					this.y = 264;
					intomap++;
				} else if (intomap ==7) {
					intomap++;
					this.x=36;
					this.y=396;
					this.dark=false;
				}
				else if (intomap ==8) {
					intomap++;
					this.x=432;
					this.y=336;

				}


			}
			if ((ch & 6) == 6 && (ch != 0) && (Nch != 0)) {
				interact = false;
				interactPressed=false;
				pushTalk=false;
				if (intomap == 2) {
					this.x = 720;
					intomap--;
				}
				if (intomap == 3) {
					this.x = 540;
					intomap--;
				}
				if (intomap == 4) {
					this.x = 720;
					intomap--;
				}
				if (intomap == 6) {
					this.x = 324;
					this.y = 480;
					intomap = 3;
					this.dark = false;
				}
				if (intomap == 7) {
					this.x = 732;
					this.y = 324;
					intomap = 6;

				}
				if (intomap == 8) {
					this.x = 420;
					this.y = 156;
					intomap = 7;
					dark=true;
				}
				else if (this.intomap == 9) {
					this.x = 288;
					this.y = 144;
					this.intomap = 8;
					this.dark = false;
				}

			}

			if ((ch & 2) == 2 && (ch != 0) && (Nch != 0)) {

				this.x = this.x + (dx * blocksize);
				this.y = this.y + (dy * blocksize);
				if(!interactPressed) {
					pushTalk=true;


				}
				if (interactPressed) {
					interact = true;

				}
				if (pushTalk==false){
					interact=false;
				}

			}
			if ((ch & 9) == 9 && (ch != 0) && (Nch != 0)) {
				climbing = true;
				walking = false;
				pushTalk=false;
				if ((Nch & 5) == 5) {
					if (intomap == 4) {

						this.y = 24;
						intomap++;
					}
				}
				if ((Nch & 6) == 6) {
					if (intomap == 5) {

						interact = false;
						interactPressed=false;
						this.y = 480;
						intomap--;
					}
					this.y = this.y + (dy * blocksize);
				}
			}
		}
	}
	public void move(short[] ScreenData, int nblocks_x, int nblocks_y, int blocksize,int H,int W) {

			if (GameOrFight){
				PixelsCheck( ScreenData,  nblocks_x,  nblocks_y,  blocksize, H, W);

			}
			else{
				System.out.println(this.x+" , "+this.y);
				this.x = this.x + dx * 12;
				if (this.y + this.h < 540) {
					this.Velocity += 1;

					y += this.Velocity;
					this.JUMP = false;

				} else {

					on_ground = true;
				}

				if (this.JUMP) {
					this.on_ground = false;
					this.JUMP = false;

					this.Velocity = -this.jump_height;
					this.y += Velocity;

				}
			}
	}
	public void setPushTalk(boolean x){
		this.pushTalk=x;
	}
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_Q) {

			dx = -1;

		}

		if (key == KeyEvent.VK_D) {

			dx = 1;
		}

		if (key == KeyEvent.VK_Z) {
			dy = -1;
		}

		if (key == KeyEvent.VK_S) {
			dy = 1;
		}
		if (key == KeyEvent.VK_F) {
			interactPressed = true;

		}
		if (key == KeyEvent.VK_SPACE) {
			JUMP=true;

		}
		if
		(key==KeyEvent.VK_ESCAPE){
			Quit=true;
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_Q) {
			dx = 0;
			stopwalk = true;
		}

		else if (key == KeyEvent.VK_D) {
			dx = 0;
			stopwalk = true;
		}
		else if (key == KeyEvent.VK_F) {


		}
		else if (key == KeyEvent.VK_Z) {
			dy = 0;
			stopwalk = true;
		}

		else if (key == KeyEvent.VK_S) {
			dy = 0;
			stopwalk = true;
		}
	}
}
