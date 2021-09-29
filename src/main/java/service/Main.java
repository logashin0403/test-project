package service;

import data.DatabaseHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);
    private static final String pathToLoggerProperty = new File("").getAbsolutePath().concat("\\src\\main\\resources\\log4j\\log4j.properties");

    public static void main(String[] args) {
        PropertyConfigurator.configure(pathToLoggerProperty);
        logger.info("Начало работы программы");
        System.out.print("Введите путь до файла: ");
        Scanner scanner = new Scanner(System.in);

        String finishedQueryForDB = null;
        try {
            //вызов функции чтения из файла
            logger.info("Вызов Main.readInformationFromFile");
            StringBuilder stringFromFile = readInformationFromFile(scanner.nextLine());
            //вызов функций очистки
            logger.info("Вызов CleanData.cleanFromUnwantedSymbol");
            StringBuilder stringToOutput = CleanData.cleanFromUnwantedSymbol(stringFromFile);

            //вызов функции вывода данных и создание запроса для бд
            logger.info("Вызов PrepareDataForOutput.makeMapFromAllWords");
            finishedQueryForDB = PrepareDataForOutput.makeMapFromAllWords(stringToOutput.toString().toUpperCase());

        } catch (Exception e) {
            System.out.println("Произошла системная ошибка в препроцессинге данных");
            logger.error(e.getMessage());
            System.exit(-1);
        }

        System.out.println("\nТакже сейчас происходит запись результата в базу данных...");

        DatabaseHandler.addResultToDB(finishedQueryForDB);

        System.out.println("\nЗапись в базу данных прошла успешно");
        logger.info("Конец работы программы");
    }

    private static StringBuilder readInformationFromFile(String path) {
        StringBuilder textFromFile = new StringBuilder();

        try (
                FileInputStream inputStream = new FileInputStream(path);
                Scanner scanner = new Scanner(inputStream, "UTF-8")
            ) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                textFromFile.append(line);
            }

        } catch (IOException e) {
            System.out.println("Произошла ошибка в чтении файлов");
            logger.error(e.getMessage());
            System.exit(-1);
        }

        return textFromFile;
    }
}
