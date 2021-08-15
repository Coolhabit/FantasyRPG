public class Merchant implements Selling {
    @Override
    public String sell(Goods goods, Creature hero) {
        String result = "";
        if (hero.getGold() > 20) {
            switch (goods) {
                case POTION -> result = "potion";
                case SWORDOFA1000TRUTHS -> result = "sword of a 1000 truths";
                case VIBRANIUMSHIELD -> result = "vibranium shield";
            }
        } else {
            System.out.println("Недостаточно золота!");
        }
        return result;
    }

    public enum Goods {
        POTION,
        SWORDOFA1000TRUTHS,
        VIBRANIUMSHIELD
    }
}
