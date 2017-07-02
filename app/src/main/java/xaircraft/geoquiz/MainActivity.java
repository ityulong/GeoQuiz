package xaircraft.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import xaircraft.geoquiz.modle.TrueFalse;

public class MainActivity extends Activity implements View.OnClickListener {
    public final static String QUESTION_INDEX_KEY = "index";
    public final static String QUESTION_DATA = "cheating";

    public final static int CHEAT_REQUEST_CODE = 1;

    private Button btnY;
    private Button btnN;
    private Button btnNext;
    private Button btnPrev;
    private Button btnCheating;
    private TextView tvQuestion;


    private TrueFalse[] mQuestions = new TrueFalse[]{
            new TrueFalse(R.string.ufo_question, true),
            new TrueFalse(R.string.dog_question, false),
            new TrueFalse(R.string.earth_question, true),
            new TrueFalse(R.string.mac_question, true),
            new TrueFalse(R.string.weather_question, true),
            new TrueFalse(R.string.woman_question, false),
    };

    private int mCurrentIndex = 0;

    private TrueFalse mCurrentTrueFalse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(QUESTION_INDEX_KEY);
            mQuestions = (TrueFalse[]) savedInstanceState.getParcelableArray(QUESTION_DATA);
        }
        initViews();


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUESTION_INDEX_KEY, mCurrentIndex);
        outState.putParcelableArray(QUESTION_DATA, mQuestions);
    }

    private void initViews() {
        btnY = (Button) findViewById(R.id.btn_yes);
        btnN = (Button) findViewById(R.id.btn_no);
        btnNext = (Button) findViewById(R.id.btn_next);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        btnPrev = (Button) findViewById(R.id.btn_previous);
        btnCheating = (Button) findViewById(R.id.btn_cheating);

        btnY.setOnClickListener(this);
        btnN.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvQuestion.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnCheating.setOnClickListener(this);

        updateCurrentQuestion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no:
                checkAnswer(false);
                break;
            case R.id.btn_yes:
                checkAnswer(true);
                break;
            case R.id.btn_next:
            case R.id.tv_question:
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateCurrentQuestion();
                break;
            case R.id.btn_previous:
                mCurrentIndex--;
                if (mCurrentIndex < 0)
                    mCurrentIndex = mQuestions.length - 1;
                updateCurrentQuestion();
                break;
            case R.id.btn_cheating:
                Intent i = new Intent(this, CheatActivity.class);
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, mCurrentTrueFalse.isTrueQuestion());
                startActivityForResult(i,CHEAT_REQUEST_CODE);
                break;
        }
    }

    private void updateCurrentQuestion() {
        mCurrentTrueFalse = mQuestions[mCurrentIndex];
        tvQuestion.setText(mCurrentTrueFalse.getQuestion());
    }

    @Deprecated
    public void checkAnswer(boolean answer) {
        if(mCurrentTrueFalse.isCheating()){
            showToast(getString(R.string.cheating_is_wrong));
            return;
        }

        boolean currentAnswer = mCurrentTrueFalse.isTrueQuestion();
        String showStr = currentAnswer == answer ? getString(R.string.correct_answer) : getString(R.string.wrong_answer);
        showToast(showStr);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == CHEAT_REQUEST_CODE && resultCode == RESULT_OK) {
            boolean isCheating = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOW, false);
            mCurrentTrueFalse.setCheating(isCheating);
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
