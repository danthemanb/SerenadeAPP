package dbarke.uci.edu.earlyserenade;

/**
 * Created by Daniel on 3/23/2018.
 */

public class SingleGame {

    public SingleGame(boolean isTicking, boolean isRedsTurn, int gamePoint) {
        this.isTicking = isTicking;
        this.isRedsTurn = isRedsTurn;
        this.redScore = 0;
        this.blueScore = 0;
        this.gamePoint = gamePoint;
    }

    public boolean isTicking() {
        return isTicking;
    }

    public void setTicking(boolean ticking) {
        isTicking = ticking;
    }

    public boolean isRedsTurn() {
        return isRedsTurn;
    }

    public int getRedScore() {
        return redScore;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public void setRedsTurn(boolean redsTurn) {
        isRedsTurn = redsTurn;
    }

    public void setRedScore(int redScore) {
        this.redScore = redScore;
    }

    public void setBlueScore(int blueScore) {
        this.blueScore = blueScore;
    }

    public void updateScore(){
        if(this.isRedsTurn){               // Give blue a point if it's red's turn
            this.blueScore++;
        }
        else{                           // Give red a point if it's blue's turn
            this.redScore++;
        }
    }

    public int testGameOver(){
        if(redScore >= gamePoint)
            return 1;
        else if(blueScore >= gamePoint)
            return 2;
        else
            return 0;
    }


    private boolean isTicking;
    private boolean isRedsTurn;
    private int redScore;
    private int blueScore;
    private int gamePoint;
}
