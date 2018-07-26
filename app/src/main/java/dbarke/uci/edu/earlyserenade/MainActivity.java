package dbarke.uci.edu.earlyserenade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = (Button) findViewById(R.id.playBtn);
        Button settingsButton = (Button) findViewById(R.id.settingsBtn);

        // Launch game activity
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toGameIntent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(toGameIntent);
            }
        });

        // Launch settings activity
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSettingsIntent = new Intent(getApplicationContext(), CreditsActivity.class);
                startActivity(toSettingsIntent);
            }
        });
    }
}
