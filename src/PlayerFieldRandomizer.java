public class PlayerFieldRandomizer {
    private PlayField pf = new PlayField();

    private PlayField RandomizePlayField() {
        for (int i = 0; i < 52; i++){
            pf.sourseDeck[i] = true;
        }
        FillBaseStack();
        FillFreeStack();
        return pf;
    }

    private void FillBaseStack() {
        for (int i = 0; i < 7; i++){
            for (int j = 6; j > i-1; j--){
                int tmpCard = RndCard();
                if (j == i) {
                    pf.baseStack[i][j] = tmpCard+1;
                    continue;
                }

                pf.baseStack[i][j] = tmpCard+1;
            }
        }
    }

    private void FillFreeStack(){
        for (int i = 0; i < 24; i++){
            int tmpCard = RndCard();
            pf.freeStack[i] = tmpCard+1;
        }
    }

    private int RndCard() {
        int rndNum = (int) (Math.random() * 52);
        if (pf.sourseDeck[rndNum]) {
            pf.sourseDeck[rndNum] = false;
            return rndNum;
        } else return RndCard();
    }

    public PlayField getNewPlayField() {
        return RandomizePlayField();
    }
}
