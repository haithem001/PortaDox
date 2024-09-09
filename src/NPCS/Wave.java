package NPCS;

import java.awt.*;
import java.util.Random;

public class Wave {

    public Mob[] Mobs;
    int level;
    int Plateform;
    public int npcCount;
    public float spawnInterval;
    private float[] mobSpawnTimes;
    int currentTime;
    public Wave(int count, float interval,int level,int Plateform){
        currentTime = 0;
        generateRandomSpawnTimes();
        npcCount = count;
        spawnInterval = interval;
        Mobs=new Mob[npcCount];
        this.level=level;
        this.Plateform=Plateform;
        mobSpawnTimes = new float[npcCount];

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


    public void populate(int W){
        Random rand = new Random();
        countTime();
       if (currentTime>=   spawnInterval + rand.nextInt((int) (spawnInterval * 2))) {
           currentTime=0;
           count();
           if (npcCount > 1 ) {

               Mobs[Mobs.length - npcCount] = new Mob(W+500, Plateform - 45, 1);


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
        if(npcCount<Mobs.length){
            for (int i =0;i<Mobs.length;i++){
                if (Mobs[i]!=null){
                    Mobs[i].DrawMob(g2);
                    Mobs[i].UpdateMob();
                }


            }
        }


    }
    public void Move(){
        for (int i =0;i<Mobs.length;i++){
            if(Mobs[i]!=null){
                Mobs[i].move(Plateform);

            }
        }
    }


}
