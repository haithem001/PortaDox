package NPCS;

import javax.swing.*;
import java.awt.*;

public class Bullet {
    private final int type;
    double angle;
    int x,y,w,h;
    int dx=1,dy;
    double Vx=1,Vy=1;
    double length;

    Image img;
    Image[] imgs;
    Image[] imgsR;
    Image[] imgsP2;
    Image[] imgsRP2;

    public boolean visible;
    public boolean bounced=false;
    private boolean changed=false;

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public Bullet(int startX, int startY , int targetX, int targetY,int type)
    {   this.type=type;
        x=startX;
        y=startY;
        this.w=10;
        this.h=10;
        float XLEN = targetX - startX;
        float YLEN = targetY - startY;

        length = Math.sqrt(XLEN * XLEN + YLEN * YLEN);



        angle = Math.atan2(targetX- startX, targetY - startY) + Math.PI/2;
        if (type==0){
            ImageIcon newBullet = new ImageIcon("src/Alien Fight Map/Bullet.png");
            img = newBullet.getImage();
            if(XLEN>0){
                Vx=10;
            }else {
                Vx=-10;
            }

        }else if (type==2){
            ImageIcon newBullet1 = new ImageIcon("src/Alien Fight Map/M1.png");
            ImageIcon newBullet2 = new ImageIcon("src/Alien Fight Map/M2.png");
            ImageIcon newBullet3 = new ImageIcon("src/Alien Fight Map/M1R.png");
            ImageIcon newBullet4 = new ImageIcon("src/Alien Fight Map/M2R.png");
            ImageIcon newBullet1P2 = new ImageIcon("src/Alien Fight Map/M1P2.png");
            ImageIcon newBullet2P2 = new ImageIcon("src/Alien Fight Map/M2P2.png");
            ImageIcon newBullet3P2 = new ImageIcon("src/Alien Fight Map/M1RP2.png");
            ImageIcon newBullet4P2 = new ImageIcon("src/Alien Fight Map/M2RP2.png");
            imgs = new Image[]{newBullet1.getImage(),newBullet2.getImage()};
            imgsR = new Image[]{newBullet3.getImage(),newBullet4.getImage()};
            imgsP2 = new Image[]{newBullet1P2.getImage(),newBullet2P2.getImage()};
            imgsRP2 = new Image[]{newBullet3P2.getImage(),newBullet4P2.getImage()};


                Vx=-15;


        }
        visible = true;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(x,y, 19, 23);
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public boolean getVisible()
    {
        return visible;
    }
    public Image getImage()
    {
        return img;
    }

    public Image getImages(int p,int health)
    {   if(dx>0) {
        if (health>=10){
            return imgs[p];

        }else{
            return imgsP2[p];
        }
    }else{
        if (health<10){
            return imgsRP2[p];
        }else{
            return imgsR[p];
            }
        }
    }
    public void move(int W ,int dx,int health)
    {
        if(type==0){
            x += Vx;
        }else if (type==2){
            if (health<10 && !changed){
                changed=true;
                Vx=-40;
            }
            x+= Vx;
        }
        if (x< -100 || x>W){
            visible = false;
        }
    }
    public void setVisible(boolean isVisible)//down
    {
        visible = isVisible;
    }


    public void BounceBack() {
        if (type==2){
            Vx = -Vx;
            this.dx  = -dx;
            this.bounced = true;

        }
    }
    public int getDx() {
        return dx;
    }
}