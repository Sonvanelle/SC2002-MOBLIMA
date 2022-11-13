package utils;

import java.io.*;

public class SerializeObjects {

    public static void saveData(String filepath, Object object) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(object);
            objectOut.close();
        } catch (Exception e) {
            System.out.println("Got an error while saving cineplexes data: " + e);
            // e.printStackTrace();
        }
    }

    public static Object loadData(String filepath) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (Exception e) {
            System.out.println("Got an error while loading cineplexes data: " + e);
            // e.printStackTrace();
            return null;
        }
    }
}