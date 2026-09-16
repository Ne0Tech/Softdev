import java.util.Scanner;

public class App {

    static final int MAX_HEALTH = 100;
    static final int STARTING_GOLD = 20;
    
    static final int GRID_ROWS = 5;
    static final int GRID_COLS = 11;
    static final int ARENA_ROW = 2; 

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String title = """
                ========================
                    THE ARENA (2D Grid)
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
        
        String enemyName = "Cave Goblin";
        int enemyHealth = 30 + difficulty * 15;
        int enemyPower = 4 + difficulty * 3;

        int playerCol = 1;  
        int enemyCol = 8;   

        System.out.printf("%n%s enters the arena. The %s watches from across the sands.%n", playerName, enemyName);
        System.out.println("Use [L] to move Left and [R] to move Right to approach your foe.");
        System.out.print("Press Enter to step onto the sands...");
        in.nextLine();

        boolean exploring = true;
        while (exploring) {
            drawArena(playerCol, enemyCol);

            int distance = Math.abs(playerCol - enemyCol);
            if (distance == 1) {
                System.out.printf("%n[!] You are face-to-face with the %s!%n", enemyName);
                exploring = false;
                break; 
            }

            String move = readChoice(in, "Move ([L]eft, [R]ight): ");

            if (move.equals("L")) {
                if (playerCol > 0) {
                    playerCol--;
                } else {
                    System.out.println("The arena wall blocks your retreat!");
                }
            } else if (move.equals("R")) {
                if (playerCol < GRID_COLS - 1) {
                    playerCol++;
                } else {
                    System.out.println("The arena wall blocks your advancement!");
                }
            } else {
                System.out.println("You scan the crowd instead of moving.");
            }
        }

        runCountdown(3);

        int turnNumber = 1;
        int potions = 2;
        boolean playing = true;

        while (playing) {
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

            System.out.printf("%n--- COMBAT TURN %d ---%n", turnNumber);
            int roll = (turnNumber * 3) % 10 + 1;
            int damage2 = 0;

            String action = readChoice(in, "[A]ttack  [D]efend  [P]otion  [F]lee: ");

            switch (action) {
                case "A" -> {
                    damage2 = calculateDamage(roll, enemyPower);
                    enemyHealth -= damage2;
                    System.out.printf("You dealt %d damage. %s has %d HP left.%n", damage2, enemyName, Math.max(0, enemyHealth));
                }
                case "D" -> {
                    damage2 = calculateDamage(); 
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
                    System.out.println("You break away and escape with your life!");
                    playing = false;
                    break;
                }
                default -> System.out.println("You hesitate, and lose the turn.");
            }

            if (playing && enemyHealth > 0) {
                health -= enemyPower;
                System.out.printf("The %s strikes back for %d.%n", enemyName, enemyPower);
            }

            if (health > MAX_HEALTH) health = MAX_HEALTH;
            if (health < 0) health = 0;

            drawHealthBar(health);

            turnNumber++;
        }
    }

    public static void drawArena(int playerCol, int enemyCol) {
        System.out.println("\n--- ARENA FLOOR ---");
        for (int r = 0; r < GRID_ROWS; r++) {
            for (int c = 0; c < GRID_COLS; c++) {
                if (r == ARENA_ROW && c == playerCol) {
                    System.out.print("@"); 
                } else if (r == ARENA_ROW && c == enemyCol) {
                    System.out.print("X"); 
                } else {
                    System.out.print("."); 
                }
            }
            System.out.println(); 
        }
        System.out.println("-------------------");
    }

    public static String readChoice(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim().toUpperCase();
    }

    public static void runCountdown(int seconds) {
        System.out.println("\nPrepare yourself! Weapons drawn...");
        for (int count = seconds; count > 0; count--) {
            System.out.println(count + "...");
            try { Thread.sleep(600); } catch (InterruptedException e) { } 
        }
        System.out.println("FIGHT!\n");
    }

    public static int calculateDamage(int roll, int enemyPower) {
        if (roll >= 9) {
            System.out.println("Critical hit!");
            return enemyPower * 2;
        } else if (roll >= 3) {
            System.out.println("Solid hit!");
            return enemyPower;
        } else {
            System.out.println("You missed!");
            return 0;
        }
    }

    public static int calculateDamage() {
        return 0;
    }

    public static void drawHealthBar(int currentHealth) {
        int bars = currentHealth / 5;
        String bar = "#".repeat(bars) + "-".repeat(20 - bars);
        System.out.printf("[%s] %d%%%n", bar, currentHealth);
    }
}
