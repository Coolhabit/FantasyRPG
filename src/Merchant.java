public class Merchant implements Selling {
    @Override
    public String sell(Goods goods, Creature player) {
        String result = "";
            switch (goods) {
                case POTION -> {
                    result = "potion";
                    player.setHealthPoints(player.getHealthPoints() + 20);
                    System.out.println("Теперь ваше здоровье равно " + player.getHealthPoints());
                }
                case SWORDOFA1000TRUTHS -> {
                    result = "sword of a 1000 truths";
                    System.out.println("Меч привезут на следующей неделе");
                }
                case VIBRANIUMSHIELD -> {
                    result = "vibranium shield";
                    System.out.println("Капитан-Америка еще не вернул щит");
                }
            }
        return result;
    }

    public enum Goods {
        POTION,
        SWORDOFA1000TRUTHS,
        VIBRANIUMSHIELD
    }
}
