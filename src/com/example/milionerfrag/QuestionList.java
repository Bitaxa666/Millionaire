package com.example.milionerfrag;

public class QuestionList
{
	private Question[] mQuestion;
	private int count;
	private int yourCash;
	/*
	 * new Question for Game
	 */
	public QuestionList() {
		this.mQuestion = new Question[]{
				new Question("������ ����� ����?","�����","�������","�������","Ƹ����","�����"),
				new Question("����� ����� ����?","�������","����","����","�����","����"),
				new Question("������� ������� �� �����?","���","����","���","����","����"),
				new Question("� ������ ���� �������� ��������� �����?","�����","����","�����","������","�����"),
				new Question("����� ����� ����������?", "����-����", "���������", "��������", "���������","���������"),
				new Question("����� ���������� ������� � ������ ������", "�����", "�����", "�����", "�����","�����"),
				new Question("��������� ���� ����������� ����������?", "�������", "�����", "�����", "�����","�����"),
				new Question("���� ���������� �� ����������� ������ � ���������� ��������?", "��������", "���������", "�������-���", "�����","��������"),
				new Question("������� �������� � 1 ���������?", "100500", "1024", "2048", "512","1024")
		};
	}

	public Question[] getmQuestion() {
		return mQuestion;
	}

	public void setmQuestion(Question[] mQuestion) {
		this.mQuestion = mQuestion;
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getYourCash() {
		return yourCash;
	}

	public void setYourCash(int yourCash) {
		this.yourCash = yourCash;
	}
	
}
