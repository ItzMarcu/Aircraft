import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class GameArea extends JPanel implements ActionListener, KeyListener {
    private int     ENEMIES_START; 
    private int     lives; 
    private int     score;
    private int     level;
    private boolean gameOver;
    private boolean paused;
    private boolean damageable;

    private List<Enemy>  enemies;
    private List<Bullet> bullets;
    private Ship         player;

    private final int WIDTH = 600;
    private final int HEIGHT = 900;
    private final int FPS = 60;
    private final int INITIAL_LIVES = 3;

    private final Timer timer;

    public GameArea() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        enemies = new ArrayList<>();
        bullets = new ArrayList<>();

        timer = new Timer(1000 / FPS, this);
        startGame();
    }

    public void startGame() {
        ENEMIES_START = 10;
        damageable = true;
        score = 0;
        lives = INITIAL_LIVES;
        level = 1;
        gameOver = false;
        paused = false;

        player = new Ship(WIDTH, HEIGHT);
        enemies.clear();
        bullets.clear();

        timer.start();
    }

    public void spawnEnemies(int count) {
        while (enemies.size() < count) 
            enemies.add(Enemy.spawnRandom(WIDTH, HEIGHT));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!paused && !gameOver) {
            updateEntities();
            checkCollisions();
        }

        if (enemies.isEmpty()) {
            level++;
            ENEMIES_START += 5;
            spawnEnemies(ENEMIES_START);
        }

        repaint();
    }

    public void updateEntities() {
        player.update(WIDTH, HEIGHT);

        Iterator<Enemy> enemiesIterator = enemies.iterator();
        while (enemiesIterator.hasNext()) {
            Enemy actual = enemiesIterator.next();
            if (!actual.isAlive()) enemiesIterator.remove();
            else actual.update(WIDTH, HEIGHT);
        }

        Iterator<Bullet> bulletsIterator = bullets.iterator();
        while (bulletsIterator.hasNext()) {
            Bullet actual = bulletsIterator.next();
            if (!actual.isAlive()) bulletsIterator.remove();
            else actual.update(WIDTH, HEIGHT);
        }
    }

    public void checkCollisions() {
        Iterator<Bullet> bulletsIterator = bullets.iterator();
        while (bulletsIterator.hasNext()) {
            Bullet b = bulletsIterator.next();
            for (Enemy e : enemies) {
                if (!e.isAlive()) continue;

                if (b.collidesWith(e)) {
                    b.destroy();
                    e.destroy();
                    score += e.getScore();
                    break;
                }
            }

            damageable = true;
            if (b.collidesWith(player)) {
                if (damageable) {
                    lives--;
                    b.destroy();
                    
                    if (lives <= 0) {
                        gameOver = true;
                        lives--;
                    }

                    damageable = false;
                    player.setColor(Color.YELLOW);
                    break;
                }
            }
        }

        updateEntities();
    }

    // ═══════════════════════════════════════════════════════════════
    //                          RENDERING 
    // ═══════════════════════════════════════════════════════════════

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOver) {
            drawGameOver(g);
            return;
        }

        player.draw(g);
        for (Enemy e : enemies)  e.draw(g);
        for (Bullet b : bullets) b.draw(g);

        drawHUD(g);
        if (paused) drawPaused(g);
    }

    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("SCORE: " + score,  10,          20);
        g.drawString("LIVES: " + lives,  10,          42);
        g.drawString("LEVEL: " + level,  WIDTH - 110, 20);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 48));
        g.drawString("GAME OVER", WIDTH / 2 - 140, HEIGHT / 2 - 20);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g.drawString("Score: " + score,          WIDTH / 2 - 70,  HEIGHT / 2 + 20);
        g.drawString("Premi R per ricominciare", WIDTH / 2 - 160, HEIGHT / 2 + 50);
    }

    private void drawPaused(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        g.drawString("PAUSA", WIDTH / 2 - 60, HEIGHT / 2);
    }

    // ═══════════════════════════════════════════════════════════════
    //                         INPUT TASTIERA
    // ═══════════════════════════════════════════════════════════════

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  case KeyEvent.VK_A: player.setTurningLeft(true);  break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D: player.setTurningRight(true); break;
            case KeyEvent.VK_SPACE:
                if (!gameOver && !paused) {
                    Bullet b = player.shoot();
                    if (b != null) bullets.add(b);
                }
                break;
            case KeyEvent.VK_P: paused = !paused; break;
            case KeyEvent.VK_R: startGame();      break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  case KeyEvent.VK_A: player.setTurningLeft(false);  break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D: player.setTurningRight(false); break;
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
}