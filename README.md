# Aircraft

**Aircraft** is an arcade action game developed in Java using the `Swing` library. The player controls a spacecraft with the goal of shooting down waves of enemies entering the playfield, aiming to survive as long as possible and achieve the highest score.

---

## 🚀 Game Mechanics

* **Movement:** The player can move the spacecraft horizontally to dodge hazards and position themselves strategically.
* **Firing System:** The ship is equipped with a rapid-fire cannon to eliminate threats.
* **Enemies:** Opponents spawn in the lower/middle part of the screen and move according to predefined patterns. They can also fire back at the player.
* **Progression:** The game features a level system. Once all enemies are cleared from the screen, the level increases, and difficulty rises with a higher number of enemy spawns.
* **Lives and Score:** The player starts with 3 lives. Each defeated enemy increases the total score displayed in the HUD.

---

## 🚧 Current Status and Known Bugs

The project is currently in the **development/prototyping** phase. While the foundations are solid, there are several logical errors and bugs that require attention:

### 🔴 Critical Issues to Resolve:
* **Collision Logic:** The distance calculation between entities contains a mathematical error in the square root formula (using the difference of squares instead of the sum), making collisions unpredictable.
* **Damage System:** The "temporary invulnerability" (damageable) system does not reset its state correctly, potentially hindering proper life management.
* **Projectile Spawning:** Player projectiles inherit the ship's velocity but lack their own upward vertical thrust, making shooting ineffective in the current setup.

---

## 🎮 Controls
* **A / Left Arrow:** Move Left
* **D / Right Arrow:** Move Right
* **SPACE:** Shoot
* **P:** Pause
* **R:** Restart (Game Over)
