package com.example.milionerfrag;

public class Question 
{
	// values in a question
	private String question;
	private String StringTrueQuestion;
	private String answerQuestion1;
	private String answerQuestion2;
	private String answerQuestion3;
	private String answerQuestion4;
	
	public Question(String question, 
			String answerQuestion1, String answerQuestion2,
			String answerQuestion3, String answerQuestion4, String StringTrueQuestion)
	{
		this.question = question;
		this.StringTrueQuestion = StringTrueQuestion;
		this.answerQuestion1 = answerQuestion1;
		this.answerQuestion2 = answerQuestion2;
		this.answerQuestion3 = answerQuestion3;
		this.answerQuestion4 = answerQuestion4;
		
	}
	

	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getStringTrueQuestion() {
		return StringTrueQuestion;
	}

	public String getAnswerQuestion1() {
		return answerQuestion1;
	}

	public String getAnswerQuestion2() {
		return answerQuestion2;
	}

	public String getAnswerQuestion3() {
		return answerQuestion3;
	}

	public String getAnswerQuestion4() {
		return answerQuestion4;
	}

	@Override
	public String toString() {
		return "Question [question=" + question + ", StringTrueQuestion="
				+ StringTrueQuestion + ", answerQuestion1=" + answerQuestion1
				+ ", answerQuestion2=" + answerQuestion2 + ", answerQuestion3="
				+ answerQuestion3 + ", answerQuestion4=" + answerQuestion4
				+ "]";
	}
	
	
	
	

}
