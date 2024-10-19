package com.example.fittrackmobileapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NthMinuteWorkout extends AppCompatActivity {

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

        ChallengeData[] NthMinute = new ChallengeData[]{
                new ChallengeData("10-Minute Workout", "Quick and Effective", R.drawable.workoutone, "1 min Jumping Jacks, 1 min Push-ups, 1 min Squats, 1 min Mountain Climbers, 1 min Lunges, 1 min Plank, 1 min High Knees, 1 min Bicycle Crunches, 1 min Burpees, 1 min Cool Down"),
                new ChallengeData("15-Minute Workout", "Total Body Blast", R.drawable.workouttwo, "2 min Warm-Up, 1 min Jump Squats, 1 min Push-ups, 1 min Plank Shoulder Taps, 1 min Lunges, 1 min Russian Twists, 1 min Jumping Jacks, 1 min Side Lunges, 1 min Burpees, 2 min Cool Down"),
                new ChallengeData("20-Minute Workout", "Strength & Cardio", R.drawable.workoutthree, "3 min Warm-Up, 1 min Squats, 1 min Push-ups, 1 min Jump Lunges, 1 min Plank Jacks, 1 min Burpees, 1 min Sit-ups, 1 min Skater Jumps, 1 min Mountain Climbers, 1 min Side Planks, 1 min Bicycle Crunches, 1 min High Knees, 2 min Cool Down"),
                new ChallengeData("25-Minute Workout", "Core and Cardio Focus", R.drawable.workoutfour, "3 min Warm-Up, 2 min Squats, 2 min Push-ups, 2 min Plank Holds, 2 min Mountain Climbers, 2 min Lunges, 2 min High Knees, 2 min Sit-ups, 1 min Russian Twists, 2 min Skater Jumps, 5 min Cool Down"),
                new ChallengeData("30-Minute Workout", "Full Body Workout", R.drawable.workoutfive, "5 min Warm-Up, 5 min Cardio (Jumping Jacks & High Knees), 5 min Upper Body (Push-ups & Tricep Dips), 5 min Core (Plank & Bicycle Crunches), 5 min Lower Body (Squats & Lunges), 5 min Cool Down (Stretching)"),
                new ChallengeData("35-Minute Workout", "Intense Cardio", R.drawable.workoutone, "5 min Warm-Up, 10 min Cardio HIIT (High Knees, Jumping Jacks), 10 min Strength (Push-ups, Squats), 5 min Core (Plank, Sit-ups), 5 min Cool Down (Yoga)"),
                new ChallengeData("40-Minute Workout", "Strength and Flexibility", R.drawable.workouttwo, "5 min Warm-Up, 10 min Upper Body (Push-ups, Tricep Dips), 10 min Lower Body (Squats, Lunges), 10 min Yoga Flow, 5 min Cool Down"),
                new ChallengeData("45-Minute Workout", "Full Body Circuit", R.drawable.workoutthree, "5 min Warm-Up, 10 min Cardio (Burpees, Mountain Climbers), 10 min Upper Body (Push-ups, Shoulder Taps), 10 min Lower Body (Jump Squats, Lunges), 5 min Core (Plank, Crunches), 5 min Cool Down"),
                new ChallengeData("50-Minute Workout", "Balanced Routine", R.drawable.workoutfour, "10 min Warm-Up (Dynamic Stretches), 10 min Cardio (Jumping Jacks, Sprints), 10 min Strength (Push-ups, Squats), 10 min Core (Planks, Sit-ups), 5 min Flexibility (Yoga), 5 min Cool Down"),
                new ChallengeData("55-Minute Workout", "Cardio & Strength Combo", R.drawable.workoutfive, "10 min Warm-Up, 15 min Cardio Intervals (High Knees, Skater Jumps, Mountain Climbers), 15 min Strength Training (Push-ups, Squats, Lunges), 10 min Core (Crunches, Bicycle Twists), 5 min Cool Down"),
                new ChallengeData("60-Minute Workout", "Total Body Burn", R.drawable.workoutone, "10 min Warm-Up (Dynamic Movements), 15 min Cardio (HIIT - Jump Squats, Burpees, High Knees), 15 min Strength (Upper and Lower Body), 10 min Core (Plank Variations, Sit-ups), 10 min Cool Down (Yoga and Stretching)")
        };

        ChallengeAdapter nthMinute = new ChallengeAdapter(NthMinute, this);
        recyclerView.setAdapter(nthMinute);
    }
}
