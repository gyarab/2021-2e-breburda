package objects;

import java.util.Objects;
import java.util.Scanner;

public class MyScanner {
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
            System.out.println("Zadejte hodnotu znova");
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
            System.out.println("Zadejte hodnotu znova");
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
            System.out.println("Zadejte hodnotu znova");
            return nextInt(title, info);
        }
    }
}
