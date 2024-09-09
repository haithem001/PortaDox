import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class InitGame extends JFrame implements ActionListener{
	
	JLabel label;
	JButton play;
	Image logo;
	JPanel p=new JPanel();
	JPanel p1=new JPanel();
	JPanel ALL=new JPanel();
	JLabel image=new JLabel();
	JPanel pbutton=new JPanel();
	JPanel ilabel=new JPanel();
	JPanel plabel=new JPanel();
	Font pixelfont;
	
	InitGame() throws IOException{	
     //LOGO
		try{
			pixelfont = Font.createFont(Font.TRUETYPE_FONT, new File("src/PixellettersFull.ttf"));


		}catch (IOException | FontFormatException e) {
			throw new RuntimeException(e);
		}
       BufferedImage i = ImageIO.read(new File("src/LOGO.png"));
       image=new JLabel(new ImageIcon(i));
       image.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        //welcoming 
       	//p1(plabel,pbutton)
       	//
        play = new JButton();
        label =new JLabel();
        
        
      
        
        ilabel.add(image);
        
        
        plabel.add(label);
        pbutton.add(play);
        this.setUndecorated(true);
		p1.setLayout(new GridLayout(2,1,0,0));
		
		p.setBackground(Color.black);
		pbutton.setBackground(Color.black);
		plabel.setBackground(Color.black);
		ilabel.setBackground(Color.black);
		
		p1.add(plabel);
		p1.add(pbutton);
		

		ALL.add(ilabel);
		ALL.setLayout(new GridLayout(2,1,0,0));
		ALL.add(p1);
		
		this.add(ALL);
		
		//PORTADOX
      	label.setText("Portadox");
      	label.setHorizontalAlignment(JLabel.CENTER);
      	label.setVerticalAlignment(JLabel.CENTER);
      	label.setForeground(Color.white);
      	label.setFont(pixelfont.deriveFont(Font.BOLD, 180));
        label.setBorder(null);
      	label.setVisible(true); 
      
      	
  		
  		
  		//PLAY BUTTON
		
		play.setText("PLAY");
		play.setFocusable(false);
        play.addActionListener(this);
        play.setFont(pixelfont.deriveFont(Font.BOLD, 180));
        play.setBorder(null);
        play.setBackground(Color.black);
        play.setFocusPainted(false);
        play.setVisible(true);
        
	
}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==play) {
			LaunchGame start=new LaunchGame();
			start.getSize(this.getSize());
			start.setBackground(Color.black);
			this.dispose();
		}
		
	}
	
	    
}
