package com.example.quizzapp;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private Long id;
    private String username;
    private String password;
    private int score;
    private String answerbaomat;
    private String questionbaomat;
    private int kimcuong;
    private int avatar;
    private int timep; // Thêm trường timep

    // Constructors
    public User(Long id, String username, String password, int score, String answerbaomat, String questionbaomat, int kimcuong, int avatar, int timep) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.score = score;
        this.answerbaomat = answerbaomat;
        this.questionbaomat = questionbaomat;
        this.kimcuong = kimcuong;
        this.avatar = avatar;
        this.timep = timep; // Khởi tạo trường timep
    }
    public User(){

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAnswerbaomat() {
        return answerbaomat;
    }

    public void setAnswerbaomat(String answerbaomat) {
        this.answerbaomat = answerbaomat;
    }

    public String getQuestionbaomat() {
        return questionbaomat;
    }

    public void setQuestionbaomat(String questionbaomat) {
        this.questionbaomat = questionbaomat;
    }

    public int getKimcuong() {
        return kimcuong;
    }

    public void setKimcuong(int kimcuong) {
        this.kimcuong = kimcuong;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getTimep() {
        return timep;
    }

    public void setTimep(int timep) {
        this.timep = timep;
    }

    // Parcelable implementation
    protected User(Parcel in) {
        id = in.readLong();
        username = in.readString();
        password = in.readString();
        score = in.readInt();
        answerbaomat = in.readString();
        questionbaomat = in.readString();
        kimcuong = in.readInt();
        avatar = in.readInt();
        timep = in.readInt(); // Đọc giá trị của timep
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeInt(score);
        dest.writeString(answerbaomat);
        dest.writeString(questionbaomat);
        dest.writeInt(kimcuong);
        dest.writeInt(avatar);
        dest.writeInt(timep); // Ghi giá trị của timep
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
