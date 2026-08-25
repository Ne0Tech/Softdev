public class Main {
    public static void main(String[] args) {
        int health = 150; 
        final int MAX_HEALTH = 100;
        int enemyPower = 12;
        int enemyHealth = 20;
        String enemyName = "Goblin";
        int gold = 15;
        boolean alive = true;

        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;
        } else if (health < 0) {
            health = 0;
        }
        System.out.println("Current Health (Clamped): " + health + "/" + MAX_HEALTH);

        int roll = 7; 
        int damage;

        if (roll >= 9) {
            damage = enemyPower * 2;
            System.out.println("CRITICAL HIT!");
        } else if (roll >= 3) {
            damage = enemyPower;
            System.out.println("A solid hit.");
        } else {
            damage = 0;
            System.out.println("You miss.");
        }

        enemyHealth -= damage;
        System.out.println(enemyName + " takes " + damage + " damage. Remaining: " + enemyHealth);

        if (enemyHealth <= 0) {
            System.out.println("The " + enemyName + " falls!");
            alive = true;
        } else if (health <= 0) {
            System.out.println("You have fallen.");
            alive = false;
        }

        if (health < MAX_HEALTH / 4 && gold >= 10) {
            System.out.println("You should buy a potion.");
        }
    }
}