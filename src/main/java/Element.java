import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    protected Position position;
    protected Health health;
    protected boolean killable = true;

    public Element(int x, int y) {
        position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isKillable() {return killable;}

    public boolean isDead() {return health.isDead();}

    public abstract void draw(TextGraphics graphics);
}
