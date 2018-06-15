package com.fh.controller.lottery.questionsandanswers;

public class QuestionAndAnswersEntity {
	/**
	 * 题设
	 */
	public String questionSetting;
	/**
	 * 答案1
	 */
	public String answerSetting1;
	/**
	 * 答案1状态
	 */
	public Integer answerStatus1;
	/**
	 * 答案2
	 */
	public String answerSetting2;
	/**
	 * 答案2状态
	 */
	public Integer answerStatus2;

	public String getQuestionSetting() {
		return questionSetting;
	}

	public void setQuestionSetting(String questionSetting) {
		this.questionSetting = questionSetting;
	}

	public String getAnswerSetting1() {
		return answerSetting1;
	}

	public void setAnswerSetting1(String answerSetting1) {
		this.answerSetting1 = answerSetting1;
	}

	public Integer getAnswerStatus1() {
		return answerStatus1;
	}

	public void setAnswerStatus1(Integer answerStatus1) {
		this.answerStatus1 = answerStatus1;
	}

	public String getAnswerSetting2() {
		return answerSetting2;
	}

	public void setAnswerSetting2(String answerSetting2) {
		this.answerSetting2 = answerSetting2;
	}

	public Integer getAnswerStatus2() {
		return answerStatus2;
	}

	public void setAnswerStatus2(Integer answerStatus2) {
		this.answerStatus2 = answerStatus2;
	}

	@Override
	public String toString() {
		return "QuestionAndAnswersEntity [questionSetting=" + questionSetting + ", answerSetting1=" + answerSetting1 + ", answerStatus1=" + answerStatus1 + ", answerSetting2=" + answerSetting2 + ", answerStatus2=" + answerStatus2 + "]";
	}

	public QuestionAndAnswersEntity(String questionSetting, String answerSetting1, Integer answerStatus1, String answerSetting2, Integer answerStatus2) {
		this.questionSetting = questionSetting;
		this.answerSetting1 = answerSetting1;
		this.answerStatus1 = answerStatus1;
		this.answerSetting2 = answerSetting2;
		this.answerStatus2 = answerStatus2;
	}

	public QuestionAndAnswersEntity() {
	}

}
