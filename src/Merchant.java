public class Merchant implements Selling {
    @Override
    public String sell(Goods goods, Creature player) {
        String result = "";
            switch (goods) {
                case POTION -> {
                    if (player.getGold() >= 50) {
                        result = "potion";
                        player.setHealthPoints(player.getHealthPoints() + 20);
                        player.setGold(player.getGold() - 50);
                        System.out.println("Теперь ваше здоровье равно " + player.getHealthPoints());
                    } else {
                        System.out.println("Нужно больше золота!");
                    }
                }
                case SWORDOFA1000TRUTHS -> {
                    if (player.getGold() >= 100) {
                        result = "sword of a 1000 truths";
                        player.setStrength(player.getStrength() + 10);
                        player.setGold(player.getGold() - 100);
                        System.out.println("Теперь ваша сила равна " + player.getStrength());
                    } else {
                        System.out.println("Нужно больше золота!");
                    }
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
