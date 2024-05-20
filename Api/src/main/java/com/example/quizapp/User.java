package com.example.quizapp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    @Column(columnDefinition = "nvarchar(255)")
    private String username;
    @Column(columnDefinition = "nvarchar(255)")
    private String password;
   
    private int score;
    @Column(columnDefinition = "nvarchar(255)")
    private String questionbaomat;
    @Column(columnDefinition = "nvarchar(255)")
    private String answerbaomat;
    private int timep;
    private int kimcuong;
    private int avatar;
	public User(Long id, String username, String password, int score, String questionbaomat, String answerbaomat,
			int timep, int kimcuong, int avatar) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.score = score;
		this.questionbaomat = questionbaomat;
		this.answerbaomat = answerbaomat;
		this.timep = timep;
		this.kimcuong = kimcuong;
		this.avatar = avatar;
	}
	public User() {
		
	}
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
	public String getQuestionbaomat() {
		return questionbaomat;
	}
	public void setQuestionbaomat(String questionbaomat) {
		this.questionbaomat = questionbaomat;
	}
	public String getAnswerbaomat() {
		return answerbaomat;
	}
	public void setAnswerbaomat(String answerbaomat) {
		this.answerbaomat = answerbaomat;
	}
	public int getTimep() {
		return timep;
	}
	public void setTimep(int timep) {
		this.timep = timep;
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
	
}
