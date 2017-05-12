
package com.example.android.PickFood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        chatButton = (Button) findViewById(R.id.button);

        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Users.class));
                finish();
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LogIn.class));
                finish();
                return true;

            case R.id.add:
                startActivity(new Intent(MainActivity.this, AddItem.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
