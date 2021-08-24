import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// создание мира
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

// основной метод
    private static void command(String string) throws IOException {
        boolean isEnough = false;
        if (player == null) {
            player = new Hero(
                    string,
                    100,
                    20,
                    20,
                    0,
                    0
            );
            System.out.println(String.format("Эй, проснись, %s! Ну ты и соня. Тебя даже вчерашний шторм не разбудил ! Говорят, мы уже приплыли в Морроувинд. Нас выпустят, это точно!", player.getName()));
            //Метод для вывода меню
            printNavigation();
        }
        //Варианты для команд
        while (!isEnough) {
            switch (string) {
                case "торговец" -> {
                    merchantTime();
                    isEnough = true;
                }
                case "лес" -> {
                    commitFight();
                    isEnough = true;

                }
                case "выход", "принять судьбу" -> {
                    System.exit(1);
                }
                case "скиллы" -> {
                    System.out.println(player);
                    isEnough = true;
                }
                case "поход" -> {
                    command("лес");
                    isEnough = true;
                }
                case "город" -> {
                    isEnough = true;
                    printNavigation();
                    command(br.readLine());
                }
                case "возродиться" -> {
                    player = null;
                    System.out.println("Введите имя персонажа:");
                    command(br.readLine());
                }
                default -> {
                    isEnough = true;
                    System.out.println("Ты определился?");
                    command(br.readLine());
                }
            }
        }
    }

    //основное меню
    private static void printNavigation() {
        System.out.println("Куда вы хотите пойти?");
        System.out.println("(торговец) -> К Торговцу");
        System.out.println("(лес) -> В темный лес");
        System.out.println("(выход) -> Выход");
        System.out.println("(скиллы) -> Показать мои характеристики");
    }

    //идем в лес биться с монстром
    private static void commitFight() {
        battle.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d единиц здоровья.", player.getName(), player.getExp(), player.getGold(), player.getHealthPoints()));
                System.out.println("Желаете продолжить поход или вернуться в город? (поход/город)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() throws IOException {
                System.out.println("Извините, вы пали в бою. Введите *возродиться*, чтобы создать нового персонажа, или *принять судьбу*, чтобы выйти из игры.");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    interface FightCallback {
        void fightWin();
        void fightLost() throws IOException;
    }

    //создаем монстра
    private static Creature createMonster() {
        //Рандомайзер
        int random = (int) (Math.random() * 10);
        int min = 10;
        int max = 20;
        //С вероятностью 50% создается или скелет, или гоблин
        if (random % 2 == 0) return new Monster(
                "Гоблин",
                (int) (Math.random() * ++max) + min,
                (int) (Math.random() * ++max) + min,
                (int) (Math.random() * ++max) + min,
                100,
                (int) (Math.random() * ++max) + min
        );
        else return new Monster(
                "Скелет",
                (int) (Math.random() * ++max) + min,
                (int) (Math.random() * ++max) + min,
                (int) (Math.random() * ++max) + min,
                100,
                (int) (Math.random() * ++max) + min
        );
    }

    //торговля (есть непонятный баг)
    private static void merchantTime() throws IOException {
        System.out.println("Чего желаете?");
        System.out.println("(зелье) -> Зелье лечения");
        System.out.println("(меч) -> Меч тысячи истин");
        System.out.println("(щит) -> Щит  из вибраниума");
        System.out.println("(хватит) -> Уйти из магазина");
        boolean isFinished = false;
        while(!isFinished) {
            switch (br.readLine()) {
                case "зелье" -> {
                    merchant.sell(Merchant.Goods.POTION, player);
                }
                case "меч" -> {
                    merchant.sell(Merchant.Goods.SWORDOFA1000TRUTHS, player);
                }
                case "щит" -> {
                    merchant.sell(Merchant.Goods.VIBRANIUMSHIELD, player);
                }
                case "хватит" -> {
                    isFinished = true;
                    printNavigation();
                    command(br.readLine());
                }
                default -> System.out.println("Выбери что-нибудь, парень!");
            }
        }
    }
}
