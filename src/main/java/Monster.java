import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {
    private final Random random = new Random();

    public Monster(int x, int y) {
        super(x, y);
    }

    public Position move() {
        return switch (random.nextInt(4)) {
            case 0 -> new Position(position.getX(), position.getY() - 1);
            case 1 -> new Position(position.getX(), position.getY() + 1);
            case 2 -> new Position(position.getX() - 1, position.getY());
            default -> new Position(position.getX() + 1, position.getY());
        };
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#422100"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }
}
