import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public final class GameArea extends JPanel implements ActionListener, KeyListener {
    private int     ENEMIES_START; 
    private int     lives; 
    private int     score;
    private int     level;
    private boolean gameOver;
    private boolean paused;
    private boolean damageable;

    private final Random       rng = new Random();
    private final List<Enemy>  enemies;
    private final List<Bullet> bullets;
    private Ship         player;

    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 900;
    private final int FPS = 60;
    private final int INITIAL_LIVES = 3;

    private final Timer timer;

    public GameArea() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();

        timer = new Timer(1000 / FPS, this);
        startGame();
    }

    public void startGame() {
        ENEMIES_START = 4;
        damageable = true;
        score = 0;
        lives = INITIAL_LIVES;
        level = 0;
        gameOver = false;
        paused = false;

        player = new Ship(SCREEN_WIDTH, SCREEN_HEIGHT);
        enemies.clear();
        bullets.clear();

        addKeyListener(this);
        timer.start();
    }

    public void spawnEnemies(int count) {
        while (enemies.size() < count) 
            enemies.add(Enemy.spawnRandom(SCREEN_WIDTH, SCREEN_HEIGHT));
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

        player.setColor(Color.WHITE);
        repaint();
    }

    public void updateEntities() {
        player.update(SCREEN_WIDTH, SCREEN_HEIGHT);

        Iterator<Enemy> enemiesIterator = enemies.iterator();
        while (enemiesIterator.hasNext()) {
            Enemy actual = enemiesIterator.next();
            if (!actual.isAlive()) enemiesIterator.remove();
            else actual.update(SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        Iterator<Bullet> bulletsIterator = bullets.iterator();
        while (bulletsIterator.hasNext()) {
            Bullet actual = bulletsIterator.next();
            if (!actual.isAlive()) bulletsIterator.remove();
            else actual.update(SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        if (!enemies.isEmpty()) {
            int randomEnemyIndex = rng.nextInt(enemies.size());
            Bullet b = enemies.get(randomEnemyIndex).shoot();
            if (b != null) bullets.add(b);
        }
    }

    public void checkCollisions() {
        for (Bullet b : bullets) {
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
        g.drawString("LEVEL: " + level,  SCREEN_WIDTH - 110, 20);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 48));
        g.drawString("GAME OVER", SCREEN_WIDTH / 2 - 140, SCREEN_HEIGHT / 2 - 20);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g.drawString("Score: " + score,          SCREEN_WIDTH / 2 - 70,  SCREEN_HEIGHT / 2 + 20);
        g.drawString("Premi R per ricominciare", SCREEN_WIDTH / 2 - 160, SCREEN_HEIGHT / 2 + 50);
    }

    private void drawPaused(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        g.drawString("PAUSA", SCREEN_WIDTH / 2 - 60, SCREEN_HEIGHT / 2);
    }

    // ═══════════════════════════════════════════════════════════════
    //                         INPUT TASTIERA
    // ═══════════════════════════════════════════════════════════════

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> player.setTurningLeft(true);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> player.setTurningRight(true);
            case KeyEvent.VK_SPACE -> {
                if (!gameOver && !paused) {
                    Bullet b = player.shoot();
                    if (b != null) bullets.add(b);
                }
            }
            case KeyEvent.VK_P -> paused = !paused;
            case KeyEvent.VK_R -> startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> player.setTurningLeft(false);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> player.setTurningRight(false);
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
}