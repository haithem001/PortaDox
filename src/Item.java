import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Item extends JComponent{
	private int w;
	private int h;
	private int x;
	private int y;
	private int Id=0;
	private int damage=0;
	private int heal=0;
	private int shield=0;
	private boolean functional=false;
	private boolean exist ;
	private Image image ,imageR;
	private int JumpHeight=10;
	private int Velocity;
	private int floor =50;
	private int inity=0;
	public boolean bounce=false;
	private boolean onground=true;


	Item(int Id,int damage,int heal,int shield,boolean functional,boolean exist,int x,int y){
		this.damage=damage;
		this.heal=heal;
		this.shield=shield;
		this.functional=functional;
		this.exist=exist;
		this.Id=Id;
		this.x=x;
		this.y=y;
		this.setVisible(true);

			ImageIcon item =new ImageIcon("src/Tools/"+Integer.toString(Id)+".png");
			ImageIcon itemR =new ImageIcon("src/Tools/"+Integer.toString(Id)+"R.png");
			image=item.getImage();
			imageR=itemR.getImage();

			w=image.getWidth(null); 
			h=image.getHeight(null);
		
		this.inity=this.y;
		
	}
	
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	public int getW() {
		return this.w;
	}
	public int getH() {
		return this.h;
	}
	public Image getImage(int dx) {
		if(dx>=0){return image;}
		else{return imageR;}
	}
	public void setExist(boolean b){
		this.exist=b;
		if (b==false){
			this.Id=0;
			this.image=null;
			this.damage=0;
			this.heal=0;
			this.shield=0;
			this.functional=false;

		}
	}

	
	public boolean isExisted() {
		return this.exist;
	}

	public int getId() {
		return this.Id;
	}

	public void setId(int i) {

		this.Id=i;
		ImageIcon item =new ImageIcon("src/Tools/"+Integer.toString(i)+".png");
		ImageIcon itemR =new ImageIcon("src/Tools/"+Integer.toString(i)+"R.png");
		image=item.getImage();
		imageR=itemR.getImage();

	}

	public void setHeal(int i) {
		this.heal=i;
	}

	public void setShield(int i) {
		this.shield=i;
	}

	public void setFunctional(boolean b) {
		this.functional=b;
	}

	public void setDamage(int i) {
		this.damage=i;
	}

	public int getDamage() {
		return damage;
	}

	public int getHeal() {
		return heal;
	}

	public int getShield() {
		return shield;
	}

	public void bounceItem(){
		if(!this.bounce && onground){
			this.bounce=true;
			this.onground=false;
			this.Velocity = -14;
			this.y += Velocity;
		}
		if(!onground){
			this.Velocity += 3;
			y += this.Velocity;
			this.x+=15;
			y+=Velocity;
			if (this.y>=this.inity){
				this.y=this.inity;
				onground=true;


			}


		}
	}

	public boolean isFunctional() {
		return functional;
	}
}
