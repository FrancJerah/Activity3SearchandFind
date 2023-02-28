package com.example.activity3searchandfind;

import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
//    variable instantiation
    EditText inputText;
    TextView response;
    Button saveButton, readButton;
    String stringSearch;
    int indexSearch;

//    file instantiation (filename, filepath, file variable itself, default myData String
    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    String myData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        find id of variables instatiated
        inputText = (EditText) findViewById(R.id.myInputText);
        response = (TextView) findViewById(R.id.response);
        saveButton = (Button) findViewById(R.id.saveExternalStorage);

//        saveButton onClickListener
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                fileoutputstream instantiation and getting text from user
                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(inputText.getText().toString().getBytes());
                    fos.close();
//                    if anything above fails
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputText.setText("");
                response.setText("SampleFile.txt saved to External Storage");
            }
        });

//        readButton onClickListener
        readButton = (Button) findViewById(R.id.getExternalStorage);
        readButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                fileInputStream instantiation, DataInputStream as editor,
//                BufferedReader as line reader
                try {
                    FileInputStream fis = new FileInputStream(myExternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    // strLine instatiation
                    String strLine;
                    // process to read line
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                    }
                    in.close();
//                    if anything above fails
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                setting stringSearch as user input's string to find word
                stringSearch = inputText.getText().toString();
                // find index of user input's string relative to the saved string
                indexSearch = myData.indexOf(stringSearch);
                // condition for results
                if (myData.contains(stringSearch)) {
                    inputText.setText("Search word is position at the " + indexSearch + " element of the string");
                    Toast.makeText(MainActivity.this, "Search word is found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Search word is not found", Toast.LENGTH_SHORT).show();
                }
                response.setText("SampleFile.txt data retrieved from External Storage");
            }
        });

//        checking if External storage is indeed available and can be written instead of read-only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            saveButton.setEnabled(false);
        }
        else {
//            set the filepath and filename of external file, which is SampleFile.txt
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }


    }
//    checking if External Storage is read only
        private static boolean isExternalStorageReadOnly() {
            String extStorageState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                return true;
            }
            return false;
        }

//        chekcing if External Storage is indeed available
        private static boolean isExternalStorageAvailable() {
            String extStorageState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                return true;
            }
            return false;

        }
}