package com.example.memorydiary;
import static android.widget.ImageView.ScaleType.CENTER_INSIDE;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

public class Activity1 extends AppCompatActivity {

    //작성 화면
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                       // Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Button saveButton = (Button) findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                int count = PreferenceManagers.getInt(getApplicationContext(), "a0");
               // Toast.makeText(getApplicationContext(),String.valueOf(count), Toast.LENGTH_SHORT).show();
                count++;
                PreferenceManagers.setInt(getApplicationContext(), "a0", count);
                PreferenceManagers.setInt(getApplicationContext(), "b0", count);
                PreferenceManagers.setInt(getApplicationContext(), "c0", count);
                PreferenceManagers.setInt(getApplicationContext(), "d0", count);
                String str = "c"+count;
                //Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
                EditText text = findViewById(R.id.TitleText);
                String title = text.getText().toString();
                text = findViewById(R.id.DateText);
                String date = text.getText().toString();
                text = findViewById(R.id.ContentText);
                String content = text.getText().toString();
                //Toast.makeText(getApplicationContext(),content, Toast.LENGTH_SHORT).show();
                PreferenceManagers.setString(getApplicationContext(), str, date);
                str =  "a"+ count;
                PreferenceManagers.setString(getApplicationContext(), str, title);
                str =  "b"+ count;
                PreferenceManagers.setString(getApplicationContext(), str, content);

                activityResultLauncher.launch(intent);
                finish();
                /*
                EditText text = findViewById(R.id.TitleText);
                String title = text.getText().toString();
                text = findViewById(R.id.DateText);
                String date = text.getText().toString();

                ((MainActivity)MainActivity.CONTEXT).write_diary(date,title);
                ((MainActivity)MainActivity.CONTEXT).refresh();
                Toast.makeText(getApplicationContext(),"일기 생성", Toast.LENGTH_SHORT).show();


                */
            }
        });
        Button closeButton = (Button) findViewById(R.id.CloseButton);
        closeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void bt(View view) {    // 이미지 선택 누르면 실행됨 이미지 고를 갤러리 오픈
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    int count = PreferenceManagers.getInt(getApplicationContext(), "a0");
                    count++;
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    String temp = BitmapToString(imgBitmap);
                    String str = "d" + count;

                    PreferenceManagers.setString(getApplicationContext(), str, temp);
                    instream.close();   // 스트림 닫아주기
                    String temp2 = PreferenceManagers.getString(getApplicationContext(), str);
                    ImageView imageView =  findViewById(R.id.imageView);
                    imageView.setImageBitmap(StringToBitmap(temp2));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String BitmapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[]byteArray = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(byteArray,Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitmap(String encodedString)
    {
        try{
            byte[] encodedByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte,0,encodedByte.length);
            return bitmap;
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }
}