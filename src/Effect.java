import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Effect {
    private Image[] Images = new Image[6];

    private int h;
    private int w;
    private int x;
    private int y;
    private int effectAnimCount=20;
    private int effectAnimPos=0;
    private int effectAnimDir=1;

    Effect(int x,int y ){
        this.x = x;
        this.y = y;

        ImageIcon ii1 = new ImageIcon("src/Effects/S1.png");
        ImageIcon ii2 = new ImageIcon("src/Effects/S2.png");
        ImageIcon ii3 = new ImageIcon("src/Effects/S3.png");
        ImageIcon ii4 = new ImageIcon("src/Effects/S1L.png");
        ImageIcon ii5 = new ImageIcon("src/Effects/S2L.png");
        ImageIcon ii6 = new ImageIcon("src/Effects/S3L.png");

        this.Images[0] = ii1.getImage();
        this.Images[1] = ii2.getImage();
        this.Images[2] = ii3.getImage();
        this.Images[3] = ii4.getImage();
        this.Images[4] = ii5.getImage();
        this.Images[5] = ii6.getImage();
        this.h=this.Images[0].getHeight(null);
        this.w=this.Images[0].getWidth(null);

    }

    public void UpdateEffect() {

        effectAnimCount--;
        if (effectAnimCount <= 0) {
            effectAnimCount =7;
            effectAnimPos = effectAnimPos + effectAnimDir;
            if (effectAnimPos == 3 ) {
                effectAnimPos=-1;


            }
        }

    }
    public void PlayEffect(Graphics2D g,int dx,Item item){
        int angle=0;
        if (dx>=0){
            switch (effectAnimPos){
                case 0:
                    g.drawImage(item.getImage(dx),this.x-10,this.y-20,null);
                    g.drawImage(this.Images[0],this.x +50,this.y-10,this.w,this.h,null);
                    break;
                case 1:
                     angle = 30;
                    g.rotate(Math.toRadians(angle), this.x - this.w / 2, this.y + this.h / 2);
                    g.drawImage(item.getImage(dx),this.x-10,this.y-20,null);
                    g.rotate(Math.toRadians(-angle), this.x - this.w / 2, this.y + this.h / 2);
                    g.drawImage(this.Images[1],this.x+50,this.y-10,this.w,this.h,null);
                    break;
                case 2:
                    angle = 45;
                    g.rotate(Math.toRadians(angle), this.x - this.w / 2, this.y + this.h / 2);
                    g.drawImage(item.getImage(dx),this.x-10,this.y-20,null);
                    g.rotate(Math.toRadians(-angle), this.x - this.w / 2, this.y + this.h / 2);
                    g.drawImage(this.Images[2],this.x+50,this.y-10,this.w,this.h,null);
                    break;

                default:break;

            }
        }else {
            switch (effectAnimPos){
                case 0:
                    g.drawImage(item.getImage(dx),this.x-50,this.y,null);
                    g.drawImage(this.Images[3],this.x-60,this.y,this.w,this.h,null);
                break;
                case 1:
                    angle = -30;
                    g.rotate(Math.toRadians(angle), this.x + this.w / 2, this.y + this.h / 2);
                    g.drawImage(item.getImage(dx),this.x-50,this.y,null);
                    g.rotate(Math.toRadians(-angle), this.x + this.w / 2, this.y + this.h / 2);

                    g.drawImage(this.Images[4],this.x-60,this.y,this.w,this.h,null);
                break;
                case 2:
                    angle = -60;
                    g.rotate(Math.toRadians(angle), this.x + this.w / 2, this.y + this.h / 2);

                    g.drawImage(item.getImage(dx),this.x-50,this.y,null);
                    g.rotate(Math.toRadians(-angle), this.x + this.w / 2, this.y + this.h / 2);
                    g.drawImage(this.Images[5],this.x-60,this.y,this.w,this.h,null);
                break;
                default:break;
            }
        }



    }
    public void setX(int x){
    this.x=x;
    }
    public void setY(int y){
    this.y=y;
    }
    public void reset(){
        this.effectAnimPos=0;
    }

}
