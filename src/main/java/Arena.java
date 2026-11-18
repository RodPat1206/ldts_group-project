import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private final int width;
    private final int height;
    private final Hero hero;
    private final List<Wall> walls;
    private final List<Coin> coins;
    private final List<Monster> monsters;
    private final List<Stone> stones;
    private final List<Door> doors;

    private boolean gameOver = false;

    public boolean isGameOver() {
        return gameOver;
    }


    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(1, 1);
        this.stones = createStones();
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
        this.doors = null;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private boolean Taken(Position p) {
        for (Stone stone : stones) {
            if (p.equals(stone.getPosition())) {return true;}
        }
        return p.equals(hero.getPosition());
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        List<Monster> monsters = new ArrayList<>();
        while (monsters.size() < 3) {
            Position p = new Position(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1);
            boolean taken = Taken(p);
            for (Monster m : monsters)
                if (m.getPosition().equals(p))
                    taken = true;
            if (!taken)
                monsters.add(new Monster(p.getX(), p.getY()));
        }
        return monsters;
    }

    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (coin.getPosition().equals(hero.getPosition())) {
                coins.remove(coin);
                break;
            }
        }
    }

    private List<Stone> createStones() {
        int w = 2; int h = 2;
        while (h <= height) {
            while (w <= width) {
                Stone s = new Stone(w, h);
                s.Unkillable();
                stones.add(s);
                w += 2;
            }
            h += 2;
        }
        Random random = new Random();
        ArrayList<Stone> stones = new ArrayList<>();
        boolean first = true;
        while (stones.size() < 5) {
            Position p = new Position(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1);
            while (p.getX() == 1 && (p.getY() == 1 || p.getY() ==2) || p.getX() == 2 && p.getY() == 1) {
                p = new Position(random.nextInt(width - 2) + 1,
                        random.nextInt(height - 2) + 1);
            }
            boolean taken = false;
            for (Stone s : stones)
                if (s.getPosition().equals(p)) {
                    taken = true;
                    break;
                }
            if (!taken) {
                Stone st = new Stone(p.getX(), p.getY());
                if (first) {
                    st.aDoorable();
                }
                stones.add(st);
            }
        }
        return stones;
    }

    private void brokenStones() {
        for (Stone stone: stones) {
            if (stone.isKillable() && stone.isDead()) {
                if (stone.hasDoor()) {doors.add(new Door(stone.getPosition().getX(), stone.getPosition().getY()));}
                stones.remove(stone);
            }
        }
    }

    private void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
            retrieveCoins();
        }
    }

    private boolean isWalkable(Position position) {
        for (Wall wall : walls)
            if (wall.getPosition().equals(position))
                return false;
        for (Stone stone : stones)
            if (stone.getPosition().equals(position))
                return false;
        return position.getX() >= 0 && position.getX() < width
                && position.getY() >= 0 && position.getY() < height;
    }

    private boolean canHeroMove(Position position) {
        return isWalkable(position);
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position next = monster.move();
            if (isWalkable(next))
                monster.setPosition(next);
        }
    }

    private void verifyMonsterCollisions() {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                System.out.println("A monster got you! Game over.");
                gameOver = true;
                return;
            }
        }
    }

    public void draw(TextGraphics graphics) {
        for (Wall wall :walls) wall.draw(graphics);
        for (Coin coin : coins) coin.draw(graphics);
        for (Stone stone : stones) stone.draw(graphics);
        for (Monster monster : monsters) monster.draw(graphics);
        for (Door door : doors) door.draw(graphics);
        hero.draw(graphics);
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        while (coins.size() < 5) {
            Position p = new Position(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1);
            boolean taken = Taken(p);
            for (Coin c : coins)
                if (c.getPosition().equals(p)) {
                    taken = true;
                    break;
                }
            if (!taken)
                coins.add(new Coin(p.getX(), p.getY()));
        }
        return coins;
    }

    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp ->  moveHero(hero.moveUp());
            case ArrowLeft -> moveHero(hero.moveLeft());
            case ArrowRight -> moveHero(hero.moveRight());
            case ArrowDown -> moveHero(hero.moveDown());
            case Character -> {
                switch (key.getCharacter()) {
                    case 'w' -> moveHero(hero.moveUp());
                    case 'a' -> moveHero(hero.moveLeft());
                    case 'd' -> moveHero(hero.moveRight());
                    case 's' -> moveHero(hero.moveDown());
                }
            }
        }
        verifyMonsterCollisions();
        if (gameOver) return;
        moveMonsters();
        verifyMonsterCollisions();
    }
}
