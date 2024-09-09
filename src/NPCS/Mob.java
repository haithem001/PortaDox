package NPCS;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Mob {
    public int x;
    public int y;
    public int w;
    public int h;
    private int startX,startY;
    private boolean alive = true;
    private int dir=-5;
    private int dy=5;

    public Rectangle BODY;
    public List<Bullet> bullets=new ArrayList<>();
    private  Image[] img =new Image[2];
    private  Image imgExplode;
    private int mobAnimCount =2;
    private int MOB_ANIM_DELAY =20;
    private int mobAnimPos=0;
    private int mobAnimDir=1;
    private int AnimCount=2;
    private int Velocity=0;
    private boolean on_ground=false;


    public  Mob (int x, int y,int level) {

        this.x = x;
        this.y = y;
        this.startX=x;
        ImageIcon image=new ImageIcon("/src/Explosion.png");

        this.alive=true;
        if (level==1){
            ImageIcon A1 = new ImageIcon("src/Alien Fight Map/A1.png");
            ImageIcon A2 = new ImageIcon("src/Alien Fight Map/A2.png");
            this.img = new Image[]{A1.getImage(),A2.getImage()};
            w=img[0].getWidth(null);
            h=img[0].getHeight(null);
        }





    }
    public void UpdateMob() {

        mobAnimCount--;
        if (mobAnimCount <= 0) {

            mobAnimCount = MOB_ANIM_DELAY;

            mobAnimPos = mobAnimPos + mobAnimDir;
            if (mobAnimPos == (AnimCount - 1) || mobAnimPos == 0) {

                mobAnimDir = -mobAnimDir;

            }
        }

    }
    public int getDy() {
        return dy;
    }
    public Image getImage(int p) {
        return this.img[p];
    }
    public Image getImgExplode() {
        return imgExplode;
    }

    public  int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }
    public int getH() {
        return h;
    }
    public void setX(int x) {
        this.x = x;
        this.BODY.x=x;
    }
    public void setY(int y) {
        this.y = y;
        this.BODY.y=y;
    }
    public void move(int Plateform){
        if(!on_ground){
            if (this.y  < Plateform-this.w) {
                this.Velocity += 1;

                y += this.Velocity;

            } else {

                on_ground = true;
            }
        }
        else{
            this.x+=dir;

        }





    }
    public void shoot(int x, int y, int playerX,int playerY){
        bullets.add(new Bullet(x,y,playerX,playerY));
    }
    public void setW(int w) {
        this.w = w;
    }
    public void setH(int h) {
        this.h = h;
    }


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void  DrawMob(Graphics2D g2){
        switch (mobAnimPos){
            case 0:
                g2.drawImage(this.getImage(0),this.x,this.y,null);
                break;
            case 1:
                g2.drawImage(this.getImage(1),this.x,this.y,null);
                break;
        }


    }

}
