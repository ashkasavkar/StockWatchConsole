package com.tradeconsole.swc.util;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class PasswordReader {
    public static String readPassword(String prompt) {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
            return lineReader.readLine(prompt, '*');
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String password = readPassword("Enter password: ");
        System.out.println("Your password is: " + password);
    }
}