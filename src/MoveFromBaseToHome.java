import java.util.Scanner;

public class MoveFromBaseToHome extends TurnOption{
    private PlayField pf;
    private Scanner in = new Scanner(System.in);

    public MoveFromBaseToHome(PlayField pf){
        this.pf = pf;
    }

    @Override
    public void MakeTurn(String[] cardNames) {
        System.out.print("Choose which card to put: ");
        String cardName = in.nextLine();
        if (cardName.length() < 2) {System.out.println("\nIncorrect value"); return;}
        int sourseCard = getValueByCardName(cardNames, cardName);
        gameRules gr = new gameRules();
        if (gr.canBeBackHome(pf, sourseCard) && gr.baseCardAtTheTop(pf, sourseCard)) {
            PutTheCardOn(sourseCard);
            RemoveCardFromBase(sourseCard);
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

    private void RemoveCardFromBase(int card){
        for (int i = 0; i < 26; i++){
            for (int j = 0; j < 7; j++){
                if (pf.baseStack[i][j] == card){
                    pf.baseStack[i][j] = 0;
                }
            }
        }
    }

    private void PutTheCardOn(int card){
        int suit = (int)((card-1)/13);

        for (int i = 0; i < 14; i++){
            if (pf.homeStack[i][suit] == 0){
                pf.homeStack[i][suit] = card;
                break;
            }
        }
    }
}
