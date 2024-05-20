package com.example.quizapp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "question")
public class question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(255)")
    private String ques;
    @Column(columnDefinition = "nvarchar(255)")
    private String type;
    @Column(columnDefinition = "nvarchar(255)")
    private String difficulty;
    
    @Column(columnDefinition = "nvarchar(255)")
    private String A;
    @Column(columnDefinition = "nvarchar(255)")
    private String B;
    @Column(columnDefinition = "nvarchar(255)")
    private String C;
    @Column(columnDefinition = "nvarchar(255)")
    private String D;
    @Column(columnDefinition = "nvarchar(255)")
    private String answer;
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public question(Long id, String ques, String type, String difficulty, String a, String b, String c, String d,
			String answer) {
		super();
		this.id = id;
		this.ques = ques;
		this.type = type;
		this.difficulty = difficulty;
		A = a;
		B = b;
		C = c;
		D = d;
		this.answer = answer;
	}

	// Constructors
    public question() {
        // Hàm khởi tạo mặc định
    }

	public question(Long id, String ques, String a, String b, String c, String d) {
		super();
		this.id = id;
		this.ques = ques;
		A = a;
		B = b;
		C = c;
		D = d;
	}

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

	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public String getB() {
		return B;
	}

	public void setB(String b) {
		B = b;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getD() {
		return D;
	}

	public void setD(String d) {
		D = d;
	}

}
