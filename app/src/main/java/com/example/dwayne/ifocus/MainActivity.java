package com.example.dwayne.ifocus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dwayne.ifocus.budget.BudgetHistoryDatabase;
import com.example.dwayne.ifocus.health.history.HealthHistoryDatabase;
import com.example.dwayne.ifocus.study.history.StudyHistoryDatabase;
import com.example.dwayne.ifocus.user.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

     ViewPager view_pager;
    private HealthHistoryDatabase healthHistoryDatabase;
    private BudgetHistoryDatabase budgetHistoryDatabase;
    private StudyHistoryDatabase studyHistoryDatabase;

    public HealthHistoryDatabase getHealthHistoryDatabase() {
        return healthHistoryDatabase;
    }

    public BudgetHistoryDatabase getBudgetHistoryDatabase() {
        return budgetHistoryDatabase;
    }

    public StudyHistoryDatabase getStudyHistoryDatabase(){ return studyHistoryDatabase; }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.addTab(tab_layout.newTab().setText("BUDGET"));
        tab_layout.addTab(tab_layout.newTab().setText("STUDY "));
        tab_layout.addTab(tab_layout.newTab().setText("HEALTH"));

        view_pager = (ViewPager) findViewById(R.id.view_pager);

        final ViewPagerAdapter adapter = new ViewPagerAdapter
                (getSupportFragmentManager(), tab_layout.getTabCount());

        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        healthHistoryDatabase = new HealthHistoryDatabase(this);
        studyHistoryDatabase = new StudyHistoryDatabase(this);
        setNavigationDrawerData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }else if(id == R.id.action_history){
            Intent intent = new Intent(MainActivity.this, ViewHistory.class);
            intent.putExtra("current_tab",view_pager.getCurrentItem());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent itd = null;

        if (id == R.id.nav_viewscore) {
            //itd = new Intent(MainActivity.this,ViewScore.class);
            //startActivity(itd);
        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_give_feedback) {

        } else if (id == R.id.nav_logout) {
            itd = new Intent(MainActivity.this,LogOut.class);
            startActivity(itd);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);



        return true;

    }

    public void setNavigationDrawerData(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        final Menu menu = navigationView.getMenu();
        menu.getItem(0).setTitle("Score is "+User.getInstance().getUserScore());

        TextView txtv_username = (TextView)header.findViewById(R.id.txtv_username);
        txtv_username.setText(User.getInstance().getUserName());
    }

}