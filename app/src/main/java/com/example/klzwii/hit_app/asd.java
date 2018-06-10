package com.example.klzwii.hit_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class asd extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,clas_frag.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asd);

        final String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        TextView tx = (TextView) findViewById(R.id.titext);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tx = findViewById(R.id.titext);
        //Toolbar 必须在onCreate()之后设置标题文本，否则默认标签将覆盖我们的设置
        if(toolbar!=null)
            toolbar.setTitle("");
        if (tx != null) {
            tx.setText("通知");
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.asd, menu);
        TextView te = findViewById(R.id.user);
        te.setText(data.getHit_num());
        te=findViewById(R.id.user_email);
        te.setText(data.getHit_num()+"@stu.hit.edu.cn");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.group) {
            Fragment frag1 = getSupportFragmentManager().findFragmentById(R.id.clas_frag);
            TextView tx = findViewById(R.id.titext);
            tx.setText("当前班级");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(frag1 == null){
                frag1 = new clas_frag();
            }
            transaction.replace(R.id.content_clas, frag1, "tag");
            transaction.commit();
            // Handle the camera action
        }  else if (id == R.id.indi) {
            TextView tx = findViewById(R.id.titext);
            tx.setText("个人信息");
            Fragment frag1 = getSupportFragmentManager().findFragmentById(R.id.indifrag);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(frag1 == null){
                frag1 = new PlusOneFragment();
            }
            transaction.replace(R.id.content_clas, frag1, "xixi");
            transaction.commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
