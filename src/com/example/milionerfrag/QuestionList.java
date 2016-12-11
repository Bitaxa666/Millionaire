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
				new Question("Какого цвета небо?","Синее","Зеленое","Красное","Жёлтое","Синее"),
				new Question("Какой формы круг?","Квадрат","Ромб","Круг","Элипс","Круг"),
				new Question("Сколько пальцев на руках?","Три","Один","Два","Пять","Пять"),
				new Question("К какому типу животных относится кошка?","Свини","Гуси","Кошки","Собаки","Кошки"),
				new Question("Какая птица существует?", "Хала-хала", "Кренделек", "Коврижка", "Каравайка","Каравайка"),
				new Question("Какая называется веревка с петлей вконце", "Кассо", "Лассо", "Массо", "Засов","Лассо"),
				new Question("Начальный этап спортивного состязания?", "Перерыв", "Финиш", "Брейк", "Старт","Старт"),
				new Question("Слой образующий на поверхности железа в результате коррозии?", "Ржавчина", "Спаивание", "Сварной-шов", "Нагар","Ржавчина"),
				new Question("Сколько килобайт в 1 мегабайте?", "100500", "1024", "2048", "512","1024")
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
