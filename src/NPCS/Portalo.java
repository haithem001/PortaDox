package NPCS;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Portalo {
	public int x;
	public int y;
	
	private int w;
	private int h;
	
	private Image[] IDOL = new Image[2];
	private Image[] IDOLL = new Image[2];
	private String[] Dialog = {"Hello! My name is Portalo \n the guardian of this portal", 
			"I can only allow you to enter \n if you have a sword",
			"Sorry i cant allow you to enter",
			"Hmm! Hou got a sword ,i can allow you now",
			"Be careful!",
			" "};
	public Portalo() {
		
		loadNPC();
		
	}
	private void loadNPC() {
		
		
		ImageIcon ii1=new ImageIcon("src/Portal1NPC.png");
		ImageIcon ii2=new ImageIcon("src/Portal2NPC.png");
		ImageIcon ii3=new ImageIcon("src/Portal1NPCL.png");
		ImageIcon ii4=new ImageIcon("src/Portal2NPCL.png");
		this.IDOL[0]=ii1.getImage();
		this.IDOL[1]=ii2.getImage();
		this.IDOLL[0]=ii3.getImage();
		this.IDOLL[1]=ii4.getImage();
		 w = IDOL[0].getWidth(null);
		 h = IDOL[0].getHeight(null);
		
		
		 
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
  
    public int getWidth() {
        
        return w;
    }
    
    public int getHeight() {
        
        return h;
    }  
    public String loadDialogue(int p) {
    	return Dialog[p];
    }
    public Image getImage(int p, int look) {
        return (look>=this.x) ? IDOL[p] : IDOLL[p];
  
    } 
	
}
