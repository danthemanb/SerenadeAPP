package dbarke.uci.edu.earlyserenade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class GamePauseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_pause);

        DisplayMetrics pauseActivitySize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(pauseActivitySize);

        int width = pauseActivitySize.widthPixels;
        int height = pauseActivitySize.heightPixels;

        width *= .8;
        height *= .6;

        getWindow().setLayout(width,height);

        Button endGameButton = (Button) findViewById(R.id.endGameBtn);
        endGameButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent endGameIntent = new Intent(getApplicationContext(), MainActivity.class);
                // How to pass information to another activity
                startActivity(endGameIntent);
            }
        });

        Button returnToGameBtn = (Button) findViewById(R.id.resumeBtn);
        returnToGameBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent resumeIntent = new Intent(getApplicationContext(), GameActivity.class);
                // How to pass information to another activity
                startActivity(resumeIntent);
            }
        });

    }
}
