package dbarke.uci.edu.earlyserenade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);


        TextView listview = (TextView) findViewById(R.id.listView);

        ManageFiles testing = new ManageFiles(this.getApplicationContext());

        testing.CreateList();

        //String temp = testing.temp();
        //listview.setText(temp);

        String temp = testing.ReadList();
        listview.setText(temp);

    }
}
