import enums.MyFiles;
import enums.ReturnStr;
import enums.InputVar;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Features {
    public static void writeToFile(Player player1, Player player2) {
        File resFile = new File(MyFiles.RESULTS.toString());
        Date dateNow = new Date();
        SimpleDateFormat formatDateNow = new SimpleDateFormat("dd.MM.yyyy (E)', время' HH.mm");
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(resFile,true))) {
           // printWriter.write("Игра " + (formatDateNow.format(dateNow)));
           // printWriter.append(String.format("Игрок %s - %d выстрелов, %d попаданий\n", player1.getName(), player1.countOfGuess, player1.countOfHitsAll));
           // printWriter.append(String.format("Игрок %s - %d выстрелов, %d попаданий\n", player2.getName(), player2.countOfGuess, player2.countOfHitsAll));
            printWriter.println("Игра " + formatDateNow.format(dateNow));
            printWriter.printf("Игрок %s - %d попыток, %d попаданий\n", player1.getName(), player1.countOfGuess, player1.countOfHitsAll);
            printWriter.printf("Игрок %s - %d попыток, %d попаданий\n", player2.getName(), player2.countOfGuess, player2.countOfHitsAll);;
            printWriter.println("---");
            System.out.println("Результаты сохранены в файл " + MyFiles.RESULTS);
        } catch (IOException e) {
            System.out.println("не найден файл для записи");
        }

    }

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
        return guess.matches("^[а-яА-Яa-zA-Z0-9._-]{1,12}$");
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
        return (Board.ALPHABET.substring(0, Board.WIDTH).contains(charGuess) && (numGuess > 0 && numGuess <= Board.HEIGHT))
                || guessString.equals(ReturnStr.QUIT.toString())
                || guessString.equals(ReturnStr.SHOW.toString());
    }


}