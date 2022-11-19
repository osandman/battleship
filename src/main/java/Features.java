import enums.InputVar;

import java.util.Scanner;

public class Features {
    //ввод данных с клавиатуры - координату выстрела
    static String getUserInput(String prompt, InputVar inputVar) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        String guess = "";
        boolean isCorrectInput = false;
        while (true) {
            guess = scanner.nextLine();
            if (inputVar.equals(InputVar.HIT_GUESS)) {
                guess = guess.toLowerCase();
                isCorrectInput = checkGuessString(guess);
            }
            if (inputVar.equals(InputVar.NAME_INPUT)) {
                isCorrectInput = checkNameString(guess);
            }
            if (isCorrectInput) {
                return guess;
            }
            System.out.println("Некорректный ввод. " + prompt);
        }
    }

    private static boolean checkNameString(String guess) {
        return guess.matches("^[а-яА-Яa-zA-Z0-9._-]{3,20}$");
    }

    //проверяет валидность ввода координаты выстрела, должна быть строка вида "A1" ... "J10"
    //также проверяет не введен ли выход из игры
    private static boolean checkGuessString(String guessString) {
        String quitStr = "q";
        String charInput = "";
        int numInput = 0;
        //todo улучшить проверку через регулярные выражения
        if (guessString.length() <= 3 && guessString.substring(1).matches("\\d+")) {
            charInput = guessString.substring(0, 1);
            numInput = Integer.parseInt(guessString.substring(1));
        }
        return (Board.ALPHABET.contains(charInput) && (numInput > 0 && numInput <= Board.HEIGHT)) ||
                guessString.equals(quitStr);
    }
}