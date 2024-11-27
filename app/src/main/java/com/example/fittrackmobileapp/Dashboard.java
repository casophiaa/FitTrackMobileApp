package com.example.fittrackmobileapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Drawable imageDrawable, imageDrawableTwo, imageDrawableThree;
    RecyclerView horizontalRv;
    ArrayList<ProgFeatItem> dataSource;
    LinearLayoutManager linearLayoutManager;
    DashAdapter dashAdapter;
    TextView gymsNparksBtn;
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gymsNparksBtn = findViewById(R.id.gymNparkContainer);
        horizontalRv = findViewById(R.id.horizontalRv);
        String currentDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());
        dataSource = new ArrayList<>();

        imageDrawable = getResources().getDrawable(R.drawable.a, null);
        imageDrawableTwo = getResources().getDrawable(R.drawable.fimmies2, null);
        imageDrawableThree = getResources().getDrawable(R.drawable.fimmies1, null);
        dataSource.add(new ProgFeatItem(currentDate, imageDrawable));
        dataSource.add(new ProgFeatItem(currentDate, imageDrawableTwo));
        dataSource.add(new ProgFeatItem(currentDate, imageDrawableThree));
        getPicFromFirebase();

        profilePic = findViewById(R.id.profilePic);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dashAdapter = new DashAdapter(dataSource);
        horizontalRv.setLayoutManager(linearLayoutManager);
        horizontalRv.setAdapter(dashAdapter);

        gymsNparksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gymsNparksPage();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUserProfile();
            }
        });
    }

    private void getPicFromFirebase() {
        String userID = firebaseAuth.getCurrentUser().getUid();

        if (userID != null) {
            databaseReference.child("users").child(userID).child("capture").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String date = snapshot.getKey();
                        Drawable picture = snapshot.child("capturedPic").getValue(Drawable.class);
                        dataSource.add(new ProgFeatItem(date, picture));
                    }
                    dashAdapter.notifyDataSetChanged();
                }else {
                    Log.e("Capture", "Error fetching Picture", task.getException());
                }
            });
        }
    }

    public void navigateToUserProfile() {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }

    public void trackMyWorkout(View v) {
        Intent i = new Intent(this, StepCount.class);
        startActivity(i);
    }

    public void exerciseWithMe(View v) {
        Intent i = new Intent(this, ExerciseWithMe.class);
        startActivity(i);
    }

    public void workoutSuggestion(View v) {
        Intent i = new Intent(this, PlanPage.class);
        startActivity(i);
    }

    public void checkProgress(View v) {
        Intent i = new Intent(this, ProgressPage.class);
        startActivity(i);
    }

    public void dashboard(View v) {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

    public void gymsNparksPage() {
        Intent i = new Intent(Dashboard.this, GymsParks.class);
        startActivity(i);
    }
}
