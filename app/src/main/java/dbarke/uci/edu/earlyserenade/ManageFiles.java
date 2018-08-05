package dbarke.uci.edu.earlyserenade;

import android.content.Context;
import android.renderscript.ScriptGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ManageFiles {

    public ManageFiles(Context context){
        File path = context.getFilesDir();
        currentName = "SongList.txt";
        appContext = context;

        songlist = new File(path, currentName);
        ReadList();
    }

    public ManageFiles(Context context, int temp){
        File path = context.getFilesDir();
        currentName = "SongList.txt";
        appContext = context;

        songlist = new File(path, currentName);
    }

    private void shuffle(String[] list, int listLen) {
        Random rand = new Random();
        int randomNum;
        String temp;
        for(int i=0; i<listLen; i++)
        {
            randomNum = rand.nextInt(listLen);
            temp = list[i];
            list[i] = list[randomNum];
            list[randomNum] = temp;
        }
    }

    public void CreateList()
    {
        int length = 5000;
        InputStream resStream = appContext.getResources().openRawResource(R.raw.app_ready_list);

        byte[] bytes = new byte[length];

        try {
            try {
                resStream.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                resStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String contents = new String(bytes);
        String[] tempArray = contents.split("@", 0);
        //shuffle(Songs, Songs.length);
        Songs = new ArrayList<>(Arrays.asList(tempArray));
        Collections.shuffle(Songs);
    }

    public void CreateTestList() {
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

    private boolean CheckFile() {
        if (songlist == null || !songlist.exists())
            return false;
        else
            return true;
    }

    public void SaveList(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String temp : list)
        {
            sb.append(temp);
            sb.append("@");
        }
        String data = sb.toString();

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

    public String ReadTestList()
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

    public void ReadList()
    {
        if(!CheckFile())
        {
            CreateList();
            return;
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
        String[] tempArray = contents.split("@", 0);
        Songs = new ArrayList<>(Arrays.asList(tempArray));
        if(Songs.isEmpty())
        {
            CreateList();
        }
    }

    public ArrayList<String> getSongs() {
        return Songs;
    }

    private String currentName;
    private File songlist;
    private ArrayList<String> Songs;
    private Context appContext;
}

