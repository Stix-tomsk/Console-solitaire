public class ShowNewCardFromFree extends TurnOption{
    private PlayField pf;

    public ShowNewCardFromFree(PlayField pf){
        this.pf = pf;
    }

    @Override
    public void MakeTurn(String[] cardNames) {
        pf.freeStackCardToShow++;
        if (pf.freeStackCardToShow == 24) pf.freeStackCardToShow = 0;
        if (pf.freeStack[pf.freeStackCardToShow] == 0) SkipVoid(true);
        pf.turnCount++;
    }

    public void MakeTurnBack(){
        pf.freeStackCardToShow--;
        if (pf.freeStackCardToShow == -1) pf.freeStackCardToShow = 23;
        if (pf.freeStack[pf.freeStackCardToShow] == 0) SkipVoid(false);
    }

    private void SkipVoid(boolean dir){
        gameRules gr = new gameRules();
        if ((pf.freeStack[pf.freeStackCardToShow] == 0) && (!gr.FreeStackIsEmpty(pf.freeStack))) {
            if (dir) {
                pf.freeStackCardToShow++;
                if (pf.freeStackCardToShow == 24) pf.freeStackCardToShow = 0;
            }
            else{
                pf.freeStackCardToShow--;
                if(pf.freeStackCardToShow == -1) pf.freeStackCardToShow = 23;
            }
            SkipVoid(dir);
        }
        else return;
    }

}
