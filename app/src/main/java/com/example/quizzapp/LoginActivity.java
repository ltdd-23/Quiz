package com.example.quizzapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText Name, Pass;
    private Button bl;
    private Dialog dialog;
    private List<User> lu;
    private TextView breg, lp;
    String A,B;

    private ImageButton avatar;

    private User user;
    private Intent musicServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Name = findViewById(R.id.editText_email);
        Pass = findViewById(R.id.editText_password);
        bl = findViewById(R.id.button);
        breg= findViewById(R.id.button_re);
        lp = findViewById(R.id.textView6);
        musicServiceIntent = new Intent(LoginActivity.this, MusicService.class);
        startService(musicServiceIntent);
        lu = new ArrayList<>();
        getListUsers();

        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });
        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tosignup = new Intent(LoginActivity.this,RegisterActivity.class);

                startActivity(tosignup);
            }
        });
        lp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ser();
            }
        });
    }
    private User tkuser(List<User> lu,String Name){
         User x = new User();
         for(User u : lu){
             if(u.getUsername().equals(Name)){
                x=u;
                break;
             }
         }
         return x;
    }
    private void login() {
        String namelogin = Name.getText().toString().trim();
        String passlogin = Pass.getText().toString().trim();
        boolean isLoggedIn = false;
        for (User x : lu) {
            if (x.getUsername().equals(namelogin) && x.getPassword().equals(passlogin)) {
                isLoggedIn = true;
                break;
            }
        }
        if (isLoggedIn) {

            Intent toapp = new Intent(LoginActivity.this, MainActivity.class);
            User u = tkuser(lu, namelogin);
            toapp.putExtra("tk", u);
            int z =0;
            toapp.putExtra("pn",z);
            toapp.putExtra("chude",R.drawable.t0);
            startActivity(toapp);
        } else {
            Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void getListUsers() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                lu = response.body();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ser(){
        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.ser);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.85));
        Button b1,b2;
        EditText a,b;
        b1 = dialog.findViewById(R.id.button8);
        b2 = dialog.findViewById(R.id.button7);
        a = dialog.findViewById(R.id.editTextText3);
         b = dialog.findViewById(R.id.textView13);


         b2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 A = a.getText().toString();
                 B = b.getText().toString();
                 if(A.isEmpty() || B.isEmpty()){
                     Toast.makeText(LoginActivity.this, "Chưa nhập tài khoản hoạc số điện thoại", Toast.LENGTH_SHORT).show();
                 } else {
                     int k=0;
                     for(User i : lu){
                         if(i.getUsername().equals(A) && i.getAnswerbaomat().equals(B)){
                             a.setText("Đây là mật khẩu của bạn "+i.getPassword());
                              k=0;
                             break;
                         } else {
                              k = -1;
                         }

                     }
                     if(k==-1){
                         Toast.makeText(LoginActivity.this, "Nhập sai tài khoản hoặc số điện thoại", Toast.LENGTH_SHORT).show();
                     }
                 }
             }
         });
         b1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialog.dismiss();
             }
         });


        dialog.show();
    }

}
