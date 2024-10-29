import java.util.HashMap;
import java.util.Map;

public class NPCS {

    public Map<Integer, String[][]> Dialog;
    public Map<Integer, Npc> Characters;
    public NPCS(){
        Dialog = new HashMap<>();
        Characters = new HashMap<>();
        Init();
    }
    private void Init(){
        Characters.put(1,new Npc(200,200,"Portalo",1));
        Characters.put(2,new Npc(200,230,"Sello",2));
        Characters.put(9,new Npc(310,340,"Princess",9));
        Characters.put(10,new Npc(550,250,"Moustachio IX",10));
        Characters.put(11,new Npc(300,300,"AL-30",11));

        Dialog.put(1,new String[][]{
                {"Hello! My name is Portalo the guardian \nof this portal,I can only allow you\nto enter only if you have \na sword. ",
                "Sorry i cant allow you to enter"},
                {"Sorry i cant allow you to enter..Oh!\nHmm! You got a sword ,\ni can allow you now",
                "Be careful!"}});
        Dialog.put(2,new String[][]{
                 {"Hello! My name is Sello, \nan ancient samurai",
                        "wanna buy some swords ?",
                        "Sorry i cant allow you to enter",
                        " "}
        });
        Dialog.put(9,new String[][]{
                {"Help us ","Take this key and go to our king"}
        });
        Dialog.put(10,new String[][]{
                {"Salutation! \nMy name is Mustachio The Ninth, \nI'm the king of this realm",
                        "In the portal you'll find aliens ", "There is a blackhole waiting for you \n you need a sword",
                        " "}
        });
        Dialog.put(11,new String[][]{
                {"BALBALBALBAL"}
        });

    }
}
