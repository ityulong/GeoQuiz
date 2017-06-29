package xaircraft.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import xaircraft.geoquiz.modle.TrueFalse;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnY;
    private Button btnN;
    private Button btnNext;
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
        btnY = (Button) findViewById(R.id.btn_yes);
        btnN = (Button) findViewById(R.id.btn_no);
        btnNext = (Button) findViewById(R.id.btn_next);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        btnY.setOnClickListener(this);
        btnN.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        mCurrentTrueFalse = mQuestions[mCurrentIndex];
        tvQuestion.setText(mCurrentTrueFalse.getQuestion());

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
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                mCurrentTrueFalse = mQuestions[mCurrentIndex];
                tvQuestion.setText(mCurrentTrueFalse.getQuestion());
                showToast("触发补丁！");
                break;
        }
    }

    public void checkAnswer(boolean answer){
        boolean currentAnswer = mCurrentTrueFalse.isTrueQuestion();
        String showStr = currentAnswer == answer ? getString(R.string.correct_answer) : getString(R.string.wrong_answer);
        showToast(showStr);

    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
