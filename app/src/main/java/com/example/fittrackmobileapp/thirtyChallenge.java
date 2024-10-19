package com.example.fittrackmobileapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class thirtyChallenge extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thirty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ChallengeData[] ChallengeData = new ChallengeData[]{
                new ChallengeData("Day 1", "Full Body Warm-Up", R.drawable.yogaone, "10 min Jumping Jacks, 10 min Squats, 10 min Lunges"),
                new ChallengeData("Day 2", "Core Focus", R.drawable.yogatwo, "3 sets of 20 Sit-Ups, 3 sets of 15 Leg Raises"),
                new ChallengeData("Day 3", "Cardio Burst", R.drawable.yogathree, "20 min Brisk Walk or Jog"),
                new ChallengeData("Day 4", "Upper Body Strength", R.drawable.yogafour, "3 sets of 15 Push-Ups, 3 sets of 12 Tricep Dips"),
                new ChallengeData("Day 5", "Flexibility & Stretching", R.drawable.yogafive, "30 min Yoga (Basic Poses)"),
                new ChallengeData("Day 6", "Lower Body Focus", R.drawable.yogaone, "3 sets of 20 Squats, 3 sets of 15 Calf Raises"),
                new ChallengeData("Day 7", "Active Recovery", R.drawable.yogatwo, "20 min Light Stretching or Yoga"),
                new ChallengeData("Day 8", "Core & Balance", R.drawable.yogathree, "3 sets of 15 Plank Holds (30 seconds each), 20 Crunches"),
                new ChallengeData("Day 9", "HIIT (High-Intensity)", R.drawable.yogafour, "4 rounds of 1 min Jumping Jacks, 1 min Burpees"),
                new ChallengeData("Day 10", "Cardio & Endurance", R.drawable.yogafive, "30 min Cycling or Running"),
                new ChallengeData("Day 11", "Upper Body Tone", R.drawable.yogaone, "3 sets of 12 Push-Ups, 3 sets of 15 Dumbbell Rows"),
                new ChallengeData("Day 12", "Leg & Glute Workout", R.drawable.yogatwo, "3 sets of 15 Lunges, 3 sets of 20 Glute Bridges"),
                new ChallengeData("Day 13", "Core Conditioning", R.drawable.yogathree, "3 sets of 20 Bicycle Crunches, 3 sets of 15 Russian Twists"),
                new ChallengeData("Day 14", "Rest Day", R.drawable.yogafour, "Focus on hydration and nutrition"),
                new ChallengeData("Day 15", "Full Body HIIT", R.drawable.yogafive, "4 rounds: 1 min Jump Squats, 1 min High Knees"),
                new ChallengeData("Day 16", "Flexibility & Mobility", R.drawable.yogaone, "30 min Pilates (Beginner Level)"),
                new ChallengeData("Day 17", "Upper & Lower Body Combo", R.drawable.yogatwo, "3 sets of 15 Push-Ups, 3 sets of 15 Squats"),
                new ChallengeData("Day 18", "Cardio Endurance", R.drawable.yogathree, "40 min Jog/Run"),
                new ChallengeData("Day 19", "Core Intensity", R.drawable.yogafour, "3 sets of 1 min Planks, 3 sets of 20 Flutter Kicks"),
                new ChallengeData("Day 20", "Lower Body Sculpt", R.drawable.yogafive, "3 sets of 20 Lunges, 3 sets of 15 Step-Ups"),
                new ChallengeData("Day 21", "Yoga for Recovery", R.drawable.yogaone, "30 min Yoga for Flexibility and Recovery"),
                new ChallengeData("Day 22", "Upper Body Strength & Tone", R.drawable.yogatwo, "3 sets of 12 Dumbbell Curls, 3 sets of 15 Tricep Extensions"),
                new ChallengeData("Day 23", "Full Body Circuit", R.drawable.yogathree, "3 rounds: 10 Burpees, 15 Squats, 20 Mountain Climbers"),
                new ChallengeData("Day 24", "Core & Stability", R.drawable.yogafour, "3 sets of 1 min Planks, 3 sets of 20 Leg Raises"),
                new ChallengeData("Day 25", "Cardio Power", R.drawable.yogafive, "30 min HIIT: 1 min Jumping Jacks, 1 min Sprint Intervals"),
                new ChallengeData("Day 26", "Strength & Flexibility Combo", R.drawable.yogaone, "3 sets of 15 Push-Ups, 30 min Yoga"),
                new ChallengeData("Day 27", "Core Burn", R.drawable.yogatwo, "3 sets of 25 Sit-Ups, 3 sets of 20 Russian Twists"),
                new ChallengeData("Day 28", "Cardio & Strength", R.drawable.yogathree, "20 min Jog + 3 sets of 15 Lunges"),
                new ChallengeData("Day 29", "Mobility & Recovery Yoga", R.drawable.yogafour, "30 min Yoga for Stretching and Relaxation"),
                new ChallengeData("Day 30", "Full Body Challenge", R.drawable.yogafive, "3 rounds: 1 min Burpees, 1 min Squats, 1 min Plank")
        };

        ChallengeAdapter challengeAdapter = new ChallengeAdapter(ChallengeData, this); // Use 'this' to pass context
        recyclerView.setAdapter(challengeAdapter);
    }
}
