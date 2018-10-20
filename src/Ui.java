import java.util.Scanner;

public class Ui {
    private static Scanner _in = new Scanner(System.in);
    Ui(){
    }
    public void showWelcomeMessage(){
        System.out.println("Welcome to TaskManager-Level1!");
    }
    public void printError(){
        System.out.println("Unexpected Error Ocurred! Please try again");
    }
    public static void printError(String e){
        System.out.println(e);
    }
    public static String getInput(){
        return _in.nextLine();
    }
    public static void showToUser(String show){
        System.out.println(show);
    }


}
