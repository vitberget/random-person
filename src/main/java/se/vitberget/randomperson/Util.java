package se.vitberget.randomperson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Util {
    static public Properties getProperties() {
        try(FileInputStream fis = new FileInputStream("random-person.properties")) {

            Properties properties = new Properties();
            properties.load(fis);
            return properties;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
