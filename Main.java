
import java.io.*;
import java.util.Scanner;

public class Main {

    private static final String nazvaFaylu = "text.txt";
    private static final String komandaVyhodu = "СТОП";
    private static final int maksRyadkiv = 1000;

    public static void main(String[] args) {
        Scanner skan = new Scanner(System.in);
        boolean pratsyuye = true;

        System.out.println("( ͡°͜ʖ͡°) Файловий редактор лупашки запущено ( ͡°͜ʖ͡°)");


        while (pratsyuye) {
            pokazatyHolovneMenu();
            int vybir = otrymatiChyslo(skan, "Ваш вибір: ");

            switch (vybir) {
                case 1:
                    zapysDoFaylu(skan);
                    break;

                case 2:
                    menuChytannya(skan);
                    break;

                case 3:
                    vstavytyVRyadok(skan);
                    break;

                case 4:
                    System.out.println("\n✓ До побачення! Файловий редактор лупашки закрито.");
                    pratsyuye = false;
                    break;

                default:
                    System.out.println("\nНевірний вибір (╬▔皿▔)╯! Спробуйте ще раз.");
            }
        }
        skan.close();
    }

    private static void pokazatyHolovneMenu() {
        System.out.println("\n┌───────────────────────────────────────┐");
        System.out.println("│ МЕНЮЕЧКА РЕДАКТОРА ЛУПАШКИ   つ◕_◕ ༽つ │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│ 1. Записати до файлу                  │");
        System.out.println("│ 2. Прочитати файл                     │");
        System.out.println("│ 3. Вставити текст у рядок             │");
        System.out.println("│ 4. Вийти з програми                   │");
        System.out.println("└───────────────────────────────────────┘");
    }

