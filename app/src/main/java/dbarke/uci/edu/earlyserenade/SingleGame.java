package dbarke.uci.edu.earlyserenade;

import java.io.Serializable;

/**
 * Created by Daniel on 3/23/2018.
 */

public class SingleGame implements Serializable {

    public SingleGame(){}

    public SingleGame(boolean isRedsTurn, int redScore, int blueScore, int gamePoint) {
        this.isRedsTurn = isRedsTurn;
        this.redScore = redScore;
        this.blueScore = blueScore;
        this.gamePoint = gamePoint;
    }

    public void fillSingleGame(SingleGame old){
        this.isRedsTurn = old.isRedsTurn();
        this.redScore = old.getRedScore();
        this.blueScore = old.getBlueScore();
        this.gamePoint = old.getGamePoint();
        this.seconds = old.getSeconds();
    }

    public void fillSingleGame(boolean oldisRedsTurn, int oldRedScore, int oldBlueScore, int oldGamePoint, long oldSeconds){
        this.isRedsTurn = oldisRedsTurn;
        this.redScore = oldRedScore;
        this.blueScore = oldBlueScore;
        this.gamePoint = oldGamePoint;
        this.seconds = oldSeconds;
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

    public String whosTurn(){
        if(this.isRedsTurn)
            return "Red's";
        else
            return "Blue's";
    }

    private boolean isRedsTurn;
    private int redScore;
    private int blueScore;
    private int gamePoint;
    private long seconds;
}
