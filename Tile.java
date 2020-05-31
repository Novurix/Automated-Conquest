import java.awt.Color;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tile extends JPanel {

    Vec2D position;
    String countryName;

    int SCALE;
    int WAIT_TIME, WAIT_TIMER;
    LWindow WINDOW;

    Tile[] neighbours = new Tile[4];

    JPanel BorderUp = new JPanel(), 
           BorderDown = new JPanel(), 
           BorderLeft = new JPanel(), 
           BorderRight = new JPanel();

    public Tile(int SCALE, int X, int Y, LWindow WINDOW, int WAITTIME) {

        WAIT_TIME = WAITTIME;
        this.SCALE = SCALE;
        this.WINDOW = WINDOW;

        position = new Vec2D(X, Y);

        setBounds(X*SCALE, Y*SCALE, SCALE, SCALE);
        setLayout(null);
        setBackground(new Color(50, 138, 23));

        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(SCALE/2-20/2,SCALE/2-20/2, 20,20);

        String path = generateAssets();
        if (path != null) {
            imageLabel.setIcon(new ImageIcon(path));
        }
        add(imageLabel);

        setBorders();
    }

    String generateAssets() {
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(WIDTH/2-20/2,HEIGHT/2-20/2, 20,20);

        Random randomV = new Random();
        int percentage = randomV.nextInt(100);

        if (percentage+1 <= 5) {
            return "Assets/Village.png";
        }
        return null;
    }

    void setBorders() {
        BorderUp.setBounds(0, 0, SCALE, 1);
        BorderDown.setBounds(0, SCALE-1, SCALE, 1);
        BorderLeft.setBounds(0, 0, 1, SCALE);
        BorderRight.setBounds(SCALE-1, 0, 1, SCALE);

        add(BorderUp);
        add(BorderDown);
        add(BorderLeft);
        add(BorderRight);
    }

    void update() {
        Tile[] tiles = WINDOW.tiles;

        // GETTING NEIBOURS

        for (int tileB = 0; tileB < tiles.length; tileB++) {
            if (tiles[tileB] != null) {

                Vec2D otherPos = tiles[tileB].position;

                int DistanceX = otherPos.WorldX - position.WorldX;
                int DistanceY = otherPos.WorldY - position.WorldY;

                if (DistanceX == -1 && DistanceY == 0) {
                    neighbours[0] = tiles[tileB];
                }else if (DistanceX == 1 && DistanceY == 0) {
                    neighbours[1] = tiles[tileB];
                }

                if (DistanceY == -1 && DistanceX == 0) {
                    neighbours[2] = tiles[tileB];
                }else if (DistanceY == 1 && DistanceX == 0) {
                    neighbours[3] = tiles[tileB];
                }
            }
        }

        // SETTING UP BORDERS
        for (int tileB = 0; tileB < tiles.length; tileB++) {
            if (tiles[tileB] != null) {

                Vec2D otherPos = tiles[tileB].position;

                int DistanceX = otherPos.WorldX - position.WorldX;
                int DistanceY = otherPos.WorldY - position.WorldY;

                if (DistanceX == -1 && DistanceY == 0 && countryName == tiles[tileB].countryName) {
                    remove(BorderLeft);
                }else if (DistanceX == 1 && DistanceY == 0 && countryName == tiles[tileB].countryName) {
                    remove(BorderRight);
                }

                if (DistanceY == -1 && DistanceX == 0 && countryName == tiles[tileB].countryName) {
                    remove(BorderUp);
                }else if (DistanceY == 1 && DistanceX == 0 && countryName == tiles[tileB].countryName) {
                    remove(BorderDown);
                }
            } 
        }

        // ACTUAL WAR AI
        WAIT_TIMER++;
        if (WAIT_TIMER >= WAIT_TIME) {
            boolean canCapture = true;
            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i] != null) {
                    if (neighbours[i].countryName != countryName) {
                        neighbours[i].changeTeams(countryName, WAIT_TIME);
                        break;
                    }
                }
            }

            WAIT_TIMER = 0;
        }
    }

    void changeTeams(String countryName, int wait) {
        WAIT_TIMER = 0;
        this.countryName = countryName;
        System.out.println("Captured by " + countryName);
        WAIT_TIME = wait;

        add(BorderUp);
        add(BorderDown);
        add(BorderLeft);
        add(BorderRight);
    }
}