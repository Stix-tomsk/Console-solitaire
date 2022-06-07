import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.SECONDS;

public class MainGameProc implements IGameProc {
    private Scanner in = new Scanner(System.in);
    private gameRules gr = new gameRules();
    private LocalTime playStartTime;
    public PlayField playField;
    private final String playerName;


    public MainGameProc(String name) {
        this.playerName = name;
    }

    public void GameStart(){
        System.out.println("Game was started, " + playerName);
        PlayerFieldRandomizer pfr = new PlayerFieldRandomizer();
        playField = pfr.getNewPlayField();
        playStartTime = LocalTime.now();

    }
    public void Turn(){
        System.out.println("\nThe turn options:\n1 - from free to home stack\n2 - from free to base stack\n3 - from base to base stack\n4 - from base to home stack\n5 - from home to base stack\n6 - show new card from free stack");
        System.out.print("Your choise: ");
        TurnOption curTurn;
        byte playerChoise = 0;
        try {
            playerChoise = in.nextByte();
        }
        catch (InputMismatchException e) {}
        switch (playerChoise) {
            case(1):
                if (!gr.FreeStackIsEmpty(playField.freeStack)) curTurn = new MoveFromFreeToHome(playField);
                else {System.out.println("\nThis action cannot be performed");curTurn = null;}
                break;
            case(2):
                if (!gr.FreeStackIsEmpty(playField.freeStack)) curTurn = new MoveFromFreeToBase(playField);
                else {System.out.println("\nThis action cannot be performed");curTurn = null;}
                break;
            case(3):
                curTurn = new MoveFromBaseToBase(playField);
                break;
            case(4):
                curTurn = new MoveFromBaseToHome(playField);
                break;
            case(5):
                curTurn = new MoveFromHomeToBase(playField);
                break;
            case(6):
                if (!gr.FreeStackIsEmpty(playField.freeStack)) curTurn = new ShowNewCardFromFree(playField);
                else {System.out.println("\nThis action cannot be performed");curTurn = null;}
                break;
            default:
                System.out.println("Incorrect option, please try again");
                curTurn = null;
                break;
        }
        TurnProc(curTurn);
    }
    public void GameFinish(boolean gameResult){
        System.out.println("----------------------------");
        if (gameResult) System.out.println("Congratulations on your victory, " + playerName + "!!");
        else System.out.println("You're better luck next time, " + playerName + "!");
        System.out.println(String.format("Moves: %d", playField.turnCount));
        System.out.println("Game time: " + getTimeInGame());
        System.out.println("----------------------------");
    }
    private final String[] cardNames = {"BA ", "B2 ", "B3 ", "B4 ", "B5 ", "B6 ", "B7 ", "B8 ", "B9 ", "B10", "BJ ", "BQ ", "BK ",
            "PA ", "P2 ", "P3 ", "P4 ", "P5 ", "P6 ", "P7 ", "P8 ", "P9 ", "P10", "PJ ", "PQ ", "PK ",
            "CA ", "C2 ", "C3 ", "C4 ", "C5 ", "C6 ", "C7 ", "C8 ", "C9 ", "C10", "CJ ", "CQ ", "CK ",
            "TA ", "T2 ", "T3 ", "T4 ", "T5 ", "T6 ", "T7 ", "T8 ", "T9 ", "T10", "TJ ", "TQ ", "TK ",
    };

    public void ShowCards(){
        System.out.println("----------------------------");
        /*for (int i = 0; i < 24; i++) {
            if (playField.freeStack[i] != 0) {
                System.out.print(cardNames[playField.freeStack[i]-1]);System.out.print(" ");
            }
            else System.out.print(" 0 ");
        }*/
        System.out.println(String.format("Moves: %d", playField.turnCount));
        if (!gr.FreeStackIsEmpty(playField.freeStack)) System.out.print(cardNames[playField.freeStack[playField.freeStackCardToShow]-1]);
        else System.out.print("--");
        System.out.print("              ");
        int[] homeStackToShow = GetTheTopOfHomeStack();
        for (int i = 0; i < 4; i++){
            if (homeStackToShow[i] == 0) {
                System.out.print("- ");
                continue;
            }
            System.out.print(cardNames[playField.homeStack[homeStackToShow[i]-1][i]-1] + " ");
        }
        System.out.println("\n----------------------------");
        for (int i = 0; i < 26; i++){
            if (CheckDeep(i)) break;
            for (int j = 0; j < 7; j++){
                if (i == 0 && playField.baseStack[i][j] == 0){
                    System.out.print("--  ");
                    continue;
                }
                if (playField.baseStack[i][j] == 0){
                    System.out.print("    ");
                    continue;
                }
                System.out.print(cardNames[playField.baseStack[i][j]-1] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------\n");

    }

    private void TurnProc(TurnOption curTurn) {
        if (curTurn != null)  {
            curTurn.MakeTurn(cardNames);
            ShowCards();
        }
    }

    private int[] GetTheTopOfHomeStack(){
        int[] tmp = new int[4];
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 4; j++){
                if (playField.homeStack[i][j] != 0) tmp[j] = i+1;
            }
        }
        return tmp;
    }

    private boolean CheckDeep(int row){
        for (int i = 0; i < 7; i++){
            if (playField.baseStack[row][i] !=0) return false;
        }
        return true;
    }

    private String getTimeInGame(){
        LocalTime timeNow = LocalTime.now();
        long sec = SECONDS.between(playStartTime, timeNow);
        long hours = (long)(sec/3600);
        sec -= hours * 3600;
        long min = (long)(sec/60);
        sec -= min * 60;

        return (String.format("%d:%d:%d", hours, min, sec));
    }
}
