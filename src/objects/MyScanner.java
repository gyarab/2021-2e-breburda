package objects;

import java.util.Objects;
import java.util.Scanner;

public class MyScanner {
    public static String reset = "\u001B[0m";
    public static String green = "\u001B[32m";
    Scanner sc = new Scanner(System.in);

    public String next(String title, String info) {
        try {
            System.out.println(title);
            String val = sc.next();

            if (Objects.equals(val, "info")) {
                System.out.println(info);
                System.out.println(title);
                val = sc.next();
            }

            return val;
        } catch (Exception ex) {
            System.out.println("Try again");
            return next(title, info);
        }
    }
    public String nextLine(String title, String info) {
        try {
            System.out.println(title);
            String line = sc.nextLine();

            while (line.equals("")) {
                line = sc.nextLine();
            }

            if (Objects.equals(line, "info")) {
                System.out.println(info);
                System.out.println(title);
                line = sc.nextLine();
            }

            return line;
        } catch (Exception ex) {
            System.out.println("Try again");
            return nextLine(title, "");
        }
    }
    public int nextInt(String title, String info) {
        try {
            System.out.print(title);
            String intos = sc.next();

            if (Objects.equals(intos, "info")){
                System.out.println(info);
                System.out.println(title);
                intos = sc.next();
            }

            return Integer.parseInt(intos);
        } catch (Exception ex) {
            System.out.println(green + "Try again; for help type: \"info\"" + reset);
            return nextInt(title, info);
        }
    }
}
