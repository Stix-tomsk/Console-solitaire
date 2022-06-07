import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the most inconvenient console solitaire!");
        GameProc();
        try {in.nextInt();} catch (InputMismatchException e) {}
    }

    public static void GameProc() {
        System.out.print("Input your name: ");
        MainGameProc myGame = new MainGameProc(in.nextLine());
        gameRules gr = new gameRules();
        //MainGameProc myGame = new MainGameProc("asd");
        myGame.GameStart();
        myGame.ShowCards();
        boolean gameInProc = true;
        while (gameInProc){
            System.out.print("The game options:\n1 - make a move\n2 - surrender\nYour choise: ");
            byte playerChoise = 0;
            try {
                playerChoise = in.nextByte();
            }
            catch (InputMismatchException e) {System.out.println("It was a mistake, bye"); break;}
            switch (playerChoise) {
                case(1):
                    myGame.Turn();
                    if (gr.CheckGameOvering(myGame.playField)) myGame.GameFinish(true);
                    break;
                case(2):
                    System.out.print("Are you sure?(1/0) ");
                    byte answer = 0;
                    try {
                        answer = in.nextByte();
                    }
                    catch (InputMismatchException e) {}
                    if (answer == 1) {
                        gameInProc = false;
                        myGame.GameFinish(false);
                    }
                    break;
                default:
                    System.out.println("Incorrect option, please try again");
                    break;
            }
        }
    }
}
