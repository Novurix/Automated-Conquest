import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Launcher {

    static int TILE_SCALE = 30;

    static int TILE_ROW_X = 30;
    static int TILE_ROW_Y = 20;

    static int WIDTH;
    static int HEIGHT;

    public static void main(String[] args) {
        WIDTH = TILE_SCALE * TILE_ROW_X;
        HEIGHT = TILE_SCALE * TILE_ROW_Y;

        new LWindow(WIDTH, HEIGHT, TILE_SCALE, TILE_ROW_X, TILE_ROW_Y);
    }
}

class LWindow extends JFrame implements ActionListener {

    Tile[] tiles;
    Timer updater = new Timer(1,this);

    int ROW_X, ROW_Y, TILE_SCALE;
    int index;

    public LWindow(int WIDTH, int HEIGHT, int SCALE, int ROW_X, int ROW_Y) {

        this.ROW_X = ROW_X;
        this.ROW_Y = ROW_Y;

        TILE_SCALE = SCALE;

        tiles = new Tile[ROW_X * ROW_Y];

        updater.start();

        setSize(WIDTH, HEIGHT);
        setTitle("War");
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        generateTiles();
    }

    void generateTiles() {

        for (int x = 0; x < ROW_X/2; x++) {
            for (int y = 0; y < ROW_Y; y++) {

                Random random = new Random();
                int randomAttackSpeed = random.nextInt(10);

                tiles[index] = new Tile(TILE_SCALE, x, y, this,randomAttackSpeed+100);
                tiles[index].countryName = "A";
                add(tiles[index]);
                index++;
            }
        }

        for (int x = 0; x < ROW_X/2; x++) {
            for (int y = 0; y < ROW_Y; y++) {

                Random random = new Random();
                int randomAttackSpeed = random.nextInt(10);

                tiles[index] = new Tile(TILE_SCALE, x+ROW_X/2, y, this,randomAttackSpeed+11+100);
                tiles[index].countryName = "B";
                add(tiles[index]);
                index++;
            }
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();

        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != null) tiles[i].update();
        }
    }
}