package com.example.fittrackmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegMoreInfo extends AppCompatActivity {

    private EditText birthdayDate, weightTxt, heightTxt;
    private boolean[] fitnessGoals = new boolean[5];
    private int selectedId;

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

        birthdayDate = findViewById(R.id.DateTxt);
        weightTxt = findViewById(R.id.WeightTxt);
        heightTxt = findViewById(R.id.HeightTxt);

        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        selectedId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String gender = selectedRadioButton.getText().toString();

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
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean hasAtLeastOneSelection = false;
        for (boolean goal : fitnessGoals) {
            if (goal) {
                hasAtLeastOneSelection = true;
                break; // Exit the loop early since we found one
            }
        }
        if (!hasAtLeastOneSelection) {
            Toast.makeText(this, "Please select at least one goal.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // All inputs are valid
    }

    public void submitRegistration() {
        // Check if all inputs are valid
        if (areInputsValid()) {
            // Proceed with the registration if inputs are valid
            Intent intent = new Intent(RegMoreInfo.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}