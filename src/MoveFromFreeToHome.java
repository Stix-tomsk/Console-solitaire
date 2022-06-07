public class MoveFromFreeToHome extends TurnOption{
    private PlayField pf;

    public MoveFromFreeToHome(PlayField pf){
        this.pf = pf;
    }

    @Override
    public void MakeTurn(String[] cardNames) {
        gameRules gr = new gameRules();
        if (gr.canBeBackHome(pf, pf.freeStack[pf.freeStackCardToShow])) {
            PutTheCardOn();
            pf.freeStack[pf.freeStackCardToShow] = 0;
            ShowNewCardFromFree snc = new ShowNewCardFromFree(pf);
            snc.MakeTurnBack();
            pf.turnCount++;
            System.out.println("\nSuccessfully");
        }
        else System.out.println("\nThis action cannot be performed");
    }

    private void PutTheCardOn(){
        int suit = (int)((pf.freeStack[pf.freeStackCardToShow]-1)/13);

        for (int i = 0; i < 14; i++){
            if (pf.homeStack[i][suit] == 0){
                pf.homeStack[i][suit] = pf.freeStack[pf.freeStackCardToShow];
                break;
            }
        }
    }
}
