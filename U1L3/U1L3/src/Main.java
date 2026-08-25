public class Main {
    public static void main(String[] args) {
        int health = 100;
        int enemyPower = 5;
        int level = 1;

        int damage = enemyPower * 2; 
        health -= damage;
        System.out.println("You take " + damage + " damage. Health: " + health);

        int potion = 15;
        health += potion;
        level++;
        System.out.println("Healed! Health: " + health + " | Level: " + level);

        int hits = 3;
        int swings = 7;

        double acc1 = (double) hits / swings * 100;

        double acc2 = hits * 100.0 / swings;

        System.out.println("Acc 1: " + acc1 + "%");
        System.out.println("Acc 2: " + acc2 + "%");

        int turn = 6;
        boolean enrages = (turn % 3 == 0);
        System.out.println("Turn " + turn + " — enrages: " + enrages);

        turn = 5;
        enrages = (turn % 3 == 0);
        System.out.println("Turn " + turn + " — enrages: " + enrages);

        turn = 9;
        enrages = (turn % 3 == 0);
        System.out.println("Turn " + turn + " — enrages: " + enrages);

        double critDamage = damage * 1.75;
        int applied = (int) critDamage;
        System.out.println("Original double crit: " + critDamage);
        System.out.println("Crit! " + applied + " damage.");

        System.out.println(0.1 + 0.2);
    }
}