package com.example.projectassignment;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

        private Toolbar mTopToolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //For toolbar
            mTopToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(mTopToolbar);


            //For NavigationDrawer:
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                    drawer, mTopToolbar, R.string.open, R.string.close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }

        public boolean onCreateOptionsMenu(Menu m) {
            getMenuInflater().inflate(R.menu.menu, m);
            return true;
        }

        @Override
        public boolean onNavigationItemSelected( MenuItem item) {

            String message = null;

            switch(item.getItemId())
            {
                case R.id.action_one:
                    message = "Going to Audio Database API...";
                    Intent goToAudio = new Intent(MainActivity.this, AUDIO_DATABASE_API.class);
                    startActivity(goToAudio);
                    break;
                case R.id.action_two:
                    message = "Going to Covid Case...";
                    Intent goToCovidCase = new Intent(MainActivity.this, COVID_19_CASE_DATA.class);
                    startActivity(goToCovidCase);
                    break;
                case R.id.action_three:
                    message = "Going to Ticket Master...";
                    Intent goToTicketMaster = new Intent(MainActivity.this, TicketMasterEventSearch.class);
                    startActivity(goToTicketMaster);
                    finish();
                    break;
                case R.id.action_four:
                    message = "Going to Recipe Search";
                    Intent goToRecipe = new Intent(MainActivity.this, RecipeSearchActivity.class);
                    startActivity(goToRecipe);
                    break;
            }

            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            drawerLayout.closeDrawer(GravityCompat.START);

            Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_LONG).show();
            return false;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            String message = null;

            switch(item.getItemId())
            {
                case R.id.action_one:
                    message = "Going to Audio Database API...";
                    Intent goToAudio = new Intent(MainActivity.this, AUDIO_DATABASE_API.class);
                    startActivity(goToAudio);
                    break;
                case R.id.action_two:
                    message = "Going to Covid Case...";
                    Intent goToCovidCase = new Intent(MainActivity.this, COVID_19_CASE_DATA.class);
                    startActivity(goToCovidCase);
                    break;
                case R.id.action_three:
                    message = "Going to Ticket Master...";
                    Intent goToTicketMaster = new Intent(MainActivity.this, TicketMasterEventSearch.class);
                    startActivity(goToTicketMaster);
                    finish();
                    break;
                case R.id.action_four:
                    message = "Going to Recipe Search";
                    Intent goToRecipe = new Intent(MainActivity.this, RecipeSearchActivity.class);
                    startActivity(goToRecipe);
                    break;
            }

            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            drawerLayout.closeDrawer(GravityCompat.START);

            Toast.makeText(this, "Toolbar: " + message, Toast.LENGTH_LONG).show();
            return true;
        }


    }
