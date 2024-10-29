package NPCS;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Buildo {
	public int x;
	public int y;
	
	private int w;
	private int h;
	
	private Image IDOL ;

	private String[] Dialog = {}; 
	public Buildo() {
		
		loadNPC();
		
	}
	private void loadNPC() { 
		
		
		ImageIcon ii1=new ImageIcon("src/Buildo.png");
		
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
   
	
}
