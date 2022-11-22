import enums.ReturnStr;
import enums.InputVar;

import java.util.Scanner;

public class Features {
    //ввод данных с клавиатуры - координату выстрела
    static String getUserInput(String message, InputVar inputVar) {
        System.out.println(message);
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
            if (inputVar.equals(InputVar.GAME_TYPE)) {
                isCorrectInput = checkGameType(guess);
            }
            if (isCorrectInput) {
                return guess;
            }
            System.out.println("Некорректный ввод. " + message);
        }
    }


    private static boolean checkGameType(String variant) {
        return (variant.equals(ReturnStr.HUMAN_GAME.toString()) || variant.equals(ReturnStr.COMP_GAME.toString()));
    }

    private static boolean checkNameString(String guess) {
        return guess.matches("^[а-яА-Яa-zA-Z0-9._-]{1,20}$");
    }

    //проверяет валидность ввода координаты выстрела, должна быть строка вида "A1" ... "J10"
    //также проверяет не введен ли выход из игры
    private static boolean checkGuessString(String guessString) {
        String charGuess = "";
        int numGuess = 0;
        //todo улучшить проверку через регулярные выражения
        if (guessString.length() <= 3 && guessString.substring(1).matches("\\d+")) {
            charGuess = guessString.substring(0, 1);
            numGuess = Integer.parseInt(guessString.substring(1));
        }
        return (Board.ALPHABET.substring(0,Board.WIDTH).contains(charGuess) && (numGuess > 0 && numGuess <= Board.HEIGHT))
                || guessString.equals(ReturnStr.QUIT.toString())
                || guessString.equals(ReturnStr.SHOW.toString());
    }
}