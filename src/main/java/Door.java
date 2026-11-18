import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Door extends Element {
    public Door(int x, int y) {
        super(x, y);
    }

    public void Unkillable() {
        killable = false;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#ffc524"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "H");
    }
}