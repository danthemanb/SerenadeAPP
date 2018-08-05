package dbarke.uci.edu.earlyserenade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // Set up popup metrics
        DisplayMetrics pauseActivitySize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(pauseActivitySize);
        int width = pauseActivitySize.widthPixels;
        int height = pauseActivitySize.heightPixels;
        width *= .8;
        height *= .6;
        getWindow().setLayout(width,height);

        // Actual activity stuff
        // Get results from game
        final Bundle bundle = getIntent().getExtras();
        final SingleGame currentGame = (SingleGame) bundle.getSerializable("CurrentGame");

        // Same Songlist
        ManageFiles songList = new ManageFiles(this.getApplicationContext(), 0);
        songList.SaveList(currentGame.getSongs());

        // Initialize objects
        TextView resultsTextView = (TextView) findViewById(R.id.resultsTextView);
        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);

        int gameResult = currentGame.testGameOver();
        if(gameResult == 1){
            resultsTextView.setText("Red Team Wins!");
        } else if(gameResult == 2){
            resultsTextView.setText("Blue Team Wins!");
        } else {
            resultsTextView.setText("Error!");
        }

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newGameIntent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(newGameIntent);
            }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainMenuIntent);
            }
        });
    }
}
