import java.util.Scanner;

public class App {

    static final int MAX_HEALTH = 100;
    static final int STARTING_GOLD = 20;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

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

        System.out.print("Difficulty (1 = easy, 2 = normal, 3 = brutal): ");
        int difficulty = in.nextInt();
        in.nextLine();

        // ---------- Switch Expression ----------
        String difficultyName = switch (difficulty) {
            case 1 -> "Easy";
            case 2 -> "Normal";
            case 3 -> "Brutal";
            default -> "Unknown";
        };
        System.out.println("Difficulty set to: " + difficultyName);

        int health = MAX_HEALTH;
        int gold = STARTING_GOLD;
        int level = 1;
        boolean alive = true;
        double critChance = 0.15;

        String enemyName = "Cave Goblin";
        int enemyHealth = 30 + difficulty * 15;
        int enemyPower = 4 + difficulty * 3;

        // ---------- L4 · status display ----------
        System.out.printf("%-12s HP %3d/%3d  Gold %4d  Lv %d%n",
                          playerName, health, MAX_HEALTH, gold, level);
        System.out.printf("Alive %-5b  Crit %.0f%%%n", alive, critChance * 100);
        System.out.println("");

        System.out.printf("%s enters the arena. The %s has %d HP.%n",
                          playerName, enemyName, enemyHealth);
        System.out.print("Press Enter to begin...");
        in.nextLine();
        System.out.println("");

        // ---------- L4 · String methods on enemy ----------
        System.out.println(enemyName.toUpperCase() + " blocks your path!");
        System.out.printf("Opponent %-14s HP %3d  Power %2d%n",
                          enemyName, enemyHealth, enemyPower);
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

        // ---------- 2 · accuracy calculations ----------
        int hits = 3;
        int swings = 7;

        int brokenAccuracy = hits / swings * 100;
        System.out.println("Accuracy (broken): " + brokenAccuracy + "%");

        double acc1 = (double) hits / swings * 100;
        double acc2 = hits * 100.0 / swings;

        System.out.printf("Accuracy (cast):    %.1f%%%n", acc1);
        System.out.printf("Accuracy (reorder): %.1f%%%n", acc2);
        System.out.println("");

        // ---------- 3 · turn rhythm ----------
        int turn = 6;
        boolean enrages = (turn % 3 == 0);
        System.out.println("Turn " + turn + " — enrages: " + enrages);
        System.out.println("");

        // ---------- 4 · crit casting ----------
        double critDamage = damage * 1.75;
        int applied = (int) critDamage;
        System.out.println("Crit damage (double): " + critDamage);
        System.out.println("Crit damage (int):    " + applied);
        System.out.println("Lost to the cast:     " + (critDamage - applied));
        System.out.println("");

        // ---------- Combat Setup ----------
        int roll = 7;
        int damage2 = 0;
        int potions = 2;

        // ---------- Menu Input ----------
        System.out.print("[A]ttack  [D]efend  [P]otion  [F]lee: ");
        String action = in.nextLine().trim().toUpperCase();

        // ---------- Switch Statement ----------
        switch (action) {
            case "A" -> {
                if (roll >= 8) {
                    damage2 = 15;
                    System.out.println("Critical hit!");
                } else if (roll >= 4) {
                    damage2 = 8;
                    System.out.println("Solid hit!");
                } else {
                    damage2 = 0;
                    System.out.println("You missed!");
                }
            }
            case "D" -> {
                health += 5;
                System.out.println("You raise your guard and recover 5 HP.");
            }
            case "P" -> {
                if (potions > 0) {
                    potions--;
                    health += 20;
                    System.out.println("You gulp down a healing potion.");
                } else {
                    System.out.println("You check your belt, but you are out of potions!");
                }
            }
            case "F" -> System.out.println("You flee from the arena in defeat!");
            default -> System.out.println("Invalid command. You freeze up and waste your turn!");
        }

        // ---------- Ternary Operators ----------
        String potionWord = (potions == 1) ? "potion" : "potions";
        String statusWord = (health > MAX_HEALTH / 2) ? "steady" : "faltering";

        System.out.println("You have " + potions + " " + potionWord + " remaining.");
        System.out.println("Your stance is " + statusWord + ".");
        System.out.println("");

        enemyHealth -= damage2;
        System.out.printf("%s has %d HP left.%n", enemyName, enemyHealth);

        // ---------- L6 · fight completion ----------
        if (enemyHealth <= 0) {
            System.out.println("The " + enemyName + " falls!");
            alive = true;
        } else if (health <= 0) {
            System.out.println("You have fallen.");
            alive = false;
        }

        if (swings > 0 && hits / swings > 0.5) {
            System.out.println("Your aim is holding up.");
        }
        if (health < MAX_HEALTH / 4 && gold >= 10) {
            System.out.println("You should buy a potion.");
        }
        if (!alive || enemyHealth <= 0) {
            System.out.println("The fight is over.");
        }

        // ---------- L6 · health clamp ----------
        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;
        } else if (health < 0) {
            health = 0;
        }

        // ---------- L4 · health bar ----------
        int bars = health / 5;
        String bar = "#".repeat(bars) + "-".repeat(20 - bars);
        System.out.printf("[%s] %d%%%n", bar, health);
    }
}