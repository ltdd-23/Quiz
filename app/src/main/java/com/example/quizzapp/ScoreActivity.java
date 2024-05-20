package com.example.quizzapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoreActivity extends AppCompatActivity {
    private List<String> li;
    private List<question> lu;
    private ListView lv;
    private User u;
    private int sco;
    List<String> lis;
    private TextView ten,kc,kq;
    private Button bac ;
    private ImageView ava;
    public int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        bac = findViewById(R.id.back);


        u = getIntent().getParcelableExtra("tk",User.class);

        sco = getIntent().getIntExtra("kq",sco);
        u.setScore(u.getScore()+sco*10);
        u.setTimep(u.getTimep()+10);
        lis = getIntent().getStringArrayListExtra("list");


        if(sco == 10){
            u.setKimcuong(u.getKimcuong()+1);
            Toast.makeText(ScoreActivity.this, "Trả lời đúng 10 câu liên tiếp được thêm 1 kim cương", Toast.LENGTH_SHORT).show();
        }
        ten = findViewById(R.id.textView5);

        kq = findViewById(R.id.correctAnswersTextView);

        ten.setText(u.getUsername());

        kq.setText(""+sco*10+"/"+100);
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent backmain = new Intent(ScoreActivity.this,MainActivity.class);
                backmain.putExtra("tk",u);

                int z=1;
                backmain.putExtra("pn",z);
                y= getIntent().getIntExtra("chude",y);
                backmain.putExtra("chude",y);
                startActivity(backmain);
            }

        });
        updateUser();
        Button coilaide = findViewById(R.id.coilaide);
        coilaide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ScoreActivity.this);
                dialog.setContentView(R.layout.de);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 1));

                ListView lv;
                lv = dialog.findViewById(R.id.lvlv);

                // Tạo CustomAdapter
                ArrayAdapter adapter = new ArrayAdapter(ScoreActivity.this, R.layout.chude, lis);

                // Thiết lập CustomAdapter cho ListView
                lv.setAdapter(adapter);

                dialog.show();
                ImageView backmain = dialog.findViewById(R.id.imageView4);
                backmain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    private void updateUser() {
        // Khởi tạo RetrofitClient và ApiService
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Tạo một yêu cầu PUT để cập nhật thông tin người dùng
        Call<User> call = apiService.updateUser(u.getId(),u);

        // Gửi yêu cầu PUT đến máy chủ
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi cập nhật thành công
                    User updatedUser = response.body();

                } else {
                    // Xử lý khi gặp lỗi từ máy chủ
                    Toast.makeText(ScoreActivity.this, "Cập nhật thông tin người dùng thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối hoặc lỗi khác
                Toast.makeText(ScoreActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
