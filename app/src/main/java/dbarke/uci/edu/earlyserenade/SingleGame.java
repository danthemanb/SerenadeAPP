package dbarke.uci.edu.earlyserenade;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 3/23/2018.
 */

public class SingleGame implements Serializable {

    public SingleGame(){}

    public SingleGame(boolean isRedsTurn, int redScore, int blueScore, int gamePoint, long seconds) {
        this.isRedsTurn = isRedsTurn;
        this.redScore = redScore;
        this.blueScore = blueScore;
        this.gamePoint = gamePoint;
        this.seconds = seconds;
    }

    public void fillSingleGame(SingleGame old){
        this.isRedsTurn = old.isRedsTurn();
        this.redScore = old.getRedScore();
        this.blueScore = old.getBlueScore();
        this.gamePoint = old.getGamePoint();
        this.seconds = old.getSeconds();
        this.Songs = old.getSongs();
        this.current = old.getCurrent();
    }

    public void fillSingleGame(boolean oldisRedsTurn, int oldRedScore, int oldBlueScore, int oldGamePoint, long oldSeconds, ArrayList<String> oldSongs, String oldCurrent){
        this.isRedsTurn = oldisRedsTurn;
        this.redScore = oldRedScore;
        this.blueScore = oldBlueScore;
        this.gamePoint = oldGamePoint;
        this.seconds = oldSeconds;
        this.Songs = oldSongs;
        this.current = oldCurrent;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
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

    public ArrayList<String> getSongs() {
        return Songs;
    }

    public void setSongs(ArrayList<String> songs) {
        Songs = songs;
        this.current = Songs.get(0);
    }

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

    public String getWord(){
        return Songs.get(0);
    }

    public String getNextWord(){
        Songs.remove(0);
        this.current = Songs.get(0);
        return Songs.get(0);
    }

    public void newWord()
    {
        Songs.remove(0);
        this.current = Songs.get(0);
    }

    private ArrayList<String> Songs;
    private String current;
    private boolean isRedsTurn;
    private int redScore;
    private int blueScore;
    private int gamePoint;
    private long seconds;
}
