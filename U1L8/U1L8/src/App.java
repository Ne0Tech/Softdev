/* * U1 L8 — WHILE LOOPS AND THE GAME LOOP · STARTER CODE * 7184 Software Development · Unit 1, Lesson 8 * * ALREADY HERE: Lessons 1-7 finished — including the combat menu and the * switch that drives it. * YOU'RE ADDING: input validation that finally closes the Lesson 5 TODO, and * a while loop that turns one turn into a whole fight. * * javac Main.java * java Main */ 
import java.util.Scanner; 

public class App { 
    static final int MAX_HEALTH = 100; 
    static final int STARTING_GOLD = 20; 

    public static void main(String[] args) { 
        
        Scanner in = new Scanner(System.util.Scanner(System.in)); 
        
        // ---------- L4 · text block title screen ---------- 
        String title = """
             ========================
                    THE ARENA
             ========================
             """; 
        System.out.print(title); 
        System.out.println("Sand, torchlight, and a crowd that has already decided how this ends."); 
        System.out.println("The gate opens."); 
        System.out.println(""); 
        System.out.print("What is your name, challenger? "); 
        String playerName = in.nextLine().trim(); 
        if (playerName.isEmpty()) { 
            playerName = "Challenger"; 
        } 

        // TODO 1: close the Lesson 5 TODO — validate the difficulty. 
        int difficulty; 
        do { 
            System.out.print("Choose a difficulty (1-Easy, 2-Normal, 3-Brutal): ");
            while (!in.hasNextInt()) { 
                System.out.print("Numbers only. Try again: "); 
                in.next(); // <- throws the bad word away 
            } 
            difficulty = in.nextInt(); 
        } while (difficulty < 1 || difficulty > 3); 
        in.nextLine(); // Clear the buffer newline

        // ---------- L7 · a switch EXPRESSION — it produces a value ---------- 
        String difficultyName = switch (difficulty) { 
            case 1 -> "Easy"; 
            case 2 -> "Normal"; 
            case 3 -> "Brutal"; 
            default -> "Unknown"; 
        }; 
        System.out.println("Difficulty: " + difficultyName); 
        System.out.println(""); 

        int health = MAX_HEALTH; 
        int gold = STARTING_GOLD; 
        int level = 1; 
        boolean alive = true; 
        double critChance = 0.15; 
        String enemyName = "Cave Goblin"; 
        int enemyHealth = 30 + difficulty * 15; 
        int enemyPower = 4 + difficulty * 3; 

        // ---------- L4 · one formatted line instead of six ---------- 
        System.out.printf("%-12s HP %3d/%3d Gold %4d Lv %d%n", playerName, health, MAX_HEALTH, gold, level); 
        System.out.printf("Alive %-5b Crit %.0f%%%n", alive, critChance * 100); 
        System.out.println(""); 
        System.out.printf("%s enters the arena. The %s has %d HP.%n", playerName, enemyName, enemyHealth); 
        System.out.print("Press Enter to begin..."); 
        in.nextLine(); 
        System.out.println(""); 

        // ---------- L4 · String methods on the enemy ---------- 
        System.out.println(enemyName.toUpperCase() + " blocks your path!"); 
        System.out.printf("Opponent %-14s HP %3d Power %2d%n", enemyName, enemyHealth, enemyPower); 
        System.out.println("Name length: " + enemyName.length()); 
        boolean isBoss = enemyName.contains("Dragon"); 
        System.out.println("Boss fight: " + isBoss); 

        if (enemyName.equalsIgnoreCase("cave goblin")) { 
            System.out.println("You have fought one of these before."); 
        } 
        System.out.println(""); 

        // ---------- 1 · combat arithmetic ---------- 
        int damage = enemyPower * 2; 
        health -= damage; 
        System.out.println("You take " + damage + " damage. Health: " + health); 
        int potion = 15; 
        health += potion; 
        level++; 
        System.out.println("You drink a potion. Health: " + health); 
        System.out.println("You reach level " + level + "."); 
        System.out.println(""); 

        // ---------- 2 · the accuracy bug, then both fixes ---------- 
        int hits = 3; 
        int swings = 7; 
        int brokenAccuracy = hits / swings * 100; 
        System.out.println("Accuracy (broken): " + brokenAccuracy + "%"); 

        double acc1 = (double) hits / swings * 100; 
        double acc2 = hits * 100.0 / swings; 
        System.out.printf("Accuracy (cast): %.1f%%%n", acc1); 
        System.out.printf("Accuracy (reorder): %.1f%%%n", acc2); 
        System.out.println(""); 

        // ---------- 3 · a rhythm with % ---------- 
        int baseTurn = 6; 
        boolean enrages = (baseTurn % 3 == 0); 
        System.out.println("Turn " + baseTurn + " — enrages: " + enrages); 
        System.out.println(""); 

        // ---------- 4 · crit, and what the cast costs ---------- 
        double critDamage = damage * 1.75; 
        int applied = (int) critDamage; 
        System.out.println("Crit damage (double): " + critDamage); 
        System.out.println("Crit damage (int): " + applied); 
        System.out.println("Lost to the cast: " + (critDamage - applied)); 
        System.out.println(""); 

        // ---------- TODO 2: The Core Game Loop ---------- 
        int turnNumber = 1; 
        boolean playing = true; 
        int potions = 2; 
        boolean fled = false;

        while (playing) { 
            // TODO 5: Loop termination condition checks 
            if (!alive) {
                System.out.println("The fight is over. You were defeated.");
                playing = false;
                break;
            } else if (enemyHealth <= 0) {
                System.out.println("The fight is over. You stand victorious!");
                playing = false;
                break;
            } else if (fled) {
                System.out.println("The fight is over. You escaped with your life.");
                playing = false;
                break;
            }

            System.out.printf("--- TURN %d ---%n", turnNumber);
            System.out.printf("%s: HP %d/%3d | %s: HP %d%n", playerName, health, MAX_HEALTH, enemyName, enemyHealth);

            // TODO 3: Generate a turn-by-turn rolling modifier
            int roll = (turnNumber * 3) % 10 + 1; 
            int damage2 = 0; 

            // ---------- L7 · the menu ---------- 
            System.out.println("Your move."); 
            System.out.print("[A]ttack [D]efend [P]otion [F]lee: "); 
            String action = in.nextLine().trim().toUpperCase(); 

            switch (action) { 
                case "A" -> { 
                    if (roll >= 9) { 
                        damage2 = enemyPower * 2; 
                        System.out.println("CRITICAL HIT!"); 
                    } else if (roll >= 3) { 
                        damage2 = enemyPower; 
                        System.out.println("A solid hit."); 
                    } else { 
                        damage2 = 0; 
                        System.out.println("You miss."); 
                    }
                    enemyHealth -= damage2;
                    System.out.printf("You dealt %d damage to the %s!%n", damage2, enemyName);
                } 
                case "D" -> { 
                    health += 5; 
                    System.out.println("You raise your guard and recover 5 HP."); 
                } 
                case "P" -> { 
                    if (potions > 0) { 
                        potions--; 
                        health += 25; 
                        System.out.println("You drink a potion and recover 25 HP."); 
                    } else { 
                        System.out.println("You reach for a potion. There are none."); 
                    } 
                } 
                case "F" -> { 
                    fled = true; 
                    System.out.println("You run for the gate. The crowd howls."); 
                } 
                default -> System.out.println("The crowd jeers. You hesitate and lose the turn."); 
            } 

            // TODO 4: Enemy counter-attacks if still alive and player hasn't fled
            if (enemyHealth > 0 && !fled) {
                health -= enemyPower;
                System.out.printf("The %s strikes back and deals %d damage!%n", enemyName, enemyPower);
            }

            // Health check flag setting
            if (health <= 0) {
                alive = false;
            }

            // ---------- L7 · the ternary ---------- 
            System.out.printf("You have %d %s left.%n", potions, potions == 1 ? "potion" : "potions"); 
            String condition = health > MAX_HEALTH / 2 ? "steady" : "faltering"; 
            System.out.println("You look " + condition + "."); 
            System.out.println(""); 

            // ---------- L6 · the fight can now end ---------- 
            if (enemyHealth <= 0) { 
                System.out.println("The " + enemyName + " falls!"); 
            } else if (health <= 0) { 
                System.out.println("You have fallen."); 
            } 

            // ---------- L6 · the clamp ---------- 
            if (health > MAX_HEALTH) { 
                health = MAX_HEALTH; 
            } else if (health < 0) { 
                health = 0; 
            } 

            // ---------- L4 · the health bar ---------- 
            int bars = health / 5; 
            String bar = "#".repeat(bars) + "-".repeat(20 - bars); 
            System.out.printf("[%s] %d%%%n%n", bar, health); 

            turnNumber++;
        } 
    } 
}
