public class gameRules {
    public boolean possibleToPutOn(int frstCard, int sndCard){
        int frstSuit = (int)((frstCard-1) / 13);
        int sndSuit = (int)((sndCard-1) / 13);
        int frstValue = (int)((frstCard-1) % 13);
        int sndValue = (int)((sndCard-1) % 13);

        if ((sndValue - frstValue == 1) && (((frstSuit % 2 == 0) && (sndSuit % 2 != 0)) || ((frstSuit % 2 != 0) && (sndSuit % 2 == 0)))) {
            return true;
        }

        return false;
    }

    public boolean baseCardAtTheTop(PlayField pf, int card) {
        int cardI = -1;
        int cardJ = -1;
        for (int i = 0; i < 25; i++){
            for (int j = 0; j < 7; j++){
                if (pf.baseStack[i][j] == card){
                    cardI = i; cardJ = j; break;
                }
            }
        }

        if ((cardI != -1) && (cardJ != -1) && (pf.baseStack[cardI+1][cardJ] == 0)) {
            return true;
        }

        return false;
    }

    public boolean canBeBackHome(PlayField pf, int sourseCard){
        int suit = (int)((sourseCard-1)/13);
        int targetCard = 0;

        for (int i = 0; i < 13; i++){
            if (pf.homeStack[i][suit] == 0) break;
            targetCard = pf.homeStack[i][suit];
        }

        if ((sourseCard - targetCard == 1) || ((targetCard == 0) && (sourseCard % 13 == 1))) return true;
        return false;
    }

    public boolean cardIsMoveable(PlayField pf, int card){
        for (int i = 0; i < 25; i++){
            for (int j = 0; j < 7; j++){
                if (pf.baseStack[i][j] == card){
                    if (pf.baseStack[i+1][j] == 0) return true;
                    else if (possibleToPutOn(pf.baseStack[i+1][j], card)) return true;
                }
            }
        }
        return false;
    }

    public boolean FreeStackIsEmpty(int[] stack){
        for (int i = 0; i < 24; i++){
            if (stack[i] != 0) return false;
        }
        return true;
    }

    public boolean CheckGameOvering(PlayField pf){
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 4; j++){
                if (pf.homeStack[i][j] == 0) return false;
            }
        }
        return true;
    }
}
