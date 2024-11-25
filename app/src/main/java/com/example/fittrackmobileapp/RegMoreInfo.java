package com.example.fittrackmobileapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegMoreInfo extends AppCompatActivity {

    private EditText birthdayDate, weightTxt, heightTxt;
    private boolean[] fitnessGoals = new boolean[5];
    private int selectedId;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg_more_info);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        birthdayDate = findViewById(R.id.DateTxt);
        weightTxt = findViewById(R.id.WeightTxt);
        heightTxt = findViewById(R.id.HeightTxt);

        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        selectedId = genderRadioGroup.getCheckedRadioButtonId();

        String gender = null; // Default value if no selection is made
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            gender = selectedRadioButton.getText().toString();
        } else {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
        }

        CheckBox weightLossCheckBox = findViewById(R.id.weightlossCheckBox);
        CheckBox muscleGainCheckBox = findViewById(R.id.MuscleCheckBox);
        CheckBox generalFitnessCheckBox = findViewById(R.id.GenFitCheckBox);
        CheckBox staminaCheckBox = findViewById(R.id.StaminaCheckBox);
        CheckBox flexibilityCheckBox = findViewById(R.id.FlexibilityCheckBox);

        boolean[] fitnessGoals = {
                weightLossCheckBox.isChecked(),
                muscleGainCheckBox.isChecked(),
                generalFitnessCheckBox.isChecked(),
                staminaCheckBox.isChecked(),
                flexibilityCheckBox.isChecked()
        };

       /* boolean isWeightLossChecked = weightLossCheckBox.isChecked();
        boolean isMuscleGainChecked = muscleGainCheckBox.isChecked();
        boolean isGeneralFitnessChecked = generalFitnessCheckBox.isChecked();
        boolean isStaminaChecked = staminaCheckBox.isChecked();
        boolean isFlexibilityChecked = flexibilityCheckBox.isChecked();*/
        // Displaying the state in a Toast
        Toast.makeText(this,
                "Weight Loss: " + fitnessGoals[0] + "\n" +
                        "Muscle Gain: " + fitnessGoals[1] + "\n" +
                        "General Fitness: " + fitnessGoals[2] + "\n" +
                        "Stamina: " + fitnessGoals[3] + "\n" +
                        "Flexibility: " + fitnessGoals[4],
                Toast.LENGTH_LONG).show();

        Button registerBtn = findViewById(R.id.registerBtn);

        birthdayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        // Register button click handler
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRegistration();
            }
        });
    }

    private boolean areInputsValid() {
        if (birthdayDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your birthday", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (weightTxt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your weight", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (heightTxt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your height", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate gender selection
        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate at least one fitness goal is selected
        boolean hasAtLeastOneGoal = false;
        CheckBox[] fitnessGoalCheckBoxes = {
                findViewById(R.id.weightlossCheckBox),
                findViewById(R.id.MuscleCheckBox),
                findViewById(R.id.GenFitCheckBox),
                findViewById(R.id.StaminaCheckBox),
                findViewById(R.id.FlexibilityCheckBox)
        };
        for (CheckBox checkBox : fitnessGoalCheckBoxes) {
            if (checkBox.isChecked()) {
                hasAtLeastOneGoal = true;
                break;
            }
        }

        if (!hasAtLeastOneGoal) {
            Toast.makeText(this, "Please select at least one fitness goal", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // All inputs are valid
    }

    private void showDatePicker() {
        // Get the current date as the default values for the picker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Format the selected date and set it to the EditText
                        String selectedDate = String.format(Locale.US, "%02d/%02d/%d", month + 1, dayOfMonth, year);
                        birthdayDate.setText(selectedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }



    public void submitRegistration() {
        if (areInputsValid()) {
            // Collect data from fields
            String firstName = getIntent().getStringExtra("firstName");
            String lastName = getIntent().getStringExtra("lastName");
            String email = getIntent().getStringExtra("email");
            String password = getIntent().getStringExtra("password");
            String birthday = birthdayDate.getText().toString();
            String weight = weightTxt.getText().toString();
            String height = heightTxt.getText().toString();

            // Get selected gender
            RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
            int selectedId = genderRadioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);
            String gender = selectedRadioButton.getText().toString();

            // Convert fitness goals from boolean[] to List<Boolean>
            List<Boolean> fitnessGoals = Arrays.asList(
                    ((CheckBox) findViewById(R.id.weightlossCheckBox)).isChecked(),
                    ((CheckBox) findViewById(R.id.MuscleCheckBox)).isChecked(),
                    ((CheckBox) findViewById(R.id.GenFitCheckBox)).isChecked(),
                    ((CheckBox) findViewById(R.id.StaminaCheckBox)).isChecked(),
                    ((CheckBox) findViewById(R.id.FlexibilityCheckBox)).isChecked()
            );

            // Create a User object
            User user = new User(firstName, lastName, email, password, birthday, gender, weight, height, fitnessGoals);

            // Save to Firebase
            databaseReference.push().setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(RegMoreInfo.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegMoreInfo.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegMoreInfo.this, "Failed to save data: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}