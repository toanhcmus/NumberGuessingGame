package com.example.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView textViewLast, textViewRight, textViewHint;
    private Button buttonConfirm;
    private EditText editTextGuess;

    boolean two, three, four;
    Random r = new Random();
    int random;
    int remainingRights = 10;
    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewHint= findViewById(R.id.textViewHint);
        textViewLast = findViewById(R.id.textViewLast);
        textViewRight = findViewById(R.id.textViewRight);

        textViewLast.setVisibility(View.INVISIBLE);
        textViewHint.setVisibility(View.INVISIBLE);
        textViewRight.setVisibility(View.INVISIBLE);

        buttonConfirm = findViewById(R.id.btnConfirm);
        editTextGuess = findViewById(R.id.editTextNumber);

        two = getIntent().getBooleanExtra("two", false);
        three = getIntent().getBooleanExtra("three", false);
        four = getIntent().getBooleanExtra("four", false);

        if (two)
        {
            random = r.nextInt(90) + 10;
        }

        if (three)
        {
            random = r.nextInt(900) + 100;
        }

        if (four)
        {
            random = r.nextInt(9000) + 1000;
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = editTextGuess.getText().toString();
                if (guess.equals(""))
                {
                    Toast.makeText(GameActivity.this, "Please enter a guess", Toast.LENGTH_LONG).show();
                }
                else
                {
                    textViewLast.setVisibility(View.VISIBLE);
                    textViewHint.setVisibility(View.VISIBLE);
                    textViewRight.setVisibility(View.VISIBLE);

                    userAttempts++;
                    remainingRights--;

                    int userGuess = Integer.parseInt(guess);
                    guessesList.add(userGuess);

                    textViewLast.setText("Your last guess is: " + guess);
                    textViewRight.setText("Your remaining right: " + remainingRights);

                    if (random == userGuess)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Congratulation. My guess was " + random + "\n\n You know my number in "+userAttempts + " attempts. \n\n Your guesses: "+guessesList
                                            +"\n\n Would you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }

                    if (random < userGuess)
                    {
                        textViewHint.setText("Decrease your guess");
                    }

                    if (random > userGuess)
                    {
                        textViewHint.setText("Increase your guess");
                    }

                    if (remainingRights == 0)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry, your right to guess is over"+"\n\nMy guess was " + random + "\n\n Your guesses: "+guessesList
                                +"\n\n Would you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }

                    editTextGuess.setText("");

                }
            }
        });


    }
}