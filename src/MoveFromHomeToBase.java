import java.util.Scanner;

public class MoveFromHomeToBase extends TurnOption {
    private PlayField pf;
    private Scanner in = new Scanner(System.in);

    public MoveFromHomeToBase(PlayField pf){
        this.pf = pf;
    }

    @Override
    public void MakeTurn(String[] cardNames) {
        System.out.print("Choose which card to put on: ");
        String cardName = in.nextLine();
        if (cardName.length() < 2) {System.out.println("\nIncorrect value"); return;}
        int targetCard = getValueByCardName(cardNames, cardName);
        int[] cardOptions = getCardFromHome(targetCard);
        gameRules gr = new gameRules();
        if (gr.baseCardAtTheTop(pf, targetCard)){
            if (gr.possibleToPutOn(cardOptions[0], targetCard)){
                PutTheCardOn(cardOptions[0], targetCard);
                RemoveCardFromHome(cardOptions[0]);
                pf.turnCount++;
                System.out.println("\nSuccessfully");
            }
            else if (gr.possibleToPutOn(cardOptions[1], targetCard)){
                PutTheCardOn(cardOptions[1], targetCard);
                RemoveCardFromHome(cardOptions[1]);
                pf.turnCount++;
                System.out.println("\nSuccessfully");
            }
            else System.out.println("\nThis action cannot be performed");
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

    private int[] getCardFromHome(int baseCard){
        int[] tmp = new int[4];
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 4; j++){
                if (pf.homeStack[i][j] != 0) tmp[j] = i+1;
            }
        }

        if ((int)((baseCard-1)/13) % 2 == 0){
            int[] res = new int[2];
            if (tmp[1] == 0) res[0] = 0;
            else res[0] = pf.homeStack[tmp[1]-1][1];
            if (tmp[3] == 0) res[1] = 0;
            else res[1] = pf.homeStack[tmp[3]-1][3];
            return res;
        }
        else {
            int[] res = new int[2];
            if (tmp[0] == 0) res[0] = 0;
            else res[0] = pf.homeStack[tmp[0]-1][0];
            if (tmp[2] == 0) res[1] = 0;
            else res[1] = pf.homeStack[tmp[2]-1][2];
            return res;
        }
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

    private void RemoveCardFromHome(int card){
        int suit = (int)((card-1)/13);
        for (int i = 0; i < 13; i++){
            if (pf.homeStack[i][suit] == card){
                pf.homeStack[i][suit] = 0;
            }
        }
    }
}

