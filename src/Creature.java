import java.io.Serializable;

public abstract class Creature implements Fighter, Serializable {
    private String name;
    private int level;
    private int hp;
    private int strength;
    private int agility;
    private int gold;
    private int exp;


    public Creature(String name, int level, int hp, int strength, int agility, int gold, int exp) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.strength = strength;
        this.agility = agility;
        this.gold = gold;
        this.exp = exp;
    }

    public int attack() {
        if (agility * 3 > getRandomValue()) return strength;
        else return  0;
    }
    //Геттеры и сеттеры
    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }
    public int getLevel() {return level;}
    public void setLevel(int level) { this.level = level;}
    public int getHealthPoints() {
        return hp;
    }
    public void setHealthPoints(int hp) {
        this.hp = hp;
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getAgility() {
        return agility;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public int getExp() {
        return exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    private int getRandomValue() {
        return (int) (Math.random() * 100);
    }

    @Override
    public String toString() {
        return String.format("%s = уровень: %d / здоровье: %d / сила: %d / ловкость: %d / золото: %d / опыт: %d", name, level, hp, strength, agility, gold, exp);
    }
}
