package TpProg2.Util;

import TpProg2.ImplementOfUsers.Date;

public class Scanner {
    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);

    private Scanner() { }

    /** Displays the given message and waits for user to enter some text.
     * @param message to be displayed.
     * @return text entered by the user.
     */
    public static String getString(String message) {
        System.out.print(message);
        final String result = scanner.nextLine().trim();
        if(result.isEmpty()) {
            System.out.println("Please enter a text.");
            return getString(message);
        }
        return result;
    }

    /** Displays the given message and waits for user to enter a character.
     * @param message to be displayed.
     * @return char entered by the user.
     */
    public static char getChar(String message) {
        return getString(message).charAt(0);
    }

    /** Displays the given message and waits for user to enter an int.
     * @param message to be displayed.
     * @return integer entered by the user.
     */
    public static int getInt(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer.");
            return getInt(message);
        }
    }

    /** Displays the given message and waits for user to enter a long.
     * @param message to be displayed.
     * @return long entered by the user.
     */
    public static long getLong(String message) {
        System.out.print(message);
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a long.");
            return getLong(message);
        }
    }

    /** Displays the given message and waits for user to enter a float.
     * @param message to be displayed.
     * @return float entered by the user.
     */
    public static float getFloat(String message) {
        System.out.print(message);
        try {
            return Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a float.");
            return getFloat(message);
        }
    }

    /** Displays the given message and waits for user to enter a double.
     * @param message to be displayed.
     * @return double entered by the user.
     */
    public static double getDouble(String message) {
        System.out.print(message);
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a double.");
            return getDouble(message);
        }
    }

    //Particulares

    public static int getDate(String message) {
        System.out.print(message);
        try {
            int date = Integer.parseInt(scanner.nextLine());
            if (String.valueOf(date).length() > 2 || date < 1){
                System.out.println("  (Ingrese 1 o 2 digitos para la fecha)");
                return getDate(message);
            }else{return date;}
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer.");
            return getInt(message);
        }
    }

    public static int getMonth(String message) {
        System.out.print(message);
        try {
            int date = Integer.parseInt(scanner.nextLine());
            if (date < 1 || date > 12){
                System.out.println("  (Ingrese un valor entre 1 y 12 para el mes)");
                return getMonth(message);
            }else{return date;}
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer.");
            return getInt(message);
        }
    }

    public static int getDay(String message, int year, int month) {
        System.out.print(message);
        int monthDays = Date.monthDays(year, month);
        try {
            int date = Integer.parseInt(scanner.nextLine());
            if (date < 1 || date > monthDays){
                System.out.println("  (Ingrese un valor entre 1 y "+ monthDays+" para el mes)");
                return getDay(message, year, month);
            }else{return date;}
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer.");
            return getInt(message);
        }
    }

    public static int getHour(String message) {
        System.out.print(message);
        try {
            int date = Integer.parseInt(scanner.nextLine());
            if (date < 1 || date > 24){
                System.out.println("  (Ingrese un valor entre 1 y 24 para la hora)");
                return getHour(message);
            }else{return date;}
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer.");
            return getInt(message);
        }
    }

    public static void enter(){
        scanner.nextLine();
    }

    public static void enter(String message){
        System.out.println(message);
        scanner.nextLine();
    }
}
