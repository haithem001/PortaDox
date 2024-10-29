import NPCS.Bullet;
import NPCS.Mob;
import NPCS.Wave;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Dude {

	private boolean Attacking=false;
	private int dx=0,savedX;
	private int dy,MemoryX,MemoryY;
	public int x;
	public int y;
	public int Plateform;
	private boolean pushTalk =false;
	private boolean stopwalk;
	private int lastdir;
	private boolean resetanim;
	private boolean stopclimb = true;
	private int w;
	private int h;
	private int health = 6;
	private boolean interact = false;
	private boolean climbing = false;
	private boolean interactPressed = false;
	private boolean walking = true;
	private int intomap = 1;
	private int intofight = 0;
	private boolean Fight = false;
	private boolean GameOrFight=true;
	private Image[] WRIGHT = new Image[2];
	private Image[] WLEFT = new Image[2];
	private Image[] WRIGHTF = new Image[2];
	private Image[] WLEFTF = new Image[2];
	private Image[] Health = new Image[6];
	private int Velocity = 10;
	public boolean JUMP = false;
	public boolean on_ground = true;
	private int jump_height = 18;
	public int DamageEffect=0;
	private Item selectedItem = null;
	private boolean bounce=false;
	private boolean Quit=false;
	private Image[] Climb = new Image[2];
	private Image[] Swalk = new Image[2];
	private Image[] SwalkL = new Image[2];
	private boolean dark = false;
	public Rectangle solidarea;
	private boolean allowWalk = false;
	private boolean damage=false;
	private boolean trig=false;
	private int columnSelected=-1;
	private boolean win=false;
	private boolean Action=false;
	public int level=1;
	private int Levels =0;
	public Dude() {
		loadPlayerIms();

	}
	public boolean getFight() {
        return this.Fight;
    }
	public void setFight(boolean Fight) {
		if(this.getIntoMap()==1)
		{
			intofight=1;
		}

		this.Fight = Fight;
	}

	public int getSavedX() {
		return savedX;
	}

	private void loadPlayerIms() {
		ImageIcon ii1 = new ImageIcon("src/player1.png");
		ImageIcon ii2 = new ImageIcon("src/Player2.png");
		ImageIcon ii3 = new ImageIcon("src/player1L.png");
		ImageIcon ii4 = new ImageIcon("src/Player2L.png");

		this.WRIGHT[0] = ii1.getImage();
		this.WRIGHT[1] = ii2.getImage();
		this.WLEFT[0] = ii3.getImage();
		this.WLEFT[1] = ii4.getImage();

		ImageIcon climb1 = new ImageIcon("src/playerClimb1.png");
		ImageIcon climb2 = new ImageIcon("src/playerClimb2.png");

		ImageIcon Swalk1 = new ImageIcon("src/PlayerShadowwalk1.png");
		ImageIcon Swalk2 = new ImageIcon("src/PlayerShadowwalk2.png");
		ImageIcon Swalk3 = new ImageIcon("src/PlayerShadowwalk1L.png");
		ImageIcon Swalk4 = new ImageIcon("src/PlayerShadowwalk2L.png");

		ImageIcon Health6= new ImageIcon("src/Health/1.png");
		ImageIcon Health5= new ImageIcon("src/Health/2.png");
		ImageIcon Health4= new ImageIcon("src/Health/3.png");
		ImageIcon Health3= new ImageIcon("src/Health/4.png");
		ImageIcon Health2= new ImageIcon("src/Health/5.png");
		ImageIcon Health1= new ImageIcon("src/Health/6.png");
		Health[5]= Health6.getImage();
		Health[4]= Health5.getImage();
		Health[3]= Health4.getImage();
		Health[2]= Health3.getImage();
		Health[1]= Health2.getImage();
		Health[0]= Health1.getImage();

		this.Climb[0] = climb1.getImage();
		this.Climb[1] = climb2.getImage();


		this.Swalk[0] = Swalk1.getImage();
		this.Swalk[1] = Swalk2.getImage();
		this.SwalkL[0] = Swalk3.getImage();
		this.SwalkL[1] = Swalk4.getImage();

		w = WRIGHT[0].getWidth(null);
		h = WRIGHT[0].getHeight(null);
		this.Plateform=530 - this.h;

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
	public Image getHealthImage(){
		if (health>0){
			return Health[health-1];
		}
		return Health[0];
	}
	public int gethealth() {
		return this.health;
	}
	public void setY(int y) {

		this.y = y;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem=selectedItem;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public int getX() {

		return this.x;
	}
	public boolean getTrig(){

		return trig;

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

	public void setAction(boolean action) {
		Action = action;
	}
	public boolean getAction() {

		return Action;
	}

	public void setDark(boolean dark) {
		this.dark = dark;
	}

	public boolean getDark() {
		return this.dark;
	}

	public Image getImage(int p) {
//		if (Attacking){
//
//			return WRIGHTF[p];
//		}
		if (!GameOrFight){
			if (damage){
				if (savedX < 0){
					return WLEFT[p];
				}
				else if (savedX >= 0){
					return WRIGHT[p];
				}
			}else
			if (Attacking){

				if (dx == 0 && dy == 0) {
					if (lastdir == -1) {
						return WLEFT[p];
					}
					if (lastdir == 1) {
						return WRIGHT[p];
					}
				}
				if (dx >= 1) {
					stopwalk = true;
					lastdir = dx;
					return WRIGHT[p];

				} else if (dx <= -1) {
					stopwalk = true;
					lastdir = dx;
					return WLEFT[p];

				}

			}else if (walking){
				if (dx == 0 && dy == 0) {
					if (lastdir == -1) {
						return  WLEFT[dx] ;
					}
					if (lastdir == 1) {
						return  WRIGHT[dx];
					}
				}
				if (dx >= 1) {
					stopwalk = true;
					lastdir = dx;
					return  WRIGHT[p];

				} else if (dx <= -1) {
					stopwalk = true;
					lastdir = dx;
					return   WLEFT[p];

				}
			}
		}

		else if (GameOrFight){
            if (climbing) {
                if (dy == 0) {
                    return Climb[dx];
                }
                return Climb[p];
            }

            else if (walking) {
                if (dx == 0 && dy == 0) {
                    if (lastdir == -1) {
                        return (!dark) ? WLEFT[dx] : SwalkL[dx];
                    }
                    if (lastdir == 1) {
                        return (!dark) ? WRIGHT[dx] : Swalk[dx];
                    }
                }
                if (dx >= 1) {
                    stopwalk = true;
                    lastdir = dx;
                    return (!dark) ? WRIGHT[p] : Swalk[p];

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
		}

		return (stopwalk == true) ? WRIGHT[dx] : WLEFT[dx];
	}

	public boolean isPushTalk() {
		return pushTalk;
	}

	public boolean isGameOrFight() {
		return GameOrFight;
	}
	public void use_Selected_Item(){
		if (this.selectedItem!=null){
			if (this.selectedItem.getHeal()!=0){

				switch (this.selectedItem.getId()){
					case 40:

						this.health += 4;
						if (health>6){
							health=6;
						}
						this.selectedItem.setExist(false);
						break;
					case 41:
						this.health += 1;
						if (health>6){
							health=6;
						}
						this.selectedItem.setExist(false);

						break;
					case 42:
						this.health += 2;
						if (health>6){
							health=6;
						}
						this.selectedItem.setExist(false);

						break;
					case 43:
						this.health += 3;
						if (health>6){
							health=6;
						}
						this.selectedItem.setExist(false);

						break;
				}

			}if(this.selectedItem.getDamage()!=0){
				this.setAttack(true);

			}
		}

	}
	private void PixelsCheck(short[] ScreenData, int nblocks_x, int nblocks_y, int blocksize,int H,int W){
		System.out.printf("(x = %d, y = %d)",this.x, this.y);
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
			System.out.println("pos: " + pos + " Nch: " + Nch+ " ch: " + (ch));

			if ((ch & 1) == 1 && (ch != 0) && (Nch != 0)) {
				this.x = this.x + (dx * blocksize);
				this.y = this.y + (dy * blocksize);
				walking=true;
				interact = false;
				interactPressed=false;
				pushTalk=false;
				GameOrFight=false;
				Fight=true;

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
					this.y = 48;
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
					if (this.x<200){
						intomap++;
						this.x=432;
						this.y=336;
					}else if (this.x>200&& this.y<700){
						intomap+=2;
						this.x = 360;
						this.y = 492;
					}


				}else if (intomap == 10) {
					interactPressed=false;
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
				if (intomap == 5) {
					this.y=504;
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
				else if (this.intomap==10){
					this.x=528;
					this.y = 84;
					this.intomap = 8;
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

				}
				if (intomap==8 && interactPressed){


				}

			}
			if ((ch & 8) == 8 && (ch != 0) && (Nch != 0)) {

				this.x = this.x + (dx * blocksize);
				this.y = this.y + (dy * blocksize);
				if (trig){
					Action = true;
				}


			}
			if ((ch & 9) == 9 && (ch != 0) && (Nch != 0)) {
				climbing = true;
				walking = false;
				pushTalk=false;
				GameOrFight=true;
				this.y = this.y + (dy * blocksize);



			}
		}
	}
	public void setWin(boolean win){
		if ( this.win ){
			if (this.intomap==11){
				this.setX(204);
				this.setY(348);
				this.win = win;

			}
		}

	}
	public boolean getPushtalk(){
		return pushTalk;
	}
	public void checkHit(Mob[] Mobs, Wave wave) {
		if (!wave.isEnded()){
			for(int i =0;i<Mobs.length;i++){
				if(Mobs[i]!=null){
					if (Mobs[i].getType()==0) {
						if(Mobs[i].bullets!=null ){
							if (!Mobs[i].bullets.isEmpty()){
								for (Bullet b :Mobs[i].bullets){
									if (b.getX()<this.x+this.w &&( b.getY()+b.getH()>this.y && b.getY()<this.y+this.h) && b.getX()+b.getW()>this.x){
										if (b.visible){
											Damaged();
											b.visible=false;

										}
										break;

									}
								}
							}

						}
					}
					else if (Mobs[i].getType()==1){
						if (Mobs[i].getX()+ Mobs[i].getW()/2 - 5 < this.x+this.w && Mobs[i].getX()+ Mobs[i].getW()/2 + 5 > this.x && Mobs[i].getMobAnimPos()==4){
							Mobs[i].setMobAnimPos(0);
							Damaged();
						}
					}

				}
			}
		}
		else{
			if(wave.Boss!=null){
				if (x<wave.Boss.getX()){
					this.x+=5;

				}
				if (wave.Boss.BossMeteor!=null){
					if ( this.y+this.getHeight() > wave.Boss.BossMeteor.getY() || this.y>wave.Boss.BossMeteor.getY() ){
						if(this.x+this.getWidth()/2>wave.Boss.BossMeteor.getX() && this.x<wave.Boss.BossMeteor.getX()+wave.Boss.BossMeteor.getW()/2){
							Damaged();
							wave.Boss.BossMeteor=null;
						}
					}
					if(this.x>wave.Boss.getX()){
						Damaged();
					}
				}
			}
		}

	}
	public void Bounce(){
		this.bounce=true;
	}
	public void checkWin(Mob Boss,int px){
		if (Boss!=null){
			if (Boss.getHealth()<1){
				if(this.x>px){
					this.Fight=false;
					this.GameOrFight=true;
					intomap=11;
					intofight=0;
					win=true;

				}
			}
		}
	}
	public void Damaged(){
		this.damage=true;


		this.health--;
	}
	public int savedDx(){
		return this.dx;
	}
	public void move(short[] ScreenData, int nblocks_x, int nblocks_y, int blocksize,int H,int W) {

			if (GameOrFight){
				PixelsCheck( ScreenData,  nblocks_x,  nblocks_y,  blocksize, H, W);
			}
			else {

				if ( x >= 36 && x < W ) {
					this.x = this.x + dx * 12;
				}

				if ( x < 36 ) {
					this.x = 36;
				}

// Gravity effect
				if ( this.y < 530 - this.h ) {
					this.Velocity +=5; // Increase downward velocity (falling)
					this.y += this.Velocity; // Apply velocity to vertical position
					this.JUMP = false;
				} else {
					// Ensure object lands on the ground and velocity is reset
					this.y = 530 - this.h; // Object stays on the ground
					this.Velocity = 0;
					this.on_ground = true; // Object is on the ground
				}

				if ( this.JUMP && this.on_ground ) {
					// If jump is triggered and the object is on the ground, allow the jump
					this.on_ground = false;
					this.JUMP = false;
					this.Velocity = -40; // Set upward velocity for the jump
					this.y += this.Velocity; // Apply initial jump velocity

				}

			}
		this.MemoryX=x;
		this.MemoryY=y;
	}
	public void setPushTalk(boolean x){
		this.pushTalk=x;
	}
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_Q) {

				dx = -1;
				savedX=dx;



		}

		if (key == KeyEvent.VK_D) {


			dx = 1;
			savedX=dx;
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
		if(key== KeyEvent.VK_E){
			trig=true;

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
		else if(key== KeyEvent.VK_E){
			trig=false;

		}
		else if (key == KeyEvent.VK_Z) {
			dy = 0;
			stopwalk = true;
		}

		if (key == KeyEvent.VK_S) {

			dy = 0;	stopwalk = true;
		}
	}
	public boolean isDamaged(){
		return this.damage;
	}
	public void setWalkingSprites(){
		ImageIcon ii1 = new ImageIcon("src/player1.png");
		ImageIcon ii2 = new ImageIcon("src/Player2.png");
		ImageIcon ii3 = new ImageIcon("src/player1L.png");
		ImageIcon ii4 = new ImageIcon("src/Player2L.png");
		ImageIcon ii1F = new ImageIcon("src/PlayerFight1.png");
		ImageIcon ii2F = new ImageIcon("src/PlayerFight2.png");
		ImageIcon ii3F = new ImageIcon("src/PlayerFight1L.png");
		ImageIcon ii4F = new ImageIcon("src/PlayerFight2L.png");
		ImageIcon F1 = new ImageIcon("src/PlayerFightSlash1.png");
		ImageIcon F2 = new ImageIcon("src/PlayerFightSlash2.png");
		ImageIcon F3 = new ImageIcon("src/PlayerFightSlash1L.png");
		ImageIcon F4 = new ImageIcon("src/PlayerFightSlash2L.png");
		ImageIcon Hit=new ImageIcon("src/PHIT.png");
		ImageIcon HitL=new ImageIcon("src/PHITL.png");

		if (GameOrFight){
			switch (level){
				case 1:
					this.WRIGHT[0] = ii1.getImage();
					this.WRIGHT[1] = ii2.getImage();
					this.WLEFT[0] = ii3.getImage();
					this.WLEFT[1] = ii4.getImage();
					break;
				case 2:



				ImageIcon ii11 = new ImageIcon("src/Player11.png");
				ImageIcon ii12 = new ImageIcon("src/Player12.png");
				ImageIcon ii13 = new ImageIcon("src/Player11L.png");
				ImageIcon ii14 = new ImageIcon("src/Player12L.png");

				this.WRIGHT[0] = ii11.getImage();
				this.WRIGHT[1] = ii12.getImage();
				this.WLEFT[0] = ii13.getImage();
				this.WLEFT[1] = ii14.getImage();
				break;

			}

		}else{
			if (damage) {
				this.WRIGHT[0] = ii1F.getImage();
				this.WRIGHT[1] =Hit.getImage();
				this.WLEFT[0] = ii3F.getImage();
				this.WLEFT[1] = HitL.getImage();
			}
			else if(Attacking){
				this.WRIGHT[0]=F1.getImage();
				this.WRIGHT[1]=F2.getImage();
				this.WLEFT[0]=F3.getImage();
				this.WLEFT[1]=F4.getImage();

			}else if (walking ){
				this.WRIGHT[0]=ii1F.getImage();
				this.WRIGHT[1]=ii2F.getImage();
				this.WLEFT[0]=ii3F.getImage();
				this.WLEFT[1]=ii4F.getImage();
			}


		}
    }



	public boolean isAttacking() {
		return Attacking;
	}

	public void setAttack(boolean b) {
		Attacking = b;
	}

	public void setDamaged(boolean b) {
		this.damage = b;
	}

	public int getMemoryY() {
		return MemoryY;
	}
	public int getMemoryX(){
		return MemoryX;
	}

	public void setColumnSelected(int column) {
		this.columnSelected=column;
	}
	public int getColumnSelected(){
		return this.columnSelected;
	}

	public void setTrig(boolean b) {
		this.trig = b;
	}

	public boolean isWin() {
		return win;
	}
}
