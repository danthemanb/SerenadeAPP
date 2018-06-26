package dbarke.uci.edu.earlyserenade;

/**
 * Created by Daniel on 3/23/2018.
 */

public class SingleGame {

    public SingleGame(boolean isRedsTurn, int redScore, int blueScore, int gamePoint) {
        this.isRedsTurn = isRedsTurn;
        this.redScore = redScore;
        this.blueScore = blueScore;
        this.gamePoint = gamePoint;
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

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public int getGamePoint() {
        return gamePoint;
    }

    public void setGamePoint(int gamePoint) {
        this.gamePoint = gamePoint;
    }

    public void incRed(){this.redScore++;}

    public void decRed(){this.redScore--;}

    public void incBlue(){this.blueScore++;}

    public void decBlue(){this.blueScore--;}

    public void toggleTurn(){
        if(this.isRedsTurn)
            this.isRedsTurn = false;
        else
            this.isRedsTurn = true;
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

    private boolean isRedsTurn;
    private int redScore;
    private int blueScore;
    private int gamePoint;
    private long seconds;
}
