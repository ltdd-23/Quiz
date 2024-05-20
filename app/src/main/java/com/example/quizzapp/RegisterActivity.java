package com.example.quizzapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText Name, Pass, sdt;
    private TextView bl;
    private User user;
    private Button su;
    private  int avatar;
    private List<User> lu;
    private int k;
    private ImageView logo;

    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = findViewById(R.id.editText_email);
        Pass = findViewById(R.id.editText_password);
        bl = findViewById(R.id.backlogin);
        su = findViewById(R.id.button_login);
        logo= findViewById(R.id.image_logo);
        logo.setImageResource(R.drawable.img_1);
        sdt = findViewById(R.id.editTextText2);
        lu = new ArrayList<>();
        avatar = 1;
        getListUsers();
         k=0;
         //dangki
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonlogo();
            }
        });
         su.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dangky(); return;
             }
         });
         // sang login
         bl.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent tologin = new Intent(RegisterActivity.this,LoginActivity.class);
                 startActivity(tologin);
             }
         });

    }


    private void chonlogo(){
         dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(R.layout.bottom_sheet_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.75));

        // Lấy GridView từ layout
        GridView gridView = dialog.findViewById(R.id.gridViewAvatars);


        // Tạo danh sách hình ảnh
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.img_1);
        imageList.add(R.drawable.img_2);
        imageList.add(R.drawable.img_3);
        imageList.add(R.drawable.img_4);
        imageList.add(R.drawable.img_5);
        imageList.add(R.drawable.img_6);
        imageList.add(R.drawable.img_7);
        imageList.add(R.drawable.img_8);
        imageList.add(R.drawable.img_9);
        imageList.add(R.drawable.img_10);

        //imageList.add(R.drawable.person3);
        //imageList.add(R.drawable.person4);
        //imageList.add(R.drawable.person5);
        //imageList.add(R.drawable.person6);
        //imageList.add(R.drawable.person7);
        //imageList.add(R.drawable.person8);
        //imageList.add(R.drawable.person9);
        // Thêm các hình ảnh khác vào danh sách

        // Tạo adapter cho GridView
        ImageAdapter adapter = new ImageAdapter(imageList);

        // Đặt adapter cho GridView
        gridView.setAdapter(adapter);

        // Xử lý sự kiện khi người dùng chọn một hình ảnh từ GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi người dùng chọn một hình ảnh từ GridView
                int selectedImage = adapter.getItem(position);
                // Thực hiện các hành động khác với hình ảnh được chọn
            }
        });

        // Xử lý sự kiện khi người dùng nhấn nút "Đóng"


        // Hiển thị dialog
        dialog.show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi người dùng chọn một hình ảnh từ GridView
                int selectedImage = adapter.getItem(position);

                // Gán giá trị của selectedImage cho biến avatar
                avatar = selectedImage;

                // Thay đổi ImageView ở màn hình đăng nhập thành ảnh được chọn
                ImageView loginAvatar = findViewById(R.id.image_logo);
                loginAvatar.setImageResource(avatar);

                // Đóng dialog sau khi chọn hình ảnh
                dialog.dismiss();
            }
        });
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
                Toast.makeText(RegisterActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
        private void dangky(){
            String ten = Name.getText().toString();
            String mk = Pass.getText().toString();
            String sp = sdt.getText().toString();

            if(sp.isEmpty()){
                Toast.makeText(RegisterActivity.this, "Nhập số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }
            if(mk.isEmpty()){
                Toast.makeText(RegisterActivity.this, "Nhập mật khẩu để đăng ký", Toast.LENGTH_SHORT).show();
                return;
            }
            if(ten.isEmpty()){
                Toast.makeText(RegisterActivity.this, "Nhập tên để đăng ký", Toast.LENGTH_SHORT).show();
                return;
            }
            if(ten.length()<3){
                Toast.makeText(RegisterActivity.this, "tên phải hơn 3 kí tự", Toast.LENGTH_SHORT).show();
                return;
            }
            if(mk.length()<3){
                Toast.makeText(RegisterActivity.this, "mật khẩu phải hơn 3 kí tự", Toast.LENGTH_SHORT).show();
                return;
            }
            if(sp.length()<8){
                Toast.makeText(RegisterActivity.this, "Sai định dạng số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }


            if(lu.stream().count()==0){
                Toast.makeText(RegisterActivity.this, "Tài khoản đang được tạo", Toast.LENGTH_SHORT).show();
                user = new User(lu.stream().count()+1, Name.getText().toString(), Pass.getText().toString(),0,sdt.getText().toString(), "Nhập số điện thoại để lấy lại mật khẩu", 0,avatar,0);
                updateUser();
                Intent toapp = new Intent(RegisterActivity.this,LoginActivity.class);
                toapp.putExtra("tk", user);
                startActivity(toapp);
                return;
            }
            for(User u: lu){
                if(ten.equals(u.getUsername())){
                    Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }  else {
                    k=1;
                }


            }
            if(k == 1){

                Toast.makeText(RegisterActivity.this, "Tài khoản đang được tạo", Toast.LENGTH_SHORT).show();
                user = new User(lu.stream().count()+1, Name.getText().toString(), Pass.getText().toString(),0,sdt.getText().toString(), "Nhập số điện thoại để lấy lại mật khẩu", 0,avatar,0);
                updateUser();

                Intent toapp = new Intent(RegisterActivity.this,LoginActivity.class);
                toapp.putExtra("tk", user);

                startActivity(toapp);
            }
        }
    private void updateUser() {
        // Khởi tạo RetrofitClient và ApiService
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Tạo một yêu cầu PUT để cập nhật thông tin người dùng
        Call<User> call = apiService.createUser(user);

        // Gửi yêu cầu PUT đến máy chủ
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi cập nhật thành công
                    User updatedUser = response.body();
                    Toast.makeText(RegisterActivity.this, "Cập nhật thông tin người dùng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý khi gặp lỗi từ máy chủ
                    Toast.makeText(RegisterActivity.this, "Cập nhật thông tin người dùng thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối hoặc lỗi khác
                Toast.makeText(RegisterActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
