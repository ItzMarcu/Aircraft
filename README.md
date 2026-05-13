# Aircraft

**Aircraft** è un action game arcade sviluppato in Java utilizzando la libreria `Swing`. Il giocatore controlla una navicella spaziale con l'obiettivo di abbattere ondate di nemici che entrano nel campo di gioco, cercando di sopravvivere il più a lungo possibile e totalizzare il punteggio più alto.

---

## 🚀 Meccaniche di Gioco

* **Movimento:** Il giocatore può muovere la navicella orizzontalmente per schivare i pericoli e posizionarsi strategicamente.
* **Sistema di Tiro:** La navicella è dotata di un cannone a ripetizione per eliminare le minacce.
* **Nemici:** Gli avversari spawnano nella parte inferiore/centrale dello schermo e si muovono seguendo pattern predefiniti. Possono a loro volta sparare per colpire il giocatore.
* **Progressione:** Il gioco gestisce un sistema di livelli. Una volta pulito lo schermo da tutti i nemici, il livello aumenta e la difficoltà cresce con uno spawn maggiore di avversari.
* **Vite e Punteggio:** Il giocatore inizia con 3 vite. Ogni nemico abbattuto aumenta il punteggio totale visualizzato nell'HUD.

---

## 🚧 Stato Attuale e Bug Noti

Il progetto è attualmente in fase di **sviluppo/prototipazione**. Sebbene le basi siano solide, sono presenti alcuni errori logici e bug che richiedono attenzione:

### 🔴 Problemi Critici da Risolvere:
* **Logica delle Collisioni:** Il calcolo della distanza tra entità presenta un errore matematico nella formula della radice quadrata (differenza di quadrati invece della somma), rendendo le collisioni imprevedibili.
* **Movimento del Giocatore:** I controlli direzionali (Sinistra/Destra) risultano attualmente invertiti o poco intuitivi rispetto alla posizione del giocatore.
* **Sistema dei Danni:** Il sistema di "invulnerabilità temporanea" (damageable) non resetta correttamente lo stato, impedendo potenzialmente la corretta gestione delle vite.
* **Spawn dei Proiettili:** I proiettili del giocatore ereditano la velocità della nave ma non hanno una spinta verticale propria verso l'alto, rendendo il tiro inefficace nel setup attuale.
* **Spawn dei Nemici:** Attualmente i nemici appaiono nella parte inferiore dello schermo e si muovono verso l'alto, contrariamente al design previsto (dall'alto verso il basso).

---

## 🎮 Comandi
* **A / Freccia Sinistra:** Muovi a sinistra
* **D / Freccia Destra:** Muovi a destra
* **SPAZIO:** Spara
* **P:** Pausa
* **R:** Ricomincia (Game Over)