package com.example.quizzapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {
    private List<String> li;
    private List<question> lu;
    private ListView lv;
    private User u;
    TextView ten,kc;
    private ImageView ava;
    public int z ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startquiz);
        li = new ArrayList<>();
        lu = new ArrayList<>();
        u = getIntent().getParcelableExtra("tk",User.class);
        lv = findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.chude, li);
        lv.setAdapter(adapter);
        ten = findViewById(R.id.textView5);
        kc = findViewById(R.id.textView8);
        ten.setText(u.getUsername());
        kc.setText(""+u.getKimcuong());
        ava = findViewById(R.id.image_logo);
        ava.setImageResource(u.getAvatar());

        getListquestion(); // Gọi phương thức để lấy danh sách câu hỏi từ API

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Truy cập TextView từ view được chọn
                TextView textView = view.findViewById(android.R.id.text1);

                // Kiểm tra textView có khác null không
                if (textView != null) {
                    // Lấy văn bản từ TextView
                    String selectedText = textView.getText().toString();

                    // Chuyển sang màn hình câu hỏi và chuyền dữ liệu
                    Intent toques = new Intent(StartActivity.this, QuestionActivity.class);

                    toques.putExtra("chude", selectedText);
                    toques.putExtra("tk",u);
                    z= getIntent().getIntExtra("cd",z);

                    toques.putExtra("cd",z);
                    startActivity(toques);
                } else {
                    // TextView không được tìm thấy trong item layout
                    Toast.makeText(StartActivity.this, "TextView not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getListquestion() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<question>> call = apiService.getquestions();
        call.enqueue(new Callback<List<question>>() {
            @Override
            public void onResponse(Call<List<question>> call, Response<List<question>> response) {
                lu = response.body();
                updateListView(); // Sau khi nhận được dữ liệu từ API, cập nhật ListView
            }

            @Override
            public void onFailure(Call<List<question>> call, Throwable t) {
                Toast.makeText(StartActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateListView() {
        for (question x : lu) {
            int k = 0;
            for (String i : li) {
                if (x.getType().equals(i)) { // So sánh chuỗi sử dụng phương thức equals()
                    k = 1;
                    break;
                }
            }
            if (k == 0) {
                li.add(x.getType());
            }
        }
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) lv.getAdapter();
        adapter.notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
    }

}

