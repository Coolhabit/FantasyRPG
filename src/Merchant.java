public class Merchant implements Selling {
    @Override
    public String sell(Goods goods) {
        String result = "";
            switch (goods) {
                case POTION -> result = "potion";
                case SWORDOFA1000TRUTHS -> result = "sword of a 1000 truths";
                case VIBRANIUMSHIELD -> result = "vibranium shield";
            }
        return result;
    }

    public enum Goods {
        POTION,
        SWORDOFA1000TRUTHS,
        VIBRANIUMSHIELD
    }
}
