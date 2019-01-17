package com.example.android.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    List<RadioButton> radioButtonList = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private TextView question;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;
    private int actualQuestionNumber;
    private int correctAnswer;
    private TextView result;
    private Button nextButton;
    private TextView userNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        String playerName = preferences.getString("username", "");
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        result = findViewById(R.id.result);
        nextButton = findViewById(R.id.next_button);
        userNameText = findViewById(R.id.name);
        userNameText.setText("Hello " + playerName + "!");
        actualQuestionNumber = 0;

        if (savedInstanceState != null) {
            actualQuestionNumber = savedInstanceState.getInt("actualQuestionNumber");
            correctAnswer = savedInstanceState.getInt("correctAnswer");
            answer1.setChecked(savedInstanceState.getBoolean("answer1"));
            answer2.setChecked(savedInstanceState.getBoolean("answer2"));
            answer3.setChecked(savedInstanceState.getBoolean("answer3"));
            answer4.setChecked(savedInstanceState.getBoolean("answer4"));
        }

        radioButtonList.add(answer1);
        radioButtonList.add(answer2);
        radioButtonList.add(answer3);
        radioButtonList.add(answer4);

        QuestionProvider questionProvider = new QuestionProvider();
        questions = questionProvider.getQuestions();
        if (actualQuestionNumber == questions.size() - 1) {
            showResult();
        }
        showNextQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("actualQuestionNumber", actualQuestionNumber);
        outState.putInt("correctAnswer", correctAnswer);
        outState.putBoolean("answer1", answer1.isChecked());
        outState.putBoolean("answer2", answer2.isChecked());
        outState.putBoolean("answer3", answer3.isChecked());
        outState.putBoolean("answer4", answer4.isChecked());
    }

    private void showNextQuestion() {
        Question actualQuestion = questions.get(actualQuestionNumber);
        this.question.setText(actualQuestion.getText());

        RadioGroup radioGroup = findViewById(R.id.answers);
        radioGroup.clearCheck();

        answer1.setChecked(false);
        answer2.setChecked(false);
        answer3.setChecked(false);
        answer4.setChecked(false);

        answer1.setText(actualQuestion.getAnswers().get(0).getText());
        answer2.setText(actualQuestion.getAnswers().get(1).getText());
        answer3.setText(actualQuestion.getAnswers().get(2).getText());
        answer4.setText(actualQuestion.getAnswers().get(3).getText());
    }

    public void showResult() {
        result.setText("Your result: " + correctAnswer + "" + " of " + questions.size() + "");
        result.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
    }


    public void nextQuestion(View view) {
        for (int i = 0; i < radioButtonList.size(); ++i) {
            if (radioButtonList.get(i).isChecked() && questions.get(actualQuestionNumber).getAnswers().get(i).isCorrect()) {
                correctAnswer++;
            }
        }
        if (actualQuestionNumber == questions.size() - 1) {
            showResult();
        } else {
            actualQuestionNumber += 1;
            showNextQuestion();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.username:
                Intent i = new Intent(this, MyPreferencesActivity.class);
                startActivity(i);
                return true;
            case R.id.new_game:
                newGame();
                resetResult();
                return true;
            case R.id.quit:
                showQuit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void resetResult() {
        correctAnswer = 0;
        result.setVisibility(View.GONE);
        nextButton.setVisibility(View.VISIBLE);
    }

    private void showQuit() {
        this.finish();
        System.exit(0);
    }

    private void newGame() {
        resetResult();
        actualQuestionNumber = 0;
        showNextQuestion();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String playerName = sharedPreferences.getString("username", "");
        userNameText.setText(playerName);
    }
}
