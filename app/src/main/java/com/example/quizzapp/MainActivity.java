package com.example.quizzapp;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<User> lu, luCopy;

    public boolean isMusicPlaying = true;
    private TextView u, sokc;
    private User x;
    private Button choingay, thongtincanhan, xephang, setting, cuahang;
    Dialog dialog;
    private List<String> luNew;
    List<Integer> lavatar = new ArrayList<>();
    List<Integer> lchude = new ArrayList<>();
    private Intent musicServiceIntent;
    private ViewGroup layout;
    private int bg;
    private int avatar;
    private ImageView ava;
     public int z ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.ques);

        {
            lavatar.add(R.drawable.img_1);
            lavatar.add(R.drawable.img_2);
            lavatar.add(R.drawable.img_3);
            lavatar.add(R.drawable.img_4);
            lavatar.add(R.drawable.img_5);
            lavatar.add(R.drawable.img_6);
            lavatar.add(R.drawable.img_7);
            lavatar.add(R.drawable.img_8);
            lavatar.add(R.drawable.img_9);
            lavatar.add(R.drawable.img_10);





        //imageList.add(R.drawable.person3);
        //imageList.add(R.drawable.person4);
        //imageList.add(R.drawable.person5);
        //imageList.add(R.drawable.person6);
        //imageList.add(R.drawable.person7);
        //imageList.add(R.drawable.person8);
        //imageList.add(R.drawable.person9);
        // Thêm các hình ảnh khác vào danh sách
            }
        musicServiceIntent = new Intent(MainActivity.this, MusicService.class);


        {
            lchude.add(R.drawable.t0);
            lchude.add(R.drawable.t1);
            lchude.add(R.drawable.t2);
            lchude.add(R.drawable.t3);
            //imageList.add(R.drawable.person4);
            //imageList.add(R.drawable.person5);
            //imageList.add(R.drawable.person6);
            //imageList.add(R.drawable.person7);
            //imageList.add(R.drawable.person8);
            //imageList.add(R.drawable.person9);
            // Thêm các hình ảnh khác vào danh sách
        }
        lu = new ArrayList<>();
        z = getIntent().getIntExtra("chude",z);
        setMainActivityBackground(z);
        getListUsers();bg=0;
        xephang = findViewById(R.id.button3);
        u = findViewById(R.id.textView5);
        sokc = findViewById(R.id.textView8);
        choingay = findViewById(R.id.button1);
        thongtincanhan = findViewById(R.id.button2);
        cuahang = findViewById(R.id.button4);
        cuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch();
            }
        });
        thongtincanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hientt();
            }
        });

        x = getIntent().getParcelableExtra("tk");
        u.setText(x.getUsername());
        sokc.setText(String.valueOf(x.getKimcuong()));
        avatar = x.getAvatar();
        ava = findViewById(R.id.image_logo);
        ava.setImageResource(avatar);
        choingay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tostart = new Intent(MainActivity.this, StartActivity.class);
                tostart.putExtra("tk", x);
                tostart.putExtra("cd",z);
                    tostart.putExtra("bg",bg);

                startActivity(tostart);
            }
        });

        xephang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xh();
            }
        });

        setting = findViewById(R.id.button5);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting();
            }
        });

        Button dangxuat = findViewById(R.id.button6);
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backlogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(backlogin);
            }
        });

    }
