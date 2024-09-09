package NPCS;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Princess {
	public int x;
	public int y;
	
	private int w;
	private int h;
	
	private Image IDOL ;

	private String[] Dialog = {"Help us ","Take this key and go to our king"};
	public Princess() {
		
		loadNPC();
		
	}
	private void loadNPC() { 
		
		
		ImageIcon ii1=new ImageIcon("src/Princess.png");
		
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
