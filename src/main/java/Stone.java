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
        if (killable) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#969696"));
            graphics.putString(new TerminalPosition(position.getX(), position.getY()), "S");

        }
        else {
            graphics.setForegroundColor(TextColor.Factory.fromString("#808080"));
            graphics.putString(new TerminalPosition(position.getX(), position.getY()), "T");
        }
    }
}