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

    boolean visible;
    public boolean bounced=false;

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

            imgs = new Image[]{newBullet1.getImage(),newBullet2.getImage()};
            imgsR = new Image[]{newBullet3.getImage(),newBullet4.getImage()};


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

    public Image getImages(int p)
    {   if(dx>0) {
        return imgs[p];
    }else{
        return imgsR[p];
    }
    }



    public void move(int W ,int dx)
    {
        if(type==0){
            x += Vx;
        }else if (type==2){

                x+=Vx;

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
        Vx = -Vx;
        this.dx  = -dx;
        this.bounced = true;


    }

    public int getDx() {
        return dx;
    }
}