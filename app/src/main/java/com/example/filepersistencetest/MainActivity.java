package com.example.filepersistencetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    //存储文件数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.edit);
        String inputText = load();
        if(!TextUtils.isEmpty(inputText)){
            editText.setText(inputText);
            editText.setSelection(inputText.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_LONG).show();
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        String inputText = editText.getText().toString();
        save(inputText);
    }

    public void save(String inputText){
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try{
            fos =  openFileOutput("data", Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(bw != null)
                    bw.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String load(){
        FileInputStream fis = null;
        BufferedReader br = null;
        StringBuilder content = new StringBuilder();
        try{
            fis = openFileInput("data");
            br = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while((line = br.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(br != null){
                try{
                        br.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        return content.toString();
    }
}
