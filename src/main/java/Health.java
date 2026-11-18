public class Health {
    private int remaining;

    public Health(int hp) {remaining = hp;}

    public void ProcessDMG(int dmg) {remaining -= dmg;}

    public boolean isDead() {return remaining >= 0;}
}