import java.util.Scanner;
import java.util.Stack;
import java.util.EmptyStackException;

public class MoveFromBaseToBase extends TurnOption{
    private PlayField pf;
    private Scanner in = new Scanner(System.in);
    private gameRules gr = new gameRules();

    public MoveFromBaseToBase(PlayField pf){
        this.pf = pf;
    }

    @Override
    public void MakeTurn(String[] cardNames) {
        System.out.print("Choose which card to put: ");
        String cardName = in.nextLine();
        if (cardName.length() < 2) {System.out.println("\nIncorrect value"); return;}
        int sourseCard = getValueByCardName(cardNames, cardName);
        Stack<Integer> stackToMove = new Stack<Integer>();
        try {
            stackToMove = getStack(sourseCard);
        }
        catch (EmptyStackException e){
            System.out.println("\nThis action cannot be performed");
            return;
        }
        int freeStack = getEmptyStack();
        if ((freeStack != -1) && (sourseCard % 13 == 0)){
            int size = stackToMove.size();
            for (int i = 0; i < size; i++) {
                int tmp = stackToMove.pop();
                RemoveCardFromBase(tmp);
                pf.baseStack[size - i - 1][freeStack] = tmp;
            }
            pf.turnCount++;
            System.out.println("\nSuccessfully");
            return;
        }

        System.out.print("Choose which card to put on: ");
        cardName = in.nextLine();
        if (cardName.length() < 2) {System.out.println("\nIncorrect value"); return;}
        int targetCard = getValueByCardName(cardNames, cardName);

        if (gr.possibleToPutOn(sourseCard, targetCard) && gr.baseCardAtTheTop(pf, targetCard)) {
            PutTheStackOn(stackToMove, targetCard);
            pf.turnCount++;
            System.out.println("\nSuccessfully");
        }
        else System.out.println("\nThis action cannot be performed");
    }

    private int getValueByCardName(String[] cardNames, String cardName) {
        for (int i = 0; i < cardNames.length; i++) {
            if ((cardNames[i].charAt(0) == cardName.charAt(0)) && (cardNames[i].charAt(1) == cardName.charAt(1)))
                return (i + 1);
        }
        return 0;
    }

    private Stack<Integer> getStack(int card){
        Stack<Integer> queueStack = new Stack<Integer>();
        for (int i = 0; i < 25; i++){
            for (int j = 0; j < 7; j++){
                if (pf.baseStack[i][j] == card){
                    BuildStack(i, j, queueStack);
                    return queueStack;
                }
            }
        }
        throw new EmptyStackException();
    }

    private void BuildStack(int i, int j, Stack<Integer> stack){
        if (gr.cardIsMoveable(pf, pf.baseStack[i][j])){
            stack.push(pf.baseStack[i][j]);
            if (pf.baseStack[i+1][j] == 0) return;
            if (i+1 < 25) BuildStack(i+1, j, stack);
        }
        else throw new EmptyStackException();
    }

    private void PutTheStackOn(Stack<Integer> stack, int card){
        int[] cardPos = new int[2];
        for (int i = 0; i < 26; i++){
            for (int j = 0; j < 7; j++){
                if (pf.baseStack[i][j] == card){
                    cardPos[0] = i; cardPos[1] = j; break;
                }
            }
        }
        int size = stack.size();
        for (int i = 0; i < size; i++){
                int tmp = stack.pop();
                RemoveCardFromBase(tmp);
                pf.baseStack[size+cardPos[0]-i][cardPos[1]] = tmp;
        }
    }

    private void RemoveCardFromBase(int card){
        for (int i = 0; i < 26; i++){
            for (int j = 0; j < 7; j++){
                if (pf.baseStack[i][j] == card){
                    pf.baseStack[i][j] = 0;
                }
            }
        }
    }

    private int getEmptyStack(){
        for (int i = 0; i < 7; i++){
            if (pf.baseStack[0][i] == 0) return i;
        }
        return -1;
    }
}
