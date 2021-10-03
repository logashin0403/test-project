package data;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseHandler {

    private static final String configPath = new File("").getAbsolutePath().concat("\\src\\main\\java\\data\\database.properties");
    private static final Logger logger = Logger.getLogger(DatabaseHandler.class);

    public static void addResultToDB(String query) {
        Properties properties = new Properties();

        //подгрузка проперти файла
        try (FileInputStream fileInputStream = new FileInputStream(configPath)) {
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось найти файл конфигураций базы данных");
            logger.error(e.getMessage());
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Произошла ошибка связанная с вводом или выводом данных");
            logger.error(e.getMessage());
            System.exit(-1);
        }

        //основная часть работы с БД
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            System.out.println("Произошла ошибка с драйвером базы данных");
            logger.error(e.getMessage());
            System.exit(-1);
        }

        try (Connection connection = DriverManager.getConnection(properties.getProperty("url"),
                                                                 properties.getProperty("username"),
                                                                 properties.getProperty("password"))) {

            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (Exception e) {
            System.out.println("Произошла ошибка связанная с записью данных в базу данных");
            logger.error(e.getMessage());
            System.exit(-1);
        }
    }
}
