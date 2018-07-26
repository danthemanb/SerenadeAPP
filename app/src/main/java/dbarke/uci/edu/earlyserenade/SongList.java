package dbarke.uci.edu.earlyserenade;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SongList {

    public SongList(Context context){
        File path = context.getFilesDir();

        songlist = new File(path, "SongList.txt");
        randomsongs = new File(path, "RandomSongs.txt");
    }

    public void CreateList()
    {
        String data = "one\ntwo\nthree\nfour\nfive\n";
        FileOutputStream stream = new FileOutputStream(songlist);
        try {
            stream.write(data.getBytes());
        } finally {
            stream.close();
        }
    }

    public String ReadList()
    {
        int length = (int) songlist.length();

        byte[] bytes = new byte[length];

        FileInputStream in = new FileInputStream(songlist);
        try {
            in.read(bytes);
        } finally {
            in.close();
        }

        String contents = new String(bytes);

        return contents;
    }

    private File songlist;
    private File randomsongs;
    private FileInputStream songsIn;
    private FileOutputStream songsOut;
}
