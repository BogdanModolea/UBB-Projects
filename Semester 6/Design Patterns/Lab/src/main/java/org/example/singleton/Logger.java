package org.example.singleton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Logger {
    private static OutputStream os;
    private static Logger logger;

    private Logger() {
        initOS();
    }

    private void initOS() {
        try {
            os = new FileOutputStream("logs.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void log(String data) {
        if (os == null)
            initOS();
        try {
            data += "\n";
            Logger.os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstanceOf() {
        if (Logger.logger == null) {
            Logger.logger = new Logger();
        }

        return Logger.logger;
    }
}
