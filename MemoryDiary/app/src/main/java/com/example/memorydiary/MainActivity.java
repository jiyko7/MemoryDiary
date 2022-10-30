package com.example.memorydiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 메인 화면

    public static Context CONTEXT;

    ImageButton imageButton;
    ImageButton imageButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CONTEXT = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //registerForContextMenu(linearLayout);
        imageButton = (ImageButton)findViewById(R.id.MainButton);
        imageButton3 = (ImageButton)findViewById(R.id.WriteButton);
        imageButton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Activity1.class);
                startActivity(intent);
                finish();
            }
        });

        //initialize();

        int count = PreferenceManagers.getInt(getApplicationContext(), "a0");
        if (count==-1){
            PreferenceManagers.setInt(getApplicationContext(), "a0", 0);
        }
       // Toast.makeText(getApplicationContext(),String.valueOf(count), Toast.LENGTH_SHORT).show();
        if (count >0){
            for (int i = count;i>=1;i--){
                if(!PreferenceManagers.getString(getApplicationContext(),"a"+String.valueOf(i)).equals("")) {
                    String str = "a" + i;
                    String title = PreferenceManagers.getString(getApplicationContext(), str);
                    str = "c" + i;
                    String date = PreferenceManagers.getString(getApplicationContext(), str);
                    write_diary(date, title, i);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.Layout);
        switch(item.getItemId()) {
            case R.id.item1:
                constraintLayout.setBackgroundColor((Color.rgb(255,255,255)));
                return true;
            case R.id.item2:
                constraintLayout.setBackgroundColor((Color.rgb(255,0,0)));
                return true;
            case R.id.item3:
                constraintLayout.setBackgroundColor((Color.rgb(0,255,0)));
                return true;
            case R.id.item4:
                constraintLayout.setBackgroundColor((Color.rgb(0,0,255)));
                return true;
        }
        return false;
    }

    public void write_diary(String Date, String Title, int count){
        LinearLayout sc = (LinearLayout)findViewById(R.id.scroll);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout .setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        linearLayout.setId(count);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Activity2.class);
                int Lid = view.getId();
                intent.putExtra("key",Lid);
               // Toast.makeText(getApplicationContext(),String.valueOf(Lid), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();;
            }
        });


        String str = "d" + count;
       // Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
        String image = PreferenceManagers.getString(getApplicationContext(),str);
        ImageView imageF = new ImageView(this);
        if (image.equals("")){
            imageF.setImageResource(R.drawable.defaultimage);
        }
        else{
            imageF.setImageBitmap(StringToBitmap(image));
        }
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(1100,500);
        imageF.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageF.setLayoutParams(parms);
        linearLayout.addView(imageF);
        TextView textView = new TextView(getApplicationContext());
        textView.setText(Title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        textView.setLayoutParams(linearLayoutParams);
        textView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(textView);
        TextView dateView = new TextView(getApplicationContext());
        dateView.setText(Date);
        dateView.setGravity(Gravity.RIGHT);
        dateView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17.0f);
        dateView.setLayoutParams(linearLayoutParams);
        dateView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        linearLayout.addView(dateView);
        sc.addView(linearLayout);
    }
    public void initialize() { //일기 초기화
        int i = PreferenceManagers.getInt(getApplicationContext(), "a0");
        for (int j=0;j<=i;j++){
            PreferenceManagers.removeKey(getApplicationContext(),"a"+j);
            PreferenceManagers.removeKey(getApplicationContext(),"b"+j);
            PreferenceManagers.removeKey(getApplicationContext(),"c"+j);
            PreferenceManagers.removeKey(getApplicationContext(),"d"+j);
        }
        PreferenceManagers.setInt(getApplicationContext(), "a0", 0);
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