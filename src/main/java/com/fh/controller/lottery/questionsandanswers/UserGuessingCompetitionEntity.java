package com.fh.controller.lottery.questionsandanswers;

import java.math.BigDecimal;
import java.util.List;

public class UserGuessingCompetitionEntity {
	public Integer id;
	public Integer questionId;
	public Integer matchId;
	public Integer userId;
	public String period;
	public Integer answerTime;
	public Integer getAward;
	public BigDecimal bonusAmount;
	public List<QuestionAndAnswersEntity> userAnswerList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Integer getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Integer answerTime) {
		this.answerTime = answerTime;
	}

	public Integer getGetAward() {
		return getAward;
	}

	public void setGetAward(Integer getAward) {
		this.getAward = getAward;
	}

	public BigDecimal getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(BigDecimal bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public List<QuestionAndAnswersEntity> getUserAnswerList() {
		return userAnswerList;
	}

	public void setUserAnswerList(List<QuestionAndAnswersEntity> userAnswerList) {
		this.userAnswerList = userAnswerList;
	}

}
