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

        int health = MAX_HEALTH;
        int gold = STARTING_GOLD;
        int level = 1;
        boolean alive = true;
        double critChance = 0.15;

        String enemyName = "Cave Goblin";
        int enemyHealth = 30 + difficulty * 15;
        int enemyPower = 4 + difficulty * 3;

        System.out.println("\nSelect your starting loadout:");
        System.out.println("1) Greatsword (Standard crit)");
        System.out.println("2) Daggers (Higher crit chance: 30%)");
        System.out.println("3) Merchant Ring (+15 starting gold)");
        System.out.print("Choice (1-3): ");
        int loadoutChoice = in.nextInt();
        in.nextLine();

        if (loadoutChoice == 2) {
            critChance = 0.30;
            System.out.println("Daggers equipped! Crit chance boosted to 30%.");
        } else if (loadoutChoice == 3) {
            gold += 15;
            System.out.println("Merchant Ring equipped! Gold increased to " + gold + ".");
        } else {
            System.out.println("Greatsword equipped!");
        }

        System.out.println("");
        System.out.printf("%-12s HP %3d/%3d  Gold %4d  Lv %d%n",
                          playerName, health, MAX_HEALTH, gold, level);
        System.out.printf("Alive %-5b  Crit %.0f%%%n", alive, critChance * 100);
        System.out.println("");

        // Summary Line & Wait for Enter
        System.out.printf("%s enters the arena. The %s has %d HP.%n",
                          playerName, enemyName, enemyHealth);
        System.out.print("Press Enter to begin...");
        in.nextLine();

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

        int damage = enemyPower * 2;
        health -= damage;
        System.out.println("You take " + damage + " damage. Health: " + health);

        int potion = 15;
        health += potion;
        level++;
        System.out.println("You drink a potion. Health: " + health);
        System.out.println("You reach level " + level + ".");
        System.out.println("");

        int hits = 3;
        int swings = 7;
        double acc1 = (double) hits / swings * 100;
        System.out.printf("Accuracy: %.1f%%%n", acc1);
        System.out.println("");

        int turn = 6;
        boolean enrages = (turn % 3 == 0);
        System.out.println("Turn " + turn + " — enrages: " + enrages);
        System.out.println("");

        int bars = health / 5;
        String bar = "#".repeat(bars) + "-".repeat(20 - bars);
        System.out.printf("[%s] %d%%%n", bar, health);
    }
}