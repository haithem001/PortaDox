package NPCS;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mob {
    private boolean bounce=false;
    public int x;
    public int y;
    public int w;
    public int h;
    private int startX,startY;
    private boolean alive = true;
    private int dir=-7;
    private int speed=dir;
    private int dy=5;
    private int health=2;
    private int stop =0;
    public Rectangle BODY;
    public List<Bullet> bullets;
    private  Image[] img =new Image[2];
    private  Image[] imgD =new Image[4];
    private  Image imgExplode;
    private int mobAnimCount =2;
    private int MOB_ANIM_DELAY =20;
    private int mobAnimPos=0;
    private int mobAnimDir=1;
    private int AnimCount=2;
    private int Velocity=0;
    private boolean on_ground=false;
    private int jump_height=10;
    private int savedY=0;
    private int DamageCounter=0;
    private  ImageIcon A1,A2,B1,B2,B3,B4,B5,Hit,HitL,A1L,BOSS1,BOSS2,BOSSHIT,BOSSDIE1,BOSSDIE2,BOSSDIE3,BOSSDIE4;
    private int type=-1;
    private int direction=0;
    private int bouncedir=0;
    private int diry=2;
    public Bullet BossMeteor ;
    private boolean damaged=false;

    public  Mob (int x, int y,int level,int stop,int type) {
        this.stop=stop;
        this.x = x;
        this.type=type;
        this.y = y;
        this.startX=x;
        ImageIcon image=new ImageIcon("/src/Explosion.png");

        this.alive=true;
        if (level==1){
            if (type==0){
                A1 = new ImageIcon("src/Alien Fight Map/A1.png");
                A2 = new ImageIcon("src/Alien Fight Map/A2.png");
                Hit = new ImageIcon("src/Alien Fight Map/AHIT.png");
                HitL=new ImageIcon("src/Alien Fight Map/AHITL.png");
                A1L = new ImageIcon("src/Alien Fight Map/A1L.png");
                this.img = new Image[]{A1.getImage(),A2.getImage()};
                w=img[0].getWidth(null);
                h=img[0].getHeight(null);
                this.savedY=y+this.h;
                bullets=new ArrayList<>();
                this.AnimCount=2;
            }if (type==1){
                B1= new ImageIcon("src/Alien Fight Map/B1.png");
                B2= new ImageIcon("src/Alien Fight Map/B2.png");
                B3= new ImageIcon("src/Alien Fight Map/B3.png");
                B4= new ImageIcon("src/Alien Fight Map/B4.png");
                B5= new ImageIcon("src/Alien Fight Map/B5.png");
                this.y-=230;
                this.img = new Image[]{B1.getImage(),B2.getImage(),B3.getImage(),B4.getImage(),B5.getImage()};
                w=img[0].getWidth(null);
                h=img[0].getHeight(null);
                this.savedY=y+this.h;
                this.AnimCount=5;
                MOB_ANIM_DELAY =13;
                dir=-12;
                health=1;

            }
            if (type == 2)
             {
                 this.y=170;
                 this.x=x;
                 BOSS1= new ImageIcon("src/Alien Fight Map/BOSS1.png");
                 BOSS2= new ImageIcon("src/Alien Fight Map/BOSS2.png");
                 BOSSHIT= new ImageIcon("src/Alien Fight Map/BOSSHIT.png");
                 BOSSDIE1 = new ImageIcon("src/Alien Fight Map/BlackholeDie1.png");
                 BOSSDIE2 = new ImageIcon("src/Alien Fight Map/BlackholeDie2.png");
                 BOSSDIE3 = new ImageIcon("src/Alien Fight Map/BlackholeDie3.png");
                 BOSSDIE4 = new ImageIcon("src/Alien Fight Map/BlackholeDie4.png");
                 this.imgD = new Image[]{BOSSDIE3.getImage(),BOSSDIE4.getImage()};
                 this.img = new Image[]{BOSS1.getImage(),BOSS2.getImage()};
                 w=img[0].getWidth(null);
                 h=img[0].getHeight(null);
                 this.AnimCount=2;
                 MOB_ANIM_DELAY =20;
                 dir=-12;
                 this.health=14;
            }
        }
    }
    public void UpdateMob() {

        mobAnimCount--;
        if (mobAnimCount <= 0) {

            mobAnimCount = MOB_ANIM_DELAY;
            if (damaged){
                DamageCounter++;
            }
            mobAnimPos = mobAnimPos + mobAnimDir;
            if (type!=2){
                if (mobAnimPos == (AnimCount - 1) || mobAnimPos == 0) {
                    mobAnimDir = -mobAnimDir;

                }
            }else if (type==2){

                    if (mobAnimPos == (AnimCount - 1) || mobAnimPos == 0) {
                        mobAnimDir = -mobAnimDir;

                    }


            }

        }

    }
    public int getDy() {
        return dy;
    }
    public Image getImage(int p) {
        if (type == 2) {
            if (health>0){
                return this.img[p];

            }else{
                return this.imgD[p];
            }
        }else{
                return this.img[p];
        }
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
        if (type==0){
            if (bounce && on_ground){
                this.on_ground = false;
                this.Velocity = -this.jump_height;
                this.y += Velocity;
            }
            if(!on_ground){
                if (this.y+this.h  < savedY) {
                    this.Velocity += 1;
                    if(bouncedir>0){
                        this.x-=dir;

                    }
                    else {
                        this.x+=dir;
                    }
                    y += this.Velocity;
                    bounce=false;
                    if(direction<0){
                        this.img = new Image[]{A1.getImage(),Hit.getImage()};

                    }else{
                        this.img = new Image[]{A1L.getImage(),HitL.getImage()};
                    }
                } else {

                    on_ground = true;
                    this.img = new Image[]{A1.getImage(),A2.getImage()};
                }
            }
            else{
                if (x>stop){
                    this.x+=dir;
                    this.img = new Image[]{A1.getImage(),A2.getImage()};
                }
                else{
                    if(this.direction<0){
                        this.img = new Image[]{A1.getImage(),A1.getImage()};
                    }else{
                        this.img = new Image[]{A1L.getImage(),A1L.getImage()};
                    }

                }

            }
        } else if (type==2){
            if (x>stop){
                this.x-=10;
            }if (y < 150 || y>190){
                diry=-diry;
            }
            y+=diry;
            if (this.BossMeteor!=null){
                if (this.getX()<=this.BossMeteor.getX()+this.BossMeteor.getW() && this.BossMeteor.bounced){
                    this.Damaged();
                }
            }
            if (damaged){
                this.img = new Image[]{BOSS1.getImage(),BOSSHIT.getImage()};
            }
            if (DamageCounter==10){
                this.img = new Image[]{BOSS1.getImage(),BOSS2.getImage()};
                damaged=false;
                DamageCounter=0;
            }

        }
        else{
            this.x+=dir;

        }






    }
    public int getType(){
        return this.type;
    }
    public void shoot(int x, int y, int playerX,int playerY){
        if (type==0){
            if (bullets.isEmpty()){
                bullets.add(new Bullet(x,y,playerX,playerY,this.type));
            }

        }if(type ==2 ){
            if (this.health>0){
                if (!damaged){
                    BossMeteor=new Bullet(x,y,playerX,playerY,this.type);

                }
            }


        }


    }

    public int getMobAnimPos(){
        return this.mobAnimPos;
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

    public void Bounce(int bouncedir){
          this.bounce=true;
          this.bouncedir=bouncedir;

    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void  DrawMob(Graphics2D g2){
        if (type==0){
            switch (mobAnimPos){
                case 0:
                    g2.drawImage(this.getImage(0),this.x,this.y,null);
                    break;
                case 1:
                    g2.drawImage(this.getImage(1),this.x,this.y,null);
                    break;
            }
        }else if (type==1){
            switch (mobAnimPos){
                case 0:
                    g2.drawImage(this.getImage(0),this.x,this.y,null);
                    break;
                case 1:
                    g2.drawImage(this.getImage(1),this.x,this.y,null);
                    break;
                case 2:
                    g2.drawImage(this.getImage(2),this.x,this.y,null);
                    break;
                case 3:
                    g2.drawImage(this.getImage(3),this.x,this.y,null);
                    break;
                case 4:
                    g2.drawImage(this.getImage(4),this.x,this.y,null);
                    break;
            }
        }else if (type==2){
            if (this.getHealth()>=0){
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




    }

    public void Damaged() {
        if (this.getType()==2){
            this.health-=10;

        }else{
            this.health--;
        }
        stop=this.x;
        dir=-1;
        damaged=true;
        if (this.health<0){
            mobAnimPos=0;
            MOB_ANIM_DELAY=50;
            this.alive=false;
        }

    }

    public int getHealth() {
        return health;
    }

    public boolean isOnGround() {
        return on_ground;
    }

    public void drawBullets(Graphics2D g2) {
        if(type==0){
            for (Bullet b:bullets){

                if(b.visible){
                    g2.drawImage(b.getImage(),b.getX(),b.getY(),null);

                }
            }
        } else if (type==2) {
            if(BossMeteor.visible){
                switch (mobAnimPos){

                case 0:
                    g2.drawImage(BossMeteor.getImages(0,getHealth()),BossMeteor.x,BossMeteor.y,null);
                    break;
                case 1:
                    g2.drawImage(BossMeteor.getImages(1,getHealth()),BossMeteor.x,BossMeteor.y,null);
                    break;

            }
            }

        }

    }

    public void setMobAnimPos(int s) {
        this.mobAnimPos=s;
        this.mobAnimDir=1;
    }
    public void ChangeDir(int px){
        if (px>this.x){
            this.direction=-speed;
        }
        else{
            this.direction=+speed;
        }
    }

    public boolean isStopped() {
        if (this.x<=stop){
            return true;
        }
        return false;
    }

    public void DrawHealth(Graphics2D g2,Font font,int W) {
        if(type==2){

            g2.setColor(Color.RED);
            g2.fillRect(W/2,35,this.health*20,14);

            g2.setFont(font.deriveFont(Font.BOLD,20));
            g2.setColor(Color.white);
            g2.drawString(Integer.toString(this.health*5)+"%",(W/2)-50,46);
        }
    }
}
