import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Features {
    //ввод данных с клавиатуры - координату выстрела
    static String getUserInput(String prompt) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        String guess = "";
        while (true) {
            guess = scanner.nextLine().toLowerCase();
            switch (guess) {
                case ("q"):
                    return "q";
                case ("v"):
                    return "v";
                default:
                    if (!checkGuessStr(guess)) {
                        System.out.println("Некорректный ввод координаты. " + prompt);
                    } else {
                        return guess;
                    }
            }
        }
    }

    //проверяет валидность ввода координаты выстрела, должна быть строка вида "A1" ... "J10"
    static boolean checkGuessStr(String guessString) {
        if (guessString.length() <= 3 && guessString.substring(1).matches("\\d+")) {
            String a = guessString.substring(0, 1);
            int b = Integer.parseInt(guessString.substring(1));
            if (Board.ALPHABET.contains(a) && (b > 0 && b <= Board.HEIGHT)) {
                return true;
            }
        }
        return false;
    }

}