package com.example.memorydiary;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {

    //상세 화면

    /*삭제처리*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Intent intent = getIntent();
        int Lid = intent.getIntExtra("key",-1);

        TextView textView = (TextView)findViewById(R.id.textView2);
        String temp = PreferenceManagers.getString(getApplicationContext(), "a"+Lid);
        textView.setText(temp);

        textView = (TextView)findViewById(R.id.ContentText1);
        temp = PreferenceManagers.getString(getApplicationContext(), "b"+Lid);
        textView.setText(temp);

        textView = (TextView)findViewById(R.id.textView1);
        temp = PreferenceManagers.getString(getApplicationContext(), "c"+Lid);
        textView.setText(temp);

        temp = PreferenceManagers.getString(getApplicationContext(), "d"+Lid);
        ImageView imageView =  findViewById(R.id.imageView1);
        temp = PreferenceManagers.getString(getApplicationContext(), "d"+Lid);
        if (temp.equals("")){
            imageView.setImageResource(R.drawable.defaultimage);
        }
        else{
            imageView.setImageBitmap(StringToBitmap(temp));
        }


        Button modifybutton = (Button)findViewById(R.id.SaveButton1);
        modifybutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                intent.putExtra("key",Lid);
                startActivity(intent);
                finish();
            }
        });


        /*
        삭제
        * */
        Button b = (Button)findViewById(R.id.DeleteButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delete = "a"+String.valueOf(Lid);
                PreferenceManagers.removeKey(getApplicationContext(),delete);
                delete = "b"+String.valueOf(Lid);
                PreferenceManagers.removeKey(getApplicationContext(),delete);
                delete = "c"+String.valueOf(Lid);
                PreferenceManagers.removeKey(getApplicationContext(),delete);
                delete = "d"+String.valueOf(Lid);
                PreferenceManagers.removeKey(getApplicationContext(),delete);
                Toast.makeText(getApplicationContext(),"삭제 되었습니다.",Toast.LENGTH_SHORT);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*
        지도
         */
        Button c = (Button) findViewById(R.id.MapMap);
        c.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String Map = PreferenceManagers.getString(getApplicationContext(),"a"+String.valueOf(Lid));
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("title",Map);
                startActivity(intent);

            }
        });

        Button d= (Button) findViewById(R.id.CloseClose);
        d.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public Bitmap StringToBitmap(String encodedString)
    {
        try{
            byte[] encodedByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte,0,encodedByte.length);
            return bitmap;
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }
}