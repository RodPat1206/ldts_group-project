import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Stone extends Element {
    private boolean door;

    public Stone(int x, int y) {
        super(x, y);
        this.door = false;
    }

    public void Unkillable() {
        killable = false;
    }

    public void aDoorable() {
        this.door = true;
    }

    public boolean hasDoor() {return door;}

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#ffc524"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "S");
    }
}