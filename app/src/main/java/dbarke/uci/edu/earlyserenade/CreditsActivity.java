package dbarke.uci.edu.earlyserenade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        SongList test = new SongList(getApplicationContext());

        test.CreateList();

        String lol = test.ReadList();

        final TextView temp = (TextView) findViewById(R.id.stringRes);
        temp.setText(lol);
    }
}
