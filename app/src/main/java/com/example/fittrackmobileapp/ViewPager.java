package com.example.fittrackmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import android.widget.Button;

public class ViewPager extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItemArrayList;
    Button loginButton;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager2 = findViewById(R.id.viewpager);
        loginButton = findViewById(R.id.button2);
        signUpButton = findViewById(R.id.button3);


        int[] images = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
        String[] heading = {"Personalized Fitness Tracking", "Capture and Visualize Progress", "Custom Workouts and Reminders", "Find Nearby Gyms and Parks", "Log Your Activities"};
        String[] desc = {getString(R.string.a_desc),
                getString(R.string.b_desc),
                getString(R.string.c_desc),
                getString(R.string.d_desc),
                getString(R.string.e_desc)};

        viewPagerItemArrayList = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i], heading[i], desc[i]);
            viewPagerItemArrayList.add(viewPagerItem);
        }

        VPAdapter vpAdapter = new VPAdapter(viewPagerItemArrayList);
        viewPager2.setAdapter(vpAdapter);

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        // Add a PageChangeCallback to detect page changes
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Check if we are on the last page
                if (position == viewPagerItemArrayList.size() - 1) {
                    // Show the Next button on the last page
                    loginButton.setVisibility(View.VISIBLE);
                    signUpButton.setVisibility(View.VISIBLE);
                } else {
                    // Hide the Next button on all other pages
                    loginButton.setVisibility(View.GONE);
                    signUpButton.setVisibility(View.GONE);
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to the dashboard
                Intent intent = new Intent(ViewPager.this, Dashboard.class);
                startActivity(intent);
                finish(); // Optional: if you want to close the ViewPager activity
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to the dashboard
                Intent intent = new Intent(ViewPager.this, Dashboard.class);
                startActivity(intent);
                finish(); // Optional: if you want to close the ViewPager activity
            }
        });
    }
}
