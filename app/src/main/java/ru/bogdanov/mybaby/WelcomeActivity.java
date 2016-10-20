package ru.bogdanov.mybaby;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class WelcomeActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textViewHello, textViewRepr;
    Button button;
    EditText editText;
    private final String LOG="welcome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        imageView = (ImageView) findViewById(R.id.imageViewWelcome);
        textViewHello = (TextView) findViewById(R.id.textViewWelcomeHello);
        textViewRepr = (TextView) findViewById(R.id.textViewWelcomeReprYour);
        button = (Button) findViewById(R.id.buttonWelcome);
        editText = (EditText) findViewById(R.id.editTextWelcome);
        imageView.setVisibility(View.INVISIBLE);

        new AsyncStartAnim().execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick();
            }
        });
    }

    private void buttonClick() {
        String name = editText.getText().toString();
        name = name.trim();
        if (name.equals("")) {
            editText.setText("");
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
        } else {
            SharedPref shared=new SharedPref(this);
            if (!shared.isUserIDSaved()){
                int code=new Random().nextInt();
                String id=name+code;
                shared.saveUserID(id);
                Log.i(LOG, id);
            }

            shared.saveName(name);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public class AsyncStartAnim extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_welcome);
            imageView.setVisibility(View.VISIBLE);
            imageView.startAnimation(animation);
            textViewHello.startAnimation(animation);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            textViewRepr.startAnimation(animation);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            editText.startAnimation(animation);
            button.startAnimation(animation);
        }
    }
}
