package com.fh.controller.lottery.questionsandanswers;

import java.util.List;

public class GuessingCompetitionEntity {
	public Integer id;
	public Integer matchId;
	public String startTime;
	public String endTime;
	public Integer scopeOfActivity;
	public Integer bonusType;
	public String guessingTitle;
	public String bonusPool;
	public String limitLotteryAmount;
	public List<QuestionAndAnswersEntity> questionAndAnswersEntityList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getScopeOfActivity() {
		return scopeOfActivity;
	}

	public void setScopeOfActivity(Integer scopeOfActivity) {
		this.scopeOfActivity = scopeOfActivity;
	}

	public Integer getBonusType() {
		return bonusType;
	}

	public void setBonusType(Integer bonusType) {
		this.bonusType = bonusType;
	}

	public String getGuessingTitle() {
		return guessingTitle;
	}

	public void setGuessingTitle(String guessingTitle) {
		this.guessingTitle = guessingTitle;
	}

	public String getBonusPool() {
		return bonusPool;
	}

	public void setBonusPool(String bonusPool) {
		this.bonusPool = bonusPool;
	}

	public String getLimitLotteryAmount() {
		return limitLotteryAmount;
	}

	public void setLimitLotteryAmount(String limitLotteryAmount) {
		this.limitLotteryAmount = limitLotteryAmount;
	}

	public List<QuestionAndAnswersEntity> getQuestionAndAnswersEntityList() {
		return questionAndAnswersEntityList;
	}

	public void setQuestionAndAnswersEntityList(List<QuestionAndAnswersEntity> questionAndAnswersEntityList) {
		this.questionAndAnswersEntityList = questionAndAnswersEntityList;
	}

	@Override
	public String toString() {
		return "GuessingCompetitionEntity [id=" + id + ", matchId=" + matchId + ", startTime=" + startTime + ", endTime=" + endTime + ", scopeOfActivity=" + scopeOfActivity + ", bonusType=" + bonusType + ", guessingTitle=" + guessingTitle + ", bonusPool=" + bonusPool + ", limitLotteryAmount=" + limitLotteryAmount + ", questionAndAnswersEntityList=" + questionAndAnswersEntityList + "]";
	}

	public GuessingCompetitionEntity(Integer id, Integer matchId, String startTime, String endTime, Integer scopeOfActivity, Integer bonusType, String guessingTitle, String bonusPool, String limitLotteryAmount, List<QuestionAndAnswersEntity> questionAndAnswersEntityList) {
		this.id = id;
		this.matchId = matchId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.scopeOfActivity = scopeOfActivity;
		this.bonusType = bonusType;
		this.guessingTitle = guessingTitle;
		this.bonusPool = bonusPool;
		this.limitLotteryAmount = limitLotteryAmount;
		this.questionAndAnswersEntityList = questionAndAnswersEntityList;
	}

	public GuessingCompetitionEntity() {
	}

}
