package ru.bogdanov.mybaby;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import ru.bogdanov.mybaby.Chat.Chat;
import ru.bogdanov.mybaby.Forum.FireBase;
import ru.bogdanov.mybaby.Forum.ForumStorage;
import ru.bogdanov.mybaby.Forum.TopicForumFragment;
import ru.bogdanov.mybaby.HintFragments.HeaderFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private final String LOG="main_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        startFragment(new TopicForumFragment());

        ImageView imageView=(ImageView) findViewById(R.id.imageViewBackground);
        imageView.setImageResource(R.drawable.gradient1);

        addAd();
        ForumStorage.setUserID(new SharedPref(this).getUserID());
        new FireBase().setBanned();
    }

    private void addAd() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6667638133499349~8773289711");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void sendToMe(){
        View view=View.inflate(this,R.layout.send_to_me,null);
        final EditText editTextSendToMe=(EditText) view.findViewById(R.id.editTextSendToMe);
        AlertDialog alertDialog= new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ss=editTextSendToMe.getText().toString();
                        ss.trim();
                        if (ss.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Введите сообщение", Toast.LENGTH_SHORT).show();
                            sendToMe();
                        }else {
                            new FireBase().sendToMe(ss);
                            Toast.makeText(getApplicationContext(), "Сообщение отправлено", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.gradient1);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.textPrimary));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_forum) {
            startFragment(new TopicForumFragment());
        } else if (id == R.id.nav_hints) {
            startFragment(new HeaderFragment());
        }else if (id == R.id.nav_chat) {
            startFragment(new Chat());
        } else if (id == R.id.nav_send_to_me) {
            sendToMe();
        }else if (id == R.id.nav_change_name) {
            changeName();
        }else if (id == R.id.nav_rules) {
            showRules();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showRules() {
        new AlertDialog.Builder(this)
                .setTitle("Правила")
                .setMessage("Спасибо за использование нашего приложения!\n" +
                        "\n" +
                        "Запрещено:\n" +
                        "- Реклама в любом виде, не согласованная с создателями приложения. Для ваших предложений воспользуйтесь *Написать разработчику*, или на почту leeroy-eve@mail.ru;\n" +
                        "- Неадвекатное поведение или грубое оскорбление пользователей;\n" +
                        "- Попытки *троллинга*;\n" +
                        "- Использование внешних ссылок, которые могут нанести вред пользователям или относятся к пунктам выше.\n" )
                .setPositiveButton("Принято", null)
                .show();
    }

    private void changeName() {
        Intent intent=new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void startFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment_layout,fragment);
        fragmentTransaction.commit();
    }


}