    private static int otrymatiChyslo(Scanner skan, String povidomlennya) {
        System.out.print(povidomlennya);
        try {
            return Integer.parseInt(skan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\nНевірний вибір (╬▔皿▔)╯! Спробуйте ще раз, але вже число.");
            return 0;
        }
    }

    private static int pidrahuvatyRyadky() {
        int kilkist = 0;
        File fayl = new File(nazvaFaylu);

        if (!fayl.exists()) {
            return 0;
        }

        try (BufferedReader chytach = new BufferedReader(new FileReader(fayl))) {
            while (chytach.readLine() != null) {
                kilkist++;
            }
        } catch (IOException e) {
            System.out.println("Помилка при підрахунку рядків (╬▔皿▔)╯!: " + e.getMessage());
        }

        return kilkist;
    }

    private static void zapysDoFaylu(Scanner skan) {
        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ЗАПИС ДО ФАЙЛУ                      │");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("│1. Додати в кінець файлу            │");
        System.out.println("│2. Переписати файл повністю         │");
        System.out.println("└────────────────────────────────────┘");

        int rezhym = otrymatiChyslo(skan, "Оберіть режим (1/2): ");
        boolean dodatyDo = (rezhym == 1);

        int potochnyiRyadok = 1;
        if (rezhym == 1) {
            potochnyiRyadok = pidrahuvatyRyadky() + 1;
        } else if (rezhym != 2) {
            System.out.println("Невірний режим (╬▔皿▔)╯. Повертаємося до головного меню.");
            return;
        }

        System.out.println("\nВводьте текст (по одному рядку).");
        System.out.println("Для завершення введіть '" + komandaVyhodu + "'");

        try (BufferedWriter zapysuvach = new BufferedWriter(new FileWriter(nazvaFaylu, dodatyDo))) {
            while (true) {
                System.out.print(potochnyiRyadok +" ");
                String ryadok = skan.nextLine();

                if (ryadok.equals(komandaVyhodu)) {
                    break;
                }

                zapysuvach.write(ryadok);
                zapysuvach.newLine();
                potochnyiRyadok++;
            }
            System.out.println("\nТекст успішно записано до файлу (～￣▽￣)～!");
        } catch (IOException e) {
            System.out.println("Помилка запису до файлу (╬▔皿▔)╯: " + e.getMessage());
        }
    }

    private static void menuChytannya(Scanner skan) {

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ЧИТАННЯ ФАЙЛУ                       │");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("│1. Прочитати весь файл              │");
        System.out.println("│2. Прочитати діапазон рядків        │");
        System.out.println("└────────────────────────────────────┘");

        int vybir = otrymatiChyslo(skan, "Оберіть опцію (1/2): ");

        switch (vybir) {
            case 1:
                prochytatyVesFayl();
                break;

            case 2:
                prochytatyDiapazon(skan);
                break;

            default:
                System.out.println("Невірний вибір (╬▔皿▔)╯. Повертаємося до головного меню.");
        }
    }

    private static void prochytatyVesFayl() {
        File fayl = new File(nazvaFaylu);

        if (!fayl.exists()) {
            System.out.println("Файл не існує (╬▔皿▔)╯!");
            return;
        }

        System.out.println("\nВМІСТ ФАЙЛУ");

        try (BufferedReader chytach = new BufferedReader(new FileReader(fayl))) {
            String ryadok;
            int nomerRyadka = 1;
            boolean porozhniy = true;

            while ((ryadok = chytach.readLine()) != null) {
                System.out.println(String.format("%3d │ %s", nomerRyadka, ryadok));
                nomerRyadka++;
                porozhniy = false;
            }

            if (porozhniy) {
                System.out.println("(Файл порожній)");
            } else {
                System.out.println("\n✓ Загальна кількість рядків: " + (nomerRyadka - 1));
            }
        } catch (IOException e) {
            System.out.println("Помилка читання файлу (╬▔皿▔)╯: " + e.getMessage());
        }
    }

    private static void prochytatyDiapazon(Scanner skan) {
        int vsiohoRyadkiv = pidrahuvatyRyadky();

        if (vsiohoRyadkiv == 0) {
            System.out.println("Файл порожній або не існує (╬▔皿▔)╯!");
            return;
        }

        System.out.println("Всього рядків у файлі: " + vsiohoRyadkiv);
        int pochatkovyiRyadok = otrymatiChyslo(skan, "Введіть початковий рядок: ");
        int kintsevyiRyadok = otrymatiChyslo(skan, "Введіть кінцевий рядок: ");

        if (pochatkovyiRyadok <= 0 || kintsevyiRyadok <= 0 || pochatkovyiRyadok > kintsevyiRyadok) {
            System.out.println("Невірний діапазон рядків (╬▔皿▔)╯!");
            return;
        }

        if (pochatkovyiRyadok > vsiohoRyadkiv) {
            System.out.println("Початковий рядок перевищує кількість рядків у файлі (╬▔皿▔)╯!");
            return;
        }

        System.out.println("\nВМІСТ ФАЙЛУ (рядки " + pochatkovyiRyadok + " - " + kintsevyiRyadok + ")");

        try (BufferedReader chytach = new BufferedReader(new FileReader(nazvaFaylu))) {
            String ryadok;
            int nomerRyadka = 1;
            boolean znadeniRyadky = false;

            while (nomerRyadka < pochatkovyiRyadok && (ryadok = chytach.readLine()) != null) {
                nomerRyadka++;
            }

            while (nomerRyadka <= kintsevyiRyadok && (ryadok = chytach.readLine()) != null) {
                System.out.println(String.format("%3d %s", nomerRyadka, ryadok));
                nomerRyadka++;
                znadeniRyadky = true;
            }

            if (!znadeniRyadky) {
                System.out.println("(У зазначеному діапазоні немає рядків)");
            }
        } catch (IOException e) {
            System.out.println("Помилка читання файлу (╬▔皿▔)╯: " + e.getMessage());
        }
    }

    private static void vstavytyVRyadok(Scanner skan) {
        int vsiohoRyadkiv = pidrahuvatyRyadky();

        System.out.println("\nВСТАВКА ТЕКСТУ В РЯДОК");
        System.out.println("Поточна кількість рядків у файлі: " + vsiohoRyadkiv);

        int ryadokDlyaVstavky = otrymatiChyslo(skan,
                "Введіть номер рядка для вставки (1-" + (vsiohoRyadkiv + 1) + "): ");

        if (ryadokDlyaVstavky <= 0 || ryadokDlyaVstavky > vsiohoRyadkiv + 1) {
            System.out.println("Невірний номер рядка (╬▔皿▔)╯!");
            return;
        }

        String[] ryadkyFaylu = new String[maksRyadkiv];
        int faktychnaKilkistRyadkiv = 0;

        try (BufferedReader chytach = new BufferedReader(new FileReader(nazvaFaylu))) {
            String ryadok;
            while ((ryadok = chytach.readLine()) != null && faktychnaKilkistRyadkiv < ryadkyFaylu.length) {
                ryadkyFaylu[faktychnaKilkistRyadkiv] = ryadok;
                faktychnaKilkistRyadkiv++;
            }
        } catch (IOException e) {
            System.out.println("Помилка читання файлу (╬▔皿▔)╯: " + e.getMessage());
            return;
        }

        System.out.println("\nВводьте текст для вставки.");
        System.out.println("Для завершення введіть '" + komandaVyhodu + "'");

        String[] noviRyadky = new String[maksRyadkiv / 10];
        int kilkistNovyhRyadkiv = 0;

        while (true) {
            System.out.print("Новий рядок [" + (kilkistNovyhRyadkiv + 1) + "]: ");
            String vvedenyiRyadok = skan.nextLine();

            if (vvedenyiRyadok.equals(komandaVyhodu)) {
                break;
            }

            if (kilkistNovyhRyadkiv < noviRyadky.length) {
                noviRyadky[kilkistNovyhRyadkiv] = vvedenyiRyadok;
                kilkistNovyhRyadkiv++;
            } else {
                System.out.println("Досягнуто максимальної кількості нових рядків (╬▔皿▔)╯!");
                break;
            }
        }

        if (kilkistNovyhRyadkiv == 0) {
            System.out.println("Не введено жодного рядка (╬▔皿▔)╯. Операцію скасовано.");
            return;
        }

        String[] resultRyadky = new String[faktychnaKilkistRyadkiv + kilkistNovyhRyadkiv];
        int indexResultatu = 0;

        for (int i = 0; i < ryadokDlyaVstavky - 1 && i < faktychnaKilkistRyadkiv; i++) {
            resultRyadky[indexResultatu++] = ryadkyFaylu[i];
        }

        for (int i = 0; i < kilkistNovyhRyadkiv; i++) {
            resultRyadky[indexResultatu++] = noviRyadky[i];
        }

        for (int i = ryadokDlyaVstavky - 1; i < faktychnaKilkistRyadkiv; i++) {
            resultRyadky[indexResultatu++] = ryadkyFaylu[i];
        }

        try (BufferedWriter zapysuvach = new BufferedWriter(new FileWriter(nazvaFaylu))) {
            for (int i = 0; i < indexResultatu; i++) {
                zapysuvach.write(resultRyadky[i]);
                zapysuvach.newLine();
            }
            System.out.println("\n✓ Текст успішно вставлено в рядок " + ryadokDlyaVstavky + "!");
            System.out.println("  Загальна кількість рядків після вставки: " + indexResultatu);
        } catch (IOException e) {
            System.out.println("Помилка запису до файлу (╬▔皿▔)╯: " + e.getMessage());
        }
    }
}