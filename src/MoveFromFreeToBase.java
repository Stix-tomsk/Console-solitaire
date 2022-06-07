import java.util.Scanner;

public class MoveFromFreeToBase extends TurnOption{
    private PlayField pf;
    private Scanner in = new Scanner(System.in);

    public MoveFromFreeToBase(PlayField pf){
        this.pf = pf;
    }

    @Override
    public void MakeTurn(String[] cardNames) {
        int freeStack = getEmptyStack();
        if ((freeStack != -1) && (pf.freeStack[pf.freeStackCardToShow] % 13 == 0)){
            pf.baseStack[0][freeStack] = pf.freeStack[pf.freeStackCardToShow];
            pf.freeStack[pf.freeStackCardToShow] = 0;
            ShowNewCardFromFree snc = new ShowNewCardFromFree(pf);
            snc.MakeTurnBack();
            pf.turnCount++;
            System.out.println("\nSuccessfully");
            return;
        }

        System.out.print("Choose which card to put on: ");
        String cardName = in.nextLine();
        if (cardName.length() < 2) {System.out.println("\nIncorrect value"); return;}
        int targetCard = getValueByCardName(cardNames, cardName);
        gameRules gr = new gameRules();
        if (gr.possibleToPutOn(pf.freeStack[pf.freeStackCardToShow], targetCard) && gr.baseCardAtTheTop(pf, targetCard)) {
            PutTheCardOn(pf.freeStack[pf.freeStackCardToShow], targetCard);
            pf.freeStack[pf.freeStackCardToShow] = 0;
            ShowNewCardFromFree snc = new ShowNewCardFromFree(pf);
            snc.MakeTurnBack();
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

    private void PutTheCardOn(int frstCard, int sndCard){
        for (int i = 0; i < 26; i++){
            for (int j = 0; j < 7; j++){
                if (pf.baseStack[i][j] == sndCard){
                    pf.baseStack[i+1][j] = frstCard; return;
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
