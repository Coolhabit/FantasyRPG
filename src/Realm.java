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
                    1,
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
                case "отдых" -> {
                    levelUp();
                    isEnough = true;
                }
                case "выход", "принять судьбу" -> {
                    System.exit(1);
                }
                case "скиллы" -> {
                    isEnough = true;
                    System.out.println(player);
                    command(br.readLine());
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
                    isEnough = true;
                    System.out.println("Введите имя персонажа:");
                    String newName = br.readLine();
                    player.setName(newName);
                    player.setLevel(1);
                    player.setStrength(20);
                    player.setAgility(0);
                    player.setGold(0);
                    player.setHealthPoints(50);
                    player.setExp(0);
                    System.out.println("Вы возродились под именем " + player.getName() + "! Ваши навыки вернулись к изначальному значению, опыт и золото сгорели, а здоровье равно " + player.getHealthPoints());
                    printNavigation();
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
        System.out.println("(отдых) -> Поспать и отдохнуть");
        System.out.println("(скиллы) -> Показать мои характеристики");
        System.out.println("(выход) -> Выход");
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
                System.out.println("Извините, вы пали в бою. Введите (возродиться), чтобы создать нового персонажа, или (принять судьбу), чтобы выйти из игры.");
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
        int max = 25;
        //С вероятностью 50% создается или скелет, или гоблин
        if (random == 1) return new Monster(
                "Дракон",
                10,
                50,
                50,
                50,
                200,
                100
        );
        else if (random % 2 == 0) return new Monster(
                "Гоблин",
                1,
                (int) (Math.random() * ++max) + min,
                (int) (Math.random() * ++max) + min,
                (int) (Math.random() * ++max) + min,
                100,
                34
        );
        else return new Monster(
                "Скелет",
                1,
                (int) (Math.random() * ++max) + min,
                (int) (Math.random() * ++max) + min,
                (int) (Math.random() * ++max) + min,
                100,
                34
        );
    }

    //торговля (есть непонятный баг)
    private static void merchantTime() throws IOException {
        System.out.println("Чего желаете?");
        System.out.println("(зелье) -> Зелье лечения (50 золота / +20 к здоровью)");
        System.out.println("(меч) -> Меч тысячи истин (100 золота / +10 к силе)");
        System.out.println("(щит) -> Щит  из вибраниума (200 золота / +10 к броне)");
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

    private static void levelUp() throws IOException {
        if(player.getExp() >= 100) {
            player.setLevel(player.getLevel() + 1);
            player.setExp(player.getExp() - 100);
            player.setStrength(player.getStrength() + 5);
            player.setAgility(player.getAgility() + 5);
            player.setHealthPoints(player.getHealthPoints() + 10);
            System.out.printf("Вы чувствуете себя бодрым и отдохнувшим! Ваш уровень повысился на 1 и равен %d. Показатели силы и ловкости увеличились на 5, здоровье увеличилось на 10%n", player.getLevel());
        } else {
            System.out.println("Вы всю ночь ворочались во сне и толком не выспались. Еще один день впереди.");
        }
        command(br.readLine());
    }
}
