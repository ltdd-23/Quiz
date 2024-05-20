package com.example.quizzapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

public class question implements Parcelable {
    private Long id;
    private String ques;
    private String type;
    private String difficulty;

    private String a; // Thay đổi tên thành a
    private String b; // Thay đổi tên thành b
    private String c; // Thay đổi tên thành c
    private String d; // Thay đổi tên thành d
    private String answer;


    public question(Long id, String ques, String type, String difficulty, String a, String b, String c, String d, String answer) {
        this.id = id;
        this.ques = ques;
        this.type = type;
        this.difficulty = difficulty;
        this.a = a; // Thay đổi tên thành a
        this.b = b; // Thay đổi tên thành b
        this.c = c; // Thay đổi tên thành c
        this.d = d; // Thay đổi tên thành d
        this.answer = answer;
    }

    protected question(Parcel in) {
        id = in.readLong();
        ques = in.readString();
        type = in.readString();
        difficulty = in.readString();
        a = in.readString(); // Thay đổi tên thành a
        b = in.readString(); // Thay đổi tên thành b
        c = in.readString(); // Thay đổi tên thành c
        d = in.readString(); // Thay đổi tên thành d
        answer = in.readString();
    }

    public static final Creator<question> CREATOR = new Creator<question>() {
        @Override
        public question createFromParcel(Parcel in) {
            return new question(in);
        }

        @Override
        public question[] newArray(int size) {
            return new question[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(ques);
        dest.writeString(type);
        dest.writeString(difficulty);
        dest.writeString(a); // Thay đổi tên thành a
        dest.writeString(b); // Thay đổi tên thành b
        dest.writeString(c); // Thay đổi tên thành c
        dest.writeString(d); // Thay đổi tên thành d
        dest.writeString(answer);
    }
}
