import NPCS.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class Game extends JPanel implements ActionListener {
    public final int MAP_ANIM_DELAY = 30;
    public final int FMAP_ANIM_DELAY = 20;
    public final int MAP_ANIM_COUNT = 2;
    public final int FIGHTMAP_ANIM_COUNT = 2;
    public final int N_BLOCKS_X = 66;
    public final int N_BLOCKS_Y = 52;
    public final int BLOCK_SIZE = 12;
    private final int DELAY = 30;
    private final int DIALOGUE_ANIM_DELAY = 350;
    private final int DUDE_ANIM_DELAY = DELAY / 2;
    private int dudeAnimCount = DUDE_ANIM_DELAY;
    private final int DUDE_ANIM_COUNT = 3;
    private final boolean Pressed = false;
    private final int dialogueAnimDir = 1;
    private final double zoomFactor = 1.1;
    public int[] XY;
    public Effect e;
    public int actAnimCount;
    public Board board;
    public Dude dude;
    public Wave wave;
    public HUD hud;
    public Inventory inventory;
    public boolean GameOrFight = true;
    public int mapAnimCount = MAP_ANIM_DELAY;
    public int mapAnimDir = 1;
    public int mapAnimPos = 0;
    public short[] ScreenData;
    public short[] Data;
    public Rectangle solidarea;
    public Rectangle shadow;
    public Font pixelfont;
    private int Plateform = 470;
    private int MouseX;
    private int MouseY;
    private int deltaX, deltaY;
    private int offsetX = 0;
    private int offsetY = 0;
    private int savedX, savedY = -1;
    private boolean dragging = false;
    private int selected_item_i, selected_item_j = -1;
    private InteractionPanel faces;
    private Timer timer;
    private Animations anim;
    private Princess princess;
    private Buildo buildo;
    private Portalo portalo;
    private King king;
    private Sello sello;
    private Graphics2D G;
    private int DIALOGUE_ANIM_COUNT = 2;
    private int dialogueAnimCount = DIALOGUE_ANIM_DELAY;
    private int dialogueAnimPos = 0;
    private int dudeAnimDir = 1;
    private int dudeAnimPos = 0;
    private int X, Y;
    private AffineTransform at;
    private int actAnimPos;
    private int actAnimDir = 1;
    private int counterTitle = 0;
    private Chest chest;
    private int selected_item_column = -1;
    private Chest chest1;

    public Game() {

        initGame();
        initMap();

    }

    public void initMap() {
        ScreenData = new short[N_BLOCKS_X * N_BLOCKS_Y];

        Data = board.getleveldata(dude.getIntoMap());

        int i;
		/*for (i = 0; i < N_BLOCKS_X * N_BLOCKS_Y; i++) {

			ScreenData[i] = Data[i];
		}*/
        ScreenData = Data;

    }

    public void initGame() {
        try {
            pixelfont = Font.createFont(Font.TRUETYPE_FONT, new File("src/PixellettersFull.ttf"));


        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        this.addKeyListener(new TAdapter());
        this.setBackground(Color.black);
        this.setFocusable(true);

        this.solidarea = new Rectangle();


        inventory = new Inventory();
        hud = new HUD();
        hud.setX(90);
        hud.setY(680);

        sello = new Sello();
        portalo = new Portalo();
        buildo = new Buildo();

        chest = new Chest(100, 330, 10);
        chest1 = new Chest(100, 290, 1);
        king = new King();
        dude = new Dude();

        princess = new Princess();
        board = new Board();
        if ( GameOrFight ) {
            board.setSelector(dude.getIntoMap());
        } else {
            board.setFightSelector(dude.getIntoFight());
        }

        dude.setX(252);
        dude.setY(252);
        faces = new InteractionPanel();

        timer = new Timer(DELAY, this);
        timer.start();
        e = new Effect(dude.x + 20, dude.y + 20);
        MouseEvents();
    }

    private void doAnim(int AnimCount) {

        dudeAnimCount--;
        if ( dudeAnimCount <= 0 ) {

            dudeAnimCount = DUDE_ANIM_DELAY;

            dudeAnimPos = dudeAnimPos + dudeAnimDir;

            if ( dude.isDamaged() ) {
                dude.DamageEffect += 1;
            }
            if ( dude.DamageEffect == 3 ) {
                dude.setDamaged(false);
                dude.setWalkingSprites();

                dude.DamageEffect = 0;
            }
            if ( dudeAnimPos == (AnimCount - 1) || dudeAnimPos == 0 ) {

                dudeAnimDir = -dudeAnimDir;

            }
        }
    }

    private void ActAnim() {

        actAnimCount--;
        if ( actAnimCount <= 0 ) {
            actAnimCount = 10;
            actAnimPos = actAnimPos + actAnimDir;
            if ( actAnimPos == 2 ) {
                actAnimPos = 0;

                dude.setAttack(false);

            }
        }
        if ( !dude.isAttacking() ) {
            e.reset();

        }

    }


    private void dodialogAnim() {
        if ( dude.getInteract() ) {
            dialogueAnimCount--;
            if ( dialogueAnimCount <= 0 ) {
                dialogueAnimCount = DIALOGUE_ANIM_DELAY;
                dialogueAnimPos = dialogueAnimPos + dialogueAnimDir;
                if ( dialogueAnimPos == (DIALOGUE_ANIM_COUNT) ) {
                    dialogueAnimPos = 0;
                    dude.setPushTalk(false);
                    dude.setInteract(false);
                    dude.setTalkmessage(false);
                }
            }

        }

    }

    private void MapAnim() {
        if ( GameOrFight ) {
            mapAnimCount--;

            if ( mapAnimCount <= 0 ) {

                mapAnimCount = MAP_ANIM_DELAY;

                mapAnimPos = mapAnimPos + mapAnimDir;

                if ( mapAnimPos == (MAP_ANIM_COUNT - 1) || mapAnimPos == 0 ) {
                    mapAnimDir = -mapAnimDir;
                }
            }
        } else {
            mapAnimCount--;
            if ( mapAnimCount <= 0 ) {

                mapAnimCount = FMAP_ANIM_DELAY;
                counterTitle++;

                mapAnimPos = mapAnimPos + mapAnimDir;

                if ( mapAnimPos == (FIGHTMAP_ANIM_COUNT - 1) || mapAnimPos == 0 ) {
                    mapAnimDir = -mapAnimDir;

                }

            }


        }


    }

    @Override
    public void paintComponent(Graphics g) {
        boolean zoomer = true;

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if ( zoomer ) {
            at = new AffineTransform();
            at.scale(zoomFactor, zoomFactor);
            g2.transform(at);
        }
        dude.setWalkingSprites();


        // board
        if ( GameOrFight ) {
            DrawBoard(g2);
            MapAnim();
            g2.drawImage(hud.getChatBoxImage(), board.getX()+board.getW() +20, board.getY()+350,this);

            if ( board.getSelector() == 9 ) {
                DrawNPC(g2);
                princess.setX(310);
                princess.setY(340);
                DrawInteraction(g2);
                DIALOGUE_ANIM_COUNT = 2;


            }
            if ( board.getSelector() == 2 ) {
                DrawNPC(g2);
                DrawInteraction(g2);

                sello.setX(220);
                sello.setY(230);
                DIALOGUE_ANIM_COUNT = 1;
            }

            if ( board.getSelector() == 1 ) {

                chest1.setX(100);
                chest1.setY(290);


                g2.drawImage(chest1.getImage(), chest1.getX(), chest1.getY(), this);

                if ( chest1.isOpen() ) {
                    if ( chest1.item != null ) {
                        g2.drawImage(chest1.item.getImage(1), chest1.item.getX(), chest1.item.getY(), this);

                    }
                }
                DrawNPC(g2);
                portalo.setX(400);
                portalo.setY(200);
                DrawInteraction(g2);
                DIALOGUE_ANIM_COUNT = 2;


            }
            if ( board.getSelector() == 10 ) {


                king.setX(550);
                king.setY(250);
                DrawNPC(g2);
                chest.setX(100);
                chest.setY(330);
                g2.drawImage(chest.getImage(), chest.getX(), chest.getY(), this);

                DrawInteraction(g2);

                DIALOGUE_ANIM_COUNT = 2;


            }

            if ( board.getSelector() == 5 ) {
                DrawNPC(g2);

                DrawInteraction(g2);
            }
            if ( board.getSelector() == 4 ) {
                DrawNPC(g2);
                buildo.setX(680);
                buildo.setY(200);
                DrawInteraction(g2);
            }

            DrawPlayer(g2);
            DrawHUD(g2);

            if ( dude.isClimbing() ) {

                doAnim(4);

            } else if ( dude.isWalking() ) {

                doAnim(2);
            }

            if ( board.getSelector() == 2 ) {
                DrawArea(g2);
            }
            DrawInteractions(g2);
            DrawFunctional(g2);
            MapAnim();


        } else {
            if ( dude.getFight() ) {
                reset();

                dude.setX(252);
                dude.setY(this.Plateform - dude.getHeight());
                dude.setFight(false);

            }

            DrawBoard(g2);
            MapAnim();

            wave.drawMobs(g2, pixelfont, this.getWidth());
            wave.drawBullets(g2);

            if ( dude.isWalking() ) {
                doAnim(2);

            }
            if ( dude.isDamaged() ) {
                doAnim(2);
            }
            DrawPlayer(g2);

            if ( dude.isAttacking() ) {
                ActAnim();
                e.setX(dude.x + 30);
                e.setY(dude.y + 8);
                e.UpdateEffect();
                e.PlayEffect(g2, dude.getSavedX(), dude.getSelectedItem());
            } else {
                e.reset();
            }


            DrawHUD(g2);
            DrawTitle(g2);
            repaint();

            /*if(board.getFightSelector() == 1) {DrawEnemies(g2); }*/
        }

        Toolkit.getDefaultToolkit().sync();
    }


    private void DrawInventoryItems(Graphics2D g2d) {

        if ( savedX != -1 && savedY != -1 ) {
            g2d.fillRect(savedX + 6, savedY + 6, 69, 69);
        }

        for ( int i = 0; i < 5; i++ ) {
            for ( int j = 0; j < 7; j++ ) {

                g2d.drawImage(inventory.items.getItemImage(i, j), inventory.items.getItemX(i, j), inventory.items.getItemY(i, j), this);

            }
        }


    }

    private void DrawTitle(Graphics2D g2d) {
        if ( !wave.Ends ) {
            if ( counterTitle < 10 ) {
                g2d.setFont(pixelfont.deriveFont(Font.BOLD, 70));
                g2d.setColor(new Color(255, 255, 255));
                g2d.drawString("LEVEL" + wave.getLevel(), this.getWidth() / 2 - 100, this.getHeight() / 5);

            }
        } else {
            if ( counterTitle > 40 ) {
                counterTitle = 0;
            }
            if ( counterTitle < 30 && wave.Boss == null ) {
                g2d.setFont(pixelfont.deriveFont(Font.BOLD, 70));
                switch (mapAnimPos) {
                    case 0:

                        g2d.setColor(new Color(253, 251, 251));
                        g2d.drawString("MOVE BACK !!", this.getWidth() / 2 - 220, this.getHeight() / 3);
                        break;
                    case 1:

                        g2d.setColor(new Color(209, 12, 12));
                        g2d.drawString("MOVE BACK !!", this.getWidth() / 2 - 220, this.getHeight() / 3);
                        break;
                }


            } else if ( counterTitle > 30 && wave.Boss == null ) {
                wave.SpawnBoss(board.getX() + board.getW());
                wave.Boss.setMobAnimPos(0);
            }

        }

    }

    ;

    private void DrawHUD(Graphics2D g2d) {

        // HUD placement
        g2d.setColor(new Color(132, 117, 119));

        g2d.drawImage(dude.getHealthImage(), 30, hud.getY() + 5, this);


        g2d.drawImage(hud.getItemsImage(), hud.getX() + 110, hud.getY(), this);


        g2d.drawImage(hud.getCoinImage(0), hud.getX() + 25, hud.getY() + 50, this);

        g2d.drawString(hud.getCoins(), hud.getX() + 46, hud.getY() + 69);

        // Bag and Inventory
        if ( !hud.GetInventoryOpen() ) {
            g2d.drawImage(hud.getBagImage(0), hud.getX() + 625, hud.getY() + 6, this);
        } else if ( hud.GetInventoryOpen() ) {
            g2d.drawImage(hud.getBagImage(1), hud.getX() + 625, hud.getY() + 6, this);
            g2d.drawImage(inventory.getInventoryImage(), board.getX() + 80, board.getY() + 60, this);
            // System.out.println("items:" + inventory.items.showItemsExisted());
            DrawInventoryItems(g2d);
        }
        g2d.fillRect(MouseX, MouseY, 10, 10);
        if ( dude.getSelectedItem() != null ) {
            g2d.drawImage(dude.getSelectedItem().getImage(0), hud.getX() + hud.getW() + 120, 30, this);
            g2d.setStroke(new BasicStroke(6f));
            g2d.setColor(Color.decode("#8F5F00"));
            g2d.drawRect((board.getX() + 200 + dude.getColumnSelected() * 71) + 8, this.hud.getY() + 7, 67, 67);
        }
        for ( int j = 0; j < 7; j++ ) {
            g2d.drawImage(inventory.items.getItemImage(4, j), board.getX() + 200 + j * 71, hud.getY(), this);
            repaint();

        }
    }

    private void DrawInteractions(Graphics2D g2d) {

        if ( board.getSelector() == 1 ) {
            if ( dude.getInteract() && dude.getX() > 300 ) {
                switch (mapAnimPos) {
                    case 0:
                        g2d.drawImage(faces.getImage(1), faces.getX(), faces.getY(), this);
                        break;
                    case 1:
                        g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);
                        break;

                    default:
                        g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);

                }


            } else {
                g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);

            }


        }
        if ( board.getSelector() == 2 ) {
            if ( dude.getInteract() ) {
                switch (mapAnimPos) {
                    case 0:
                        g2d.drawImage(faces.getImage(1), faces.getX(), faces.getY(), this);
                        break;
                    case 1:
                        g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);
                        break;

                    default:
                        g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);

                }


            } else {
                g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);
            }
        }
        if ( board.getSelector() == 9 ) {
            if ( dude.getInteract() ) {
                switch (mapAnimPos) {
                    case 0:
                        g2d.drawImage(faces.getImage(1), faces.getX(), faces.getY(), this);
                        break;
                    case 1:
                        g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);
                        break;

                    default:
                        g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);

                }


            } else {
                g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);
            }
        }
        if ( board.getSelector() == 10 ) {
            if ( dude.getInteract() && dude.getX() > 400 ) {
                switch (mapAnimPos) {
                    case 0:
                        g2d.drawImage(faces.getImage(1), faces.getX(), faces.getY(), this);
                        break;
                    case 1:
                        g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);
                        break;

                    default:
                        g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);

                }


            } else {
                g2d.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);
            }
        }
        this.repaint();
    }

    private void DrawInteraction(Graphics2D g2d) {

        // HUD placement
        g2d.setFont(pixelfont.deriveFont(Font.BOLD, 30));
        g2d.setColor(Color.white);

        int x = faces.getX() + - 50;
        int y = faces.getY() + faces.getH() + 150;

        if ( dude.getInteract() && dude.getIntoMap() == 1 && dude.getX() > 300 ) {

            dodialogAnim();
            switch (dialogueAnimPos) {
                case 0:
                    for ( String line : portalo.loadDialogue(0).split("\n") ) {
                        g2d.drawString(line, x, y);
                        y = y + 30;
                    }

                    break;
                case 1:
                    for ( String line : portalo.loadDialogue(1).split("\n") ) {
                        g2d.drawString(line, x, y);
                        y = y + 30;
                    }

            }
            if ( dialogueAnimPos == dialogueAnimCount ) {
                dude.setInteract(false);
                for ( String line : portalo.loadDialogue(5).split("\n") ) {
                    g2d.drawString(line, x, y);
                    y = y + 30;
                }
            }
        }
        if ( dude.getInteract() && dude.getIntoMap() == 2 ) {
            dodialogAnim();
            switch (dialogueAnimPos) {
                case 0:
                    for ( String line : sello.loadDialogue(0).split("\n") ) {
                        g2d.drawString(line, x, y);
                        y = y + 30;
                    }

                    break;
                case 1:
                    for ( String line : sello.loadDialogue(1).split("\n") ) {
                        g2d.drawString(line, x, y);
                        y = y + 30;
                    }

            }
            if ( dialogueAnimPos == dialogueAnimCount ) {
                dude.setInteract(false);
                for ( String line : sello.loadDialogue(5).split("\n") ) {
                    g2d.drawString(line, x, y);
                    y = y + 30;
                }
            }
        }
        if ( dude.getInteract() && dude.getIntoMap() == 9 ) {
            dodialogAnim();
            switch (dialogueAnimPos) {
                case 0:
                    for ( String line : princess.loadDialogue(0).split("\n") ) {
                        g2d.drawString(line, x, y);
                        y = y + 30;
                    }

                    break;
                case 1:
                    for ( String line : princess.loadDialogue(1).split("\n") ) {
                        g2d.drawString(line, x, y);
                        y = y + 30;
                    }

            }
        }
        if ( dude.getInteract() && dude.getIntoMap() == 10 && dude.getX() > 450 ) {
            dodialogAnim();
            switch (dialogueAnimPos) {
                case 0:
                    for ( String line : king.loadDialogue(0).split("\n") ) {
                        g2d.drawString(line, x, y);
                        y = y + 30;
                    }

                    break;
                case 1:
                    for ( String line : king.loadDialogue(1).split("\n") ) {
                        g2d.drawString(line, x, y);
                        y = y + 30;
                    }

            }
        }

    }

    private void DrawFunctional(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // HUD placement
        g2d.setFont(pixelfont.deriveFont(Font.BOLD, 20));
        g2d.setColor(new Color(255, 222, 0));

        int x = 400;
        int y = 500;
        if ( dude.isPushTalk() ) {
            g2d.drawString("( Press F )", x, y);
        } else {


        }


    }

    private void DrawArea(Graphics2D g2d) {
        Color c = new Color(69, 66, 68);
        g2d.setColor(c);
        if ( solidarea != null ) {


            g2d.fillRect(612, 200, dude.getWidth() / 2 - 4, dude.getHeight() * 2 - 5);


        }

    }

    private void DrawBoard(Graphics2D g2d) {
        if ( !GameOrFight ) {
            switch (mapAnimPos) {
                case 0:
                    g2d.drawImage(board.getFightImage(0), board.getX() + 30, board.getY(), this);
                    break;
                case 1:
                    g2d.drawImage(board.getFightImage(1), board.getX() + 30, board.getY(), this);
                    break;


                default:
                    g2d.drawImage(board.getFightImage(0), board.getX() + 30, board.getY(), this);

            }
        } else {
            if ( board.getSelector() != 8 ) {
                switch (mapAnimPos) {
                    case 0:
                        g2d.drawImage(board.getImage(0), board.getX(), board.getY(), this);
                        break;
                    case 1:
                        g2d.drawImage(board.getImage(1), board.getX(), board.getY(), this);
                        break;

                    default:
                        g2d.drawImage(board.getImage(0), board.getX(), board.getY(), this);

                }
            } else {
                board.setChanged(dude.getTrig());
                g2d.drawImage(board.getImage(0), board.getX(), board.getY(), this);
            }
        }
    }

    private void DrawPlayer(Graphics2D playerG) {
        // playerArea.setColor(Color.blue);
        // playerArea.fillRect(X, Y, dude.solidarea.width, dude.solidarea.height);
        repaint();
        if ( dude.isWalking() ) {
            switch (dudeAnimPos) {
                case 0:
                    playerG.drawImage(dude.getImage(0), dude.getX(), dude.getY(), this);
                    break;
                case 1:
                    playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);
                    break;

                default:
                    playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);
            }
        } else if ( dude.isClimbing() ) {

            switch (dudeAnimPos) {
                case 0:
                    playerG.drawImage(dude.getImage(0), dude.getX(), dude.getY(), this);
                    break;
                case 1:
                    playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);
                    break;
                case 2:
                    playerG.drawImage(dude.getImage(2), dude.getX(), dude.getY(), this);
                    break;
                case 3:
                    playerG.drawImage(dude.getImage(3), dude.getX(), dude.getY(), this);
                    break;

                default:
                    playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);
            }
        } else if ( dude.isAttacking() ) {
            switch (actAnimPos) {
                case 0:
                    playerG.drawImage(dude.getImage(0), dude.getX(), dude.getY(), this);

                    break;
                case 1:
                    playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);

                    break;

                default:
                    playerG.drawImage(dude.getImage(0), dude.getX(), dude.getY(), this);
            }
        } else if ( dude.isDamaged() ) {
            switch (mapAnimPos) {
                case 0:
                    playerG.drawImage(dude.getImage(0), dude.getX(), dude.getY(), this);

                    break;
                case 1:
                    playerG.drawImage(dude.getImage(1), dude.getX(), dude.getY(), this);

                    break;

                default:
                    playerG.drawImage(dude.getImage(0), dude.getX(), dude.getY(), this);
            }

        }

    }

    private void DrawNPC(Graphics2D NPCG) {
        // playerArea.setColor(Color.blue);
        // playerArea.fillRect(X, Y, dude.solidarea.width, dude.solidarea.height);
        if ( board.getSelector() == 1 ) {
            switch (mapAnimPos) {
                case 0:
                    NPCG.drawImage(portalo.getImage(0, dude.getX()), portalo.getX(), portalo.getY(), this);
                    if ( dude.getX() > 400 ) {
                        NPCG.drawImage(faces.getImage(0), faces.getX(), faces.getY(), this);

                    }

                    break;
                case 1:
                    NPCG.drawImage(portalo.getImage(1, dude.getX()), portalo.getX(), portalo.getY(), this);

                    if ( dude.getX() > 400 ) {
                        NPCG.drawImage(faces.getImage(1), faces.getX(), faces.getY(), this);

                    }

                    break;

                default:
                    NPCG.drawImage(portalo.getImage(0, dude.getX()), portalo.getX(), portalo.getY(), this);
            }
        }
        if ( board.getSelector() == 2 ) {
            NPCG.drawImage(sello.getImage(), sello.getX(), sello.getY(), this);
            NPCG.drawImage(sello.getMarketImage(), sello.getX() + 75, sello.getY(), this);
        }
        if ( board.getSelector() == 4 ) {
            NPCG.drawImage(buildo.getImage(), buildo.getX(), buildo.getY(), this);
        }
        if ( board.getSelector() == 9 ) {
            NPCG.drawImage(princess.getImage(), princess.getX(), princess.getY(), this);
        }
        if ( board.getSelector() == 10 ) {
            NPCG.drawImage(king.getImage(), king.getX(), king.getY(), this);
        }

    }


    private void initFight() {

        wave = new Wave(2, 100, 1, this.Plateform);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( dude.isQuit() ) {
            System.exit(0);
        }

        dude.setWalkingSprites();
        GameOrFight = dude.isGameOrFight();
        dude.move(ScreenData, N_BLOCKS_Y, N_BLOCKS_X, BLOCK_SIZE, this.getHeight(), this.getWidth());

        if ( !GameOrFight ) {

            board.setFightSelector(dude.getIntoFight());

            if ( wave != null && wave.Mobs != null ) {
                if ( !wave.isEnded() ) {
                    wave.populate(board.getW() + board.getX());
                    wave.Move(dude.getX(), this.getWidth());

                } else {

                    wave.Move(dude.getX(), this.getWidth());

                }
                if ( !wave.isEnded() ) {
                    for ( Mob m : wave.Mobs ) {
                        if ( m != null ) {

                            if ( m.bullets != null ) {
                                if ( m.bullets.isEmpty() ) {
                                    wave.Shoot(m.getX(), m.getY(), dude.getMemoryX(), dude.getMemoryY() + 50, m);

                                }

                            }
                        }

                    }
                } else {
                    if ( wave.Boss != null ) {
                        if ( wave.Boss.BossMeteor == null ) {
                            if ( dude.isDamaged() ) {
                                if ( dude.DamageEffect > 2 ) {
                                    wave.Shoot(wave.Boss.getX() - 200, wave.Boss.getY() + 400, dude.getMemoryX(), dude.getMemoryY() + 50, wave.Boss);

                                }
                            } else {
                                wave.Shoot(wave.Boss.getX() - 200, wave.Boss.getY() + 400, dude.getMemoryX(), dude.getMemoryY() + 50, wave.Boss);

                            }
                        }
                    }
                }


                dude.checkHit(wave.Mobs, wave);

            }


        } else {

            board.setSelector(dude.getIntoMap());
            initMap();

            if ( board.getSelector() == 2 ) {
                faces.setSelector(2);
            }
            if ( dude.getTrig() && dude.getIntoMap() == 1 && dude.getX() < 250 ) {
                chest1.setOpen(true);
                dude.setTrig(false);
            }
            if ( board.getSelector() == 1 ) {
                faces.setSelector(1);
                if ( chest1.isOpen() ) {
                    if ( chest1.item != null ) {
                        chest1.BounceItem();
                    }
                }
            }
            if ( board.getSelector() == 9 ) {
                faces.setSelector(3);
            }
            if ( board.getSelector() == 10 ) {
                faces.setSelector(4);
            }
            if ( dude.getInteract() && dude.getIntoMap() == 10 && dude.getX() < 300 && dude.getSelectedItem() != null ) {
                if ( dude.getSelectedItem().getId() == 60 ) {
                    chest.setOpen(true);
                    dude.setTrig(false);

                    chest.BounceItem();
                    inventory.items.remove_item(4, selected_item_column);
                    selected_item_column = -1;


                }


            }


        }

    }

    private void MouseEvents() {
        this.addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {
                Point pos = e.getPoint();
                int x = (int) (pos.x / 1.1);

                int y = (int) (pos.y / 1.1);
                //hud.getX()+ 625, hud.getY()+6
                if ( y <= hud.getY() && !hud.GetInventoryOpen() ) {
                    if ( chest1.getItem() != null ) {
                        int ItemX = chest1.getItem().getX();
                        int ItemY = chest1.getItem().getY();
                        int ItemW = chest1.getItem().getW();
                        if ( x >= ItemX && x <= ItemX + ItemW && y >= ItemY && y <= ItemY + ItemW && chest1.isOpen() ) {
                            if ( chest1.getItem() != null ) {
                                inventory.equipItem(chest1.getItem());
                                chest1.removeItem();
                            }
                        }
                    } else {

                        dude.use_Selected_Item();

                    }


                }
                if ( !hud.GetInventoryOpen() && y >= hud.getY() ) {
                    for ( int i = 0; i < 7; i++ ) {
                        if ( x > board.getX() + 200 + i * 71 && x < board.getX() + 200 + i * 70 + 80 && y > hud.getY() && y < hud.getY() + hud.getH() ) {
                            System.out.println("item");
                            dude.setColumnSelected(i);
                            dude.setSelectedItem(inventory.getItems().getItem(4, i));
                            selected_item_column = i;
                        }
                    }
                }

                if ( hud.getX() + 625 <= x && x <= hud.getX() + 690 && y >= hud.getY() + 6 && y <= hud.getY() + 76 && !hud.GetInventoryOpen() ) {
                    hud.SetInventoryOpen(true);

                } else if ( hud.getX() + 625 <= x && x <= hud.getX() + 690 && y >= hud.getY() + 6 && y <= hud.getY() + 76 && hud.GetInventoryOpen() ) {
                    hud.SetInventoryOpen(false);
                } else if ( hud.GetInventoryOpen() ) {
                    for ( int i = 0; i < 5; i++ ) { // Assuming `getRows()` gives the number of rows
                        for ( int j = 0; j < 7; j++ ) { // Assuming `getColumns()` gives the number of columns
                            int itemX = inventory.items.getItemX(i, j);
                            int itemY = inventory.items.getItemY(i, j);

                            // Check if the mouse is within the item's bounds with a 60 pixel buffer
                            if ( itemX / 1.1 < x && itemX + 80 / 1.1 > x && itemY / 1.1 < y && itemY + 80 / 1.1 > y && !dragging ) {
                                offsetX = x - itemX;
                                offsetY = y - itemY;
                                savedX = itemX;
                                savedY = itemY;
                                dragging = true;
                                selected_item_i = i;
                                selected_item_j = j;
                                break;
                            }
                        }
                        if ( dragging ) {
                            break;
                        }
                    }

                } else {
                    if ( !GameOrFight ) {
                        if ( dude.isAttacking() ) {

                            wave.checkHit(dude.getSavedX(), dude.getX(), dude.getWidth());

                        }

                    }
                }


            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Point pos = e.getPoint();
                int x = (int) (pos.x / 1.1);

                int y = (int) (pos.y / 1.1);

            }

            @Override
            public void mouseReleased(MouseEvent e) {


                dragging = false;
                if ( hud.GetInventoryOpen() ) {
                    if ( XY != null ) {
                        inventory.items.showItems();
                        inventory.items.setItemXY(selected_item_i, selected_item_j, XY[0], XY[1]);
                        inventory.items.setItemXY(XY[2], XY[3], savedX, savedY);
                        inventory.items.change_item(selected_item_i, selected_item_j, XY[2], XY[3]);
                        savedX = XY[0];
                        savedY = XY[1];

                    } else {
                        if ( selected_item_i != -1 && selected_item_j != -1 ) {
                            inventory.items.setItemXY(selected_item_i, selected_item_j, savedX, savedY);

                        }

                    }
                    selected_item_i = -1;
                    selected_item_j = -1;
                    XY = null;
                    savedX = -1;
                    savedY = -1;

                }


            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if ( hud.getX() + hud.getW() - 15 >= x && x >= hud.getX() + hud.getW() - 75 && y >= hud.getY() + 11 && y <= hud.getY() + 81 ) {
                    hud.SetInventoryOpen(false);
                }

            }

        });
        this.addMouseMotionListener(new MouseMotionListener() {


            @Override
            public void mouseDragged(MouseEvent e) {

//				deltaX = e.getXOnScreen() + inventory.items.getX();
//				deltaY = e.getYOnScreen() + inventory.items.getY();
//				if (hud.GetInventoryOpen()) {
//					if (inventory.items.getItemX(0, 0) <  x && inventory.items.getItemX(0, 0) + 100  > x) {
//						if (inventory.items.getItemY(0, 0) < y && inventory.items.getItemY(0, 0) + 100 > y ) {

                if ( dragging ) {
                    final Point pos = e.getPoint();

                    int x = (int) (pos.x / 1.1);
                    int y = (int) (pos.y / 1.1);
                    inventory.items.setItemXY(selected_item_i, selected_item_j, x - offsetX, y - offsetY);
                    MouseX = (int) (inventory.items.getItemX(selected_item_i, selected_item_j));
                    MouseY = (int) (inventory.items.getItemY(selected_item_i, selected_item_j));
                    XY = inventory.items.findClosestItem(inventory.items.getItemX(selected_item_i, selected_item_j), inventory.items.getItemY(selected_item_i, selected_item_j));


                }
//
//							System.out.println("{" + deltaX + ',' + deltaY + "}");
//
//						}
//					}
//					else if (inventory.items.getItemX(0, 1) < x  && inventory.items.getItemX(0, 1) + 100 > x) {
//						if (inventory.items.getItemY(0, 1) < y  && inventory.items.getItemY(0, 1) + 100 > y) {
//
//							inventory.items.setItemXY(0, 1, deltaX, deltaY);
//
//							System.out.println("{" + deltaX + ',' + deltaY + "}");
//
//						}
//					}
//
//				}

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }

        });
    }

    public void reset() {
        initFight();

        mapAnimDir = 1;
        mapAnimPos = 0;
        dudeAnimDir = 1;
        dudeAnimPos = 0;
        actAnimPos = 0;
    }

    public class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            dude.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            dude.keyPressed(e);
        }

    }

}
