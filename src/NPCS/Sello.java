package NPCS;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Sello {
	public int x;
	public int y;
	
	private int w;
	private int h;
	
	private Image IDOL ;
	private Image Market;
	private String[] Dialog = {}; 
	public Sello() {
		
		loadNPC();
		
	}
	private void loadNPC() {
		
		
		ImageIcon ii1=new ImageIcon("src/SelloNPC.png");
		ImageIcon selloMarket=new ImageIcon("src/Maps/SelloMarket.png");
		Market=selloMarket.getImage();
		IDOL=ii1.getImage();
			
		 w = IDOL.getWidth(null);
		 h = IDOL.getHeight(null);
		
		
		 
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
    public Image getImage() {
        return  IDOL;
  
    }
    public Image getMarketImage() {
        return  Market;
  
    } 
	
}
