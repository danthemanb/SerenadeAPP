package dbarke.uci.edu.earlyserenade;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ManageFiles {

    public ManageFiles(Context context){
        File path = context.getFilesDir();
        mainName = "SongList.txt";
        currentName = "RandomSongs.txt";
        appContext = context;

        songlist = new File(path, mainName);
        randomsongs = new File(path, currentName);
    }

    public void CreateList()
    {
        String data = "one\ntwo\nthree\nfour\nfive\n";
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(songlist);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                stream.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean CheckFile() {
        if (songlist == null || !songlist.exists())
            return false;
        else
            return true;
    }

    public String ReadList()
    {
        if(!CheckFile())
        {
            return "Error Attempting to find file";
        }

        int length = (int) songlist.length();

        byte[] bytes = new byte[length];

        FileInputStream in = null;
        try {
            in = new FileInputStream(songlist);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                in.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String contents = new String(bytes);

        return contents;
    }

    private String mainName;
    private String currentName;
    private File songlist;
    private File randomsongs;
    private FileInputStream songsIn;
    private FileOutputStream songsOut;
    private Context appContext;
}

