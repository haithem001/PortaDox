package NPCS;

import java.awt.*;
import java.util.Random;

public class Wave {

    public Mob[] Mobs;
    public boolean Ends=false;
    int level;
    int Plateform;
    public int npcCount;
    public float spawnInterval;
    private float[] mobSpawnTimes;
    public Mob Boss;
    int currentTime;
    private int Deads=0;
    private int Alive=0;

    public Wave(int count, float interval,int level,int Plateform){
        currentTime = 0;
        generateRandomSpawnTimes();
        npcCount = count;
        spawnInterval = interval;
        Mobs=new Mob[npcCount];
        this.level=level;
        this.Plateform=Plateform;
        mobSpawnTimes = new float[npcCount];
        Alive=count;
        generateRandomSpawnCounters();

    }
    private void generateRandomSpawnCounters() {
        Random rand = new Random();
        for (int i = 0; i < npcCount; i++) {
            // Generate a random spawn counter between 0 and spawnInterval

        }
    }

    private void generateRandomSpawnTimes() {
        Random rand = new Random();
        for (int i = 0; i < npcCount; i++) {
            // Generate a random spawn time between 0 and spawnInterval
            mobSpawnTimes[i] = rand.nextFloat() * spawnInterval;
        }
    }
    public boolean isEnded(){
        return this.Ends;
    }
    public void SpawnBoss(int W){
        Random rand = new Random();
        Boss = new Mob(2*W , 20, 1 ,W-100, 2);
    }
    public void populate(int W){

        Random rand = new Random();
        int New=rand.nextInt((int) (spawnInterval ));
        countTime();
       if (currentTime>=   50+ New) {
           currentTime=0;
           count();
           if (npcCount > 1 ) {
               int r = rand.nextInt(2);
               Mobs[Mobs.length - npcCount] = new Mob(rand.nextInt(300)+2*W, Plateform - 45, 1 ,W+rand.nextInt(100), r);
           }

       }

    }
    public void Shoot(int x, int y, int playerX,int playerY,Mob m){

        Random rand = new Random();
                if(!Ends){
                    if (m!=null){
                        if (m.getType()==0 && m.isStopped()){
                            m.shoot(x,y+70,playerX,playerY);
                        }

                    }
                }
               else{
                    if(Boss!=null){
                        if (Boss.isStopped()){
                            Boss.shoot(x,420,playerX,playerY);

                        }
                    }
                }





    }


    public int getLevel() {
        return level;
    }

    public void BulletsUpdate(Mob m, int W){
        if(!Ends){


            if (m!=null && m.getType()==0){
                if (!m.bullets.isEmpty()){
                    for (Bullet B:m.bullets){
                        if(B.visible){
                            B.move(W,1);

                        }else {
                            B.move(W,1);
                            if (B.x<0){
                                m.bullets.remove(B);
                                break;
                            }
                        }

                    }
                }
            }
        }else{
            if (m!=null){
                if (Boss.BossMeteor!=null) {
                    if (Boss.BossMeteor.getX() + Boss.BossMeteor.getW() < -100) {
                        Boss.BossMeteor = null;
                    }
                    else if (Boss.getX()<Boss.BossMeteor.getX() ){
                        Boss.Damaged();
                        Boss.BossMeteor=null;
                    }else {
                        Boss.BossMeteor.move(W, 1);
                    }

                }
            }
        }

        }


    public void drawBullets(Graphics2D g2 ){
        if (!Ends){
            for (int i =0;i<Mobs.length;i++){
                if(Mobs[i]!=null && Mobs[i].getType()==0){

                    Mobs[i].drawBullets(g2);
                }
            }
        }else{
            if(Boss!=null){
                if(Boss.BossMeteor!=null){
                    Boss.drawBullets(g2);

                }


            }

        }

    }
    public void countTime(){
        currentTime++;
    }
    public void count(){

        npcCount--;
    }
    public void spawn(){

    }
    public  void drawMobs(Graphics2D g2){
        if(!Ends){
            for (int i =0;i<Mobs.length;i++){
                if (Mobs[i]!=null){
                    Mobs[i].DrawMob(g2);
                    Mobs[i].UpdateMob();
                }


            }
        }else{
            if (Boss!=null){
                Boss.DrawMob(g2);
                Boss.DrawHealth(g2);
                Boss.UpdateMob();
            }

        }


    }
    public void Move(int px,int Width){
        if (Deads==Alive-2){
            Ends=true;

        }
        if (!Ends){
            for (int i =0;i<Mobs.length;i++){
                if(Mobs[i]!=null){
                    if(Mobs[i].getType()==0){
                        Mobs[i].ChangeDir(px);
                    }
                    Mobs[i].move(Plateform);
                    BulletsUpdate(Mobs[i],Width);



                }
            }
        }else if (Ends){
            if(Boss!=null){
                Boss.move(Plateform);
                BulletsUpdate(Boss,Width);
            }

        }

    }


    public void checkHit(int dx,int X,int W) {
        if (!Ends){
            for(int i =0;i<Mobs.length;i++){
                if(Mobs[i]!=null){
                    if ((Mobs[i].getX()<X+W*2 && Mobs[i].getX()>X && dx>0) || (Mobs[i].getX()>X-W*2 && Mobs[i].getX()+Mobs[i].getW()<X && dx<0)) {

                        Mobs[i].Bounce(dx);
                        Mobs[i].Damaged();
                    }
                    if(Mobs[i].getX()+Mobs[i].getW()<0 && Mobs[i].getType()==1){
                        Mobs[i]=null;
                        Deads++;
                    }
                    if(Mobs[i].getHealth()<=0 && Mobs[i].isOnGround() ){
                        Deads++;
                        Mobs[i]=null;
                    }
                }

            }
        }else{
            if (Boss!=null){
                if (Boss.BossMeteor!=null){
                    if ((Boss.BossMeteor.getX()<X+W*2 && Boss.BossMeteor.getX()>X && dx>0)) {
                        Boss.BossMeteor.BounceBack();
                    }
                }

            }


        }

    }
}
