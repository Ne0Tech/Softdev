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

        int difficulty;
        do {
            System.out.print("Difficulty (1 = easy, 2 = normal, 3 = brutal): ");
            while (!in.hasNextInt()) {
                System.out.print("Numbers only. Try again: ");
                in.next(); 
            }
            difficulty = in.nextInt();
        } while (difficulty < 1 || difficulty > 3);
        in.nextLine(); 

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

        System.out.printf("%-12s HP %3d/%3d  Gold %4d  Lv %d%n",
                          playerName, health, MAX_HEALTH, gold, level);
        System.out.printf("Alive %-5b  Crit %.0f%%%n", alive, critChance * 100);
        System.out.println("");

        System.out.printf("%s enters the arena. The %s has %d HP.%n",
                          playerName, enemyName, enemyHealth);
        System.out.print("Press Enter to begin...");
        in.nextLine();
        System.out.println("");

        System.out.println(enemyName.toUpperCase() + " blocks your path!");
        System.out.printf("Opponent %-14s HP %3d  Power %2d%n",
                          enemyName, enemyHealth, enemyPower);
        System.out.println("");

        int turnNumber = 1;
        int potions = 2;
        boolean playing = true;

        while (playing) {
            if (!alive) {
                System.out.println("The fight is over. You fled from the arena in defeat!");
                playing = false;
                break;
            }
            if (enemyHealth <= 0) {
                System.out.println("The " + enemyName + " falls!");
                System.out.println("The fight is over. You stand victorious!");
                playing = false;
                break;
            }
            if (health <= 0) {
                System.out.println("You have fallen.");
                System.out.println("The fight is over. You were defeated.");
                playing = false;
                break;
            }

            System.out.printf("%n--- TURN %d ---%n", turnNumber);
            
            int roll = (turnNumber * 3) % 10 + 1;
            int damage2 = 0;

            System.out.print("[A]ttack  [D]efend  [P]otion  [F]lee  [S]tatus: ");
            String action = in.nextLine().trim().toUpperCase();

            switch (action) {
                case "A" -> {
                    if (roll >= 9) {
                        damage2 = enemyPower * 2;
                        System.out.println("Critical hit!");
                    } else if (roll >= 3) {
                        damage2 = enemyPower;
                        System.out.println("Solid hit!");
                    } else {
                        damage2 = 0;
                        System.out.println("You missed!");
                    }
                    enemyHealth -= damage2;
                    System.out.printf("You dealt %d damage. %s has %d HP left.%n", damage2, enemyName, Math.max(0, enemyHealth));
                }
                case "D" -> {
                    health += 5;
                    System.out.println("You raise your guard and recover 5 HP.");
                }
                case "P" -> {
                    if (potions > 0) {
                        potions--;
                        health += 25;
                        System.out.println("You gulp down a healing potion.");
                    } else {
                        System.out.println("You have none left!");
                    }
                }
                case "F" -> {
                }
                case "S" -> {
                    System.out.println("%n=== CURRENT CHARACTER STATUS ===");
                    System.out.printf("Challenger: %s | Gold: %d | Level: %d%n", playerName, gold, level);
                    String potionWord = (potions == 1) ? "potion" : "potions";
                    String statusWord = (health > MAX_HEALTH / 2) ? "steady" : "faltering";
                    System.out.println("Inventory: " + potions + " " + potionWord + " remaining.");
                    System.out.println("Stance: Your current footing feels " + statusWord + ".");
                    System.out.println("================================%n");
                    continue;
                }
                default -> {
                    System.out.println("You hesitate, and lose the turn.");
                }
            }

            if (alive && enemyHealth > 0) {
                health -= enemyPower;
                System.out.printf("The %s strikes back for %d.%n", enemyName, enemyPower);
            }

            if (health > MAX_HEALTH) {
                health = MAX_HEALTH;
            } else if (health < 0) {
                health = 0;
            }

            int bars = health / 5;
            String bar = "#".repeat(bars) + "-".repeat(20 - bars);
            System.out.printf("[%s] %d%%%n", bar, health);

            turnNumber++;
        }
    }
}
