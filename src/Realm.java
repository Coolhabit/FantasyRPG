import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Realm {
    private static BufferedReader br;
    private static Creature player = null;
    private static Battle battle = null;
    private static Merchant merchant = null;

    public static void main(String[] args) {
        br = new BufferedReader(new InputStreamReader(System.in));
        battle = new Battle();
        merchant = new Merchant();
        System.out.println("Введите имя персонажа:");
        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {
        if (player == null) {
            player = new Hero(
                    string,
                    100,
                    20,
                    20,
                    0,
                    0
            );
            System.out.println(String.format("Спасти наш мир от драконов вызвался %s! Да будет его броня крепка и бицепс кругл!", player.getName()));
            //Метод для вывода меню
            printNavigation();
        }
        //Варианты для команд
        switch (string) {
            case "1": {
                merchantTime();
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3":
                System.exit(1);
                break;
            case "да":
                command("2");
                break;
            case "нет": {
                printNavigation();
                command(br.readLine());
            }
        }
        //Снова ждем команды от пользователя
        command(br.readLine());
    }

    private static void printNavigation() {
        System.out.println("Куда вы хотите пойти?");
        System.out.println("1. К Торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Выход");
    }

    private static void commitFight() {
        battle.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d единиц здоровья.", player.getName(), player.getExp(), player.getGold(), player.getHealthPoints()));
                System.out.println("Желаете продолжить поход или вернуться в город? (да/нет)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {

            }
        });
    }

    interface FightCallback {
        void fightWin();
        void fightLost();
    }

    private static Creature createMonster() {
        //Рандомайзер
        int random = (int) (Math.random() * 10);
        //С вероятностью 50% создается или скелет, или гоблин
        if (random % 2 == 0) return new Goblin(
                "Гоблин",
                50,
                10,
                10,
                100,
                20
        );
        else return new Skeleton(
                "Скелет",
                25,
                20,
                20,
                100,
                10
        );
    }

    private static void merchantTime() throws IOException {
        System.out.println("Что вы хотите купить?");
        System.out.println("1. Зелье лечения");
        System.out.println("2. Меч тысячи истин");
        System.out.println("3. Щит  из вибраниума");

        switch (br.readLine()) {
            case "1":
                merchant.sell(Merchant.Goods.POTION);
                System.out.println("Что-то еще? (да/нет)");
                switch (br.readLine()) {
                    case "да": merchantTime();
                    case "нет": printNavigation(); command(br.readLine());
                }
            case "2":
                merchant.sell(Merchant.Goods.SWORDOFA1000TRUTHS);
                System.out.println("Что-то еще? (да/нет)");
                switch (br.readLine()) {
                    case "да": merchantTime();
                    case "нет": printNavigation(); command(br.readLine());
                }
            case "3":
                merchant.sell(Merchant.Goods.VIBRANIUMSHIELD);
                System.out.println("Что-то еще? (да/нет)");
                switch (br.readLine()) {
                    case "да": merchantTime();
                    case "нет": printNavigation(); command(br.readLine());
                }
        }
    }
}
