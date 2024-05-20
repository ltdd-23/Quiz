package com.example.quizzapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {
    private List<question> lu,lu10,luchude;
    private Random ran = new Random();
    List<String> lis;
    private String chude;
    private List<question> filteredQuestions;
    private ListView lv;
    private Handler mHandler = new Handler();
    private static final int DELAY_TIME = 2000; // Thời gian hiển thị đáp án (2 giây)
    private int currentQuestionIndex = 0,max,i;
    private CountDownTimer countDownTimer;
    private int sco;
    private User u;
    private TextView ten,kc;
    private ImageView ava;
    int y;
    private int min ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        lis = new ArrayList<>();
        lv = findViewById(R.id.listView);
        if (lv == null) {
            Toast.makeText(this, "ListView not found", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, "Intent not found", Toast.LENGTH_SHORT).show();
            return;
        }
        u = intent.getParcelableExtra("tk");
        if (u == null) {
            Toast.makeText(this, "User data not found in intent", Toast.LENGTH_SHORT).show();
            return;
        }
        chude = intent.getStringExtra("chude");
        if (chude == null) {
            Toast.makeText(this, "Chude data not found in intent", Toast.LENGTH_SHORT).show();
            return;
        }

        getListQuestion(); // Call the method to fetch questions from the API
    }

    private void getListQuestion() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<question>> call = apiService.getquestions();
        call.enqueue(new Callback<List<question>>() {
            @Override
            public void onResponse(Call<List<question>> call, Response<List<question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    lu = response.body();
                    updateListView();
                } else {
                    Toast.makeText(QuestionActivity.this, "Failed to get questions from the API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<question>> call, Throwable t) {
                Toast.makeText(QuestionActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateListView() {
        if (lu == null) {
            Toast.makeText(this, "No questions received from the API", Toast.LENGTH_SHORT).show();
            return;
        }

        filteredQuestions = new ArrayList<>();
        for (question x : lu) {
            if (x.getType().equals(chude)) {
                filteredQuestions.add(x);
            }
        }

        if (filteredQuestions.size() < 10) {
            Toast.makeText(this, "Not enough questions for this topic", Toast.LENGTH_SHORT).show();
        } else {
            // Display the first question
            min = 0;
            max = filteredQuestions.size() - 15;
            int randomIndex = getRandomNumberInRange(max);
            min = randomIndex;
            displayQuestion(filteredQuestions.get(randomIndex));
            lis.add("" + filteredQuestions.get(randomIndex).getQues() + " đáp án là " + filteredQuestions.get(randomIndex).getAnswer());
        }
    }


    private void displayQuestion(final question q) {
        // Hiển thị câu hỏi
        TextView tv = findViewById(R.id.textView9);
        tv.setText(q.getQues());

        // Tạo danh sách đáp án
        final List<String> dapan = new ArrayList<>();
        dapan.add(q.getA());
        dapan.add(q.getB());
        dapan.add(q.getC());
        dapan.add(q.getD());


        // Hiển thị danh sách đáp án
        ArrayAdapter adap = new ArrayAdapter<>(QuestionActivity.this,R.layout.dapan, dapan);
        lv.setAdapter(adap);

        // Bắt đầu đếm ngược
        startCountDownTimer();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                countDownTimer.cancel(); // Hủy bỏ đếm ngược

                String selectedAnswer = dapan.get(position);

                // Hiển thị đáp án đúng
                String correctAnswer = q.getAnswer();
                String message;
                if (selectedAnswer.equals(correctAnswer)) {
                    message = "Bạn đã trả lời đúng. Đáp án đúng là: " + correctAnswer;
                    sco = sco +1;
                } else {
                    message = "Bạn đã trả lời sai. Đáp án đúng là: " + correctAnswer;
                }
                showDialogWithDelay(message);
            }
        });
    }

    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(10000, 1000) { // 10 giây, cập nhật mỗi giây
            public void onTick(long millisUntilFinished) {
                // Cập nhật số giây còn lại lên TextView
                long secondsRemaining = millisUntilFinished / 1000;
                TextView textViewCountdown = findViewById(R.id.textViewCountdown);
                textViewCountdown.setText(String.valueOf(secondsRemaining));
            }

            public void onFinish() {
                // Hiển thị dialog khi hết thời gian
                showDialogWithDelay("Hết thời gian. Đáp án đúng là: " + lu.get(currentQuestionIndex).getAnswer());
            }
        }.start();
    }


    private void showDialogWithDelay(final String message) {
        // Hiển thị dialog
        final Dialog dialog = new Dialog(QuestionActivity.this);
        dialog.setContentView(R.layout.chude);
        TextView tvCorrectAnswer = dialog.findViewById(android.R.id.text1);
        tvCorrectAnswer.setText(message);
        dialog.show();

        // Tắt dialog sau 2 giây và hiển thị câu hỏi tiếp theo
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

                currentQuestionIndex++;
                if (currentQuestionIndex < 10) {
                    TextView cau = findViewById(R.id.textView14);
                    cau.setText("Câu số "+ (currentQuestionIndex+1));
                    max = max+1;
                    int randomIndex = getRandomNumberInRange( max);
                    min= randomIndex;
                    displayQuestion(filteredQuestions.get(randomIndex));
                    lis.add(""+filteredQuestions.get(randomIndex).getQues()+" đáp án là " + filteredQuestions.get(randomIndex).getAnswer());

                } else {
                    // Kết thúc hoạt động nếu đã hiển thị đủ 10 câu hỏi
                    Intent tokq = new Intent(QuestionActivity.this, ScoreActivity.class);
                    tokq.putExtra("kq", sco);
                    tokq.putExtra("tk", u);
                    y = getIntent().getIntExtra("cd",y);
                    tokq.putExtra("chude",y);
                    tokq.putStringArrayListExtra("list", (ArrayList<String>) lis);
                    startActivity(tokq);
                }
            }
        }, DELAY_TIME);
    }
    private int getRandomNumberInRange(int max) {
        if (min >= max) {
            min =0;
            return ran.nextInt(filteredQuestions.size()-1);
        }

        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}