//end
    private void getListUsers() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                lu = response.body();
                // Gọi phương thức để xử lý dữ liệu người dùng sau khi lấy dữ liệu từ API
                handleUserData();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Thêm phương thức này để sao chép và sắp xếp danh sách người dùng sau khi lấy dữ liệu từ API
    private void handleUserData() {
        luCopy = new ArrayList<>(lu);
        Collections.sort(luCopy, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user2.getScore() - user1.getScore();
            }
        });

        luNew = new ArrayList<>();
        int i = 0;
        int t =0;
        int k=0;
        for (User q : luCopy) {
            if(i==0){
                luNew.add("Hạng "+(i+1)+" "+q.getUsername()+" điểm: "+ q.getScore());
                t=i+1;
            } else {
                if(q.getScore() == k){
                    luNew.add("Hạng "+t+" "+q.getUsername()+" điểm: "+ q.getScore());
                } else {
                    luNew.add("Hạng "+(i+1)+" "+q.getUsername()+" điểm: "+ q.getScore());
                    t=i+1;
                }
            }
             k = q.getScore();

            i++;
            if (i == 49) {
                break;
            }
        }
    }

    private void hientt() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_profile);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.75));
        TextView t1, t2, t3, t4, t5, t6;
        t1 = dialog.findViewById(R.id.user_label);
        t2 = dialog.findViewById(R.id.tv16);
        t3 = dialog.findViewById(R.id.tv2);
        t4 = dialog.findViewById(R.id.tv3);
        t5 = dialog.findViewById(R.id.tv4);
        t6 = dialog.findViewById(R.id.tv1);
        t1.setText("Người chơi " + x.getUsername());
        t2.setText("Số câu đã trả lời " + x.getTimep());
        t3.setText("Với số câu đúng " + x.getScore() / 10);
        t4.setText("Số câu trả lời sai " + (x.getTimep() - x.getScore() / 10));

        if (x.getTimep() != 0) {
            DecimalFormat df = new DecimalFormat("#.##"); // Số # biểu thị cho một chữ số có thể có hoặc không.
            double rounded = Double.parseDouble(df.format((x.getScore() *10) / x.getTimep())); // rounded = 5.68
            t5.setText("Tỉ lệ trả lời đúng " + rounded + "%");
        } else {
            t5.setText("Tỉ lệ trả lời đúng là 0");
        }
        t6.setText("Tổng số điểm " + x.getScore());
        dialog.show();
        Button btb = dialog.findViewById(R.id.return_button);
        btb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void xh() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_ranking);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.75));

        ListView lv;
        lv = dialog.findViewById(R.id.leaderboardListView);

        // Tạo CustomAdapter
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, R.layout.dapan, luNew);

        // Thiết lập CustomAdapter cho ListView
        lv.setAdapter(adapter);

        dialog.show();
        Button backmain = dialog.findViewById(R.id.backToMainButton);
        backmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void setting(){

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.setting);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.6));
        Button b1,b2,b3,b4,b5;
        b1= dialog.findViewById(R.id.buttonSound);
        if(MusicService.isRunning()){
            b1.setText("Đang bật nhạc");
        } else {
            b1.setText("Nhạc đã tắt");
        }
        b2= dialog.findViewById(R.id.buttonTheme);
        b3= dialog.findViewById(R.id.buttonAvatar);
        b4= dialog.findViewById(R.id.buttonSnow);
        b5= dialog.findViewById(R.id.buttonDeleteAccount);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MusicService.isRunning()){
                    b1.setText("Nhạc đã tắt");
                    isMusicPlaying = false;
                    stopService(musicServiceIntent);
                } else {
                    b1.setText("Đang bật nhạc");

                    startService(musicServiceIntent);
                }

            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chont();
            }
        });

        dialog.show();
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonlogo();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog2 = new Dialog(MainActivity.this);
                dialog2.setContentView(R.layout.bottom_sheet_layout);
                dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.4));
                GridView gr = dialog2.findViewById(R.id.gridViewAvatars);
                List<String> ln = new ArrayList<>();
                ln.add("Chưa");
                ln.add("Xóa");
                ArrayAdapter aaa = new ArrayAdapter(MainActivity.this, R.layout.dapan,ln);
                gr.setAdapter(aaa);
                dialog2.show();
                        gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String S = aaa.getItem(position).toString();
                                if(S.equals("Chưa")){
                                    dialog2.dismiss();
                                } else {
                                    del();
                                    Intent tologin = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(tologin);
                                }

                            }
                        });
            }
        });
    }
    private void chonlogo(){
        Dialog dialog1 = new Dialog(MainActivity.this);
        dialog1.setContentView(R.layout.bottom_sheet_layout);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.75));

        // Lấy GridView từ layout
        GridView gridView = dialog1.findViewById(R.id.gridViewAvatars);


        // Tạo danh sách hình ảnh
        List<Integer> imageList = new ArrayList<>(lavatar);


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
        dialog1.show();
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
                x.setAvatar(avatar);
                updateUser();
                // Đóng dialog sau khi chọn hình ảnh
                dialog1.dismiss();
            }
        });
    }
    private void chont() {
        Dialog ndialog = new Dialog(MainActivity.this);
        ndialog.setContentView(R.layout.bottom_sheet_layout);
        ndialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.75));

        // Lấy GridView từ layout
        GridView gridView = ndialog.findViewById(R.id.gridViewAvatars);

        // Tạo danh sách hình ảnh
        List<Integer> imageList = new ArrayList<>(lchude);

        // Tạo adapter cho GridView
        ImageAdapter adapter = new ImageAdapter(imageList);

        // Đặt adapter cho GridView
        gridView.setAdapter(adapter);
        ndialog.show();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Set the clicked image as the background of MainActivity
                int selectedImageResourceId = imageList.get(position);
                setMainActivityBackground(selectedImageResourceId);
                z = selectedImageResourceId;
                // Dismiss the dialog after selecting an image
                ndialog.dismiss();
            }
        });
    }

    private void setMainActivityBackground(int imageResourceId) {

        findViewById(R.id.ques).setBackgroundResource(imageResourceId);
        bg = imageResourceId;

    }
    private void updateUser() {
        // Khởi tạo RetrofitClient và ApiService
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Tạo một yêu cầu PUT để cập nhật thông tin người dùng
        Call<User> call = apiService.updateUser(x.getId(),x);

        // Gửi yêu cầu PUT đến máy chủ
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi cập nhật thành công
                    User updatedUser = response.body();
                    Toast.makeText(MainActivity.this, "cập nhật avatar thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý khi gặp lỗi từ máy chủ
                    Toast.makeText(MainActivity.this, "Cập nhật avatar thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối hoặc lỗi khác
                Toast.makeText(MainActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void del(){
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<User> call = apiService.deleteUser(x.getId());

        // Gửi yêu cầu PUT đến máy chủ
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi cập nhật thành công
                    User updatedUser = response.body();
                    Toast.makeText(MainActivity.this, " Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý khi gặp lỗi từ máy chủ
                    Toast.makeText(MainActivity.this, "Không xóa được tài khoản", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối hoặc lỗi khác
                Toast.makeText(MainActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ch(){
        Dialog dc = new Dialog(MainActivity.this);
        dc.setContentView(R.layout.shop);
        dc.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDisplayMetrics().heightPixels * 0.75));
        Button s1,s2,s3,s4;
        dc.show();
        s1= dc.findViewById(R.id.button9);
        s2= dc.findViewById(R.id.button10);
        s3 = dc.findViewById(R.id.button11);
        s4 = dc.findViewById(R.id.button12);
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Chưa hỗ trợ trên app ", Toast.LENGTH_SHORT).show();
            }
        });
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Chưa hỗ trợ trên app ", Toast.LENGTH_SHORT).show();
            }
        });
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Chưa hỗ trợ trên app ", Toast.LENGTH_SHORT).show();
            }
        });
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.dismiss();
            }
        });
    }
}
