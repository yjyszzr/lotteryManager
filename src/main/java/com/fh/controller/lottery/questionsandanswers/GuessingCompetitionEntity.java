package com.fh.controller.lottery.questionsandanswers;

import java.util.List;

public class GuessingCompetitionEntity {
	public Integer id;
	public Integer matchId;
	public String startTime;
	public String endTime;
	public Integer scopeOfActivity;
	public Integer numOfPeople;
	public String guessingTitle;
	public String bonusPool;
	public String limitLotteryAmount;
	public Integer answerShowTime;
	public String period;
	public Integer prizewinningNum;
	public Integer awardTime;
	public Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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

	public Integer getNumOfPeople() {
		return numOfPeople;
	}

	public void setNumOfPeople(Integer numOfPeople) {
		this.numOfPeople = numOfPeople;
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

	public Integer getAnswerShowTime() {
		return answerShowTime;
	}

	public void setAnswerShowTime(Integer answerShowTime) {
		this.answerShowTime = answerShowTime;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Integer getPrizewinningNum() {
		return prizewinningNum;
	}

	public void setPrizewinningNum(Integer prizewinningNum) {
		this.prizewinningNum = prizewinningNum;
	}

	public Integer getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Integer awardTime) {
		this.awardTime = awardTime;
	}

	public List<QuestionAndAnswersEntity> getQuestionAndAnswersEntityList() {
		return questionAndAnswersEntityList;
	}

	public void setQuestionAndAnswersEntityList(List<QuestionAndAnswersEntity> questionAndAnswersEntityList) {
		this.questionAndAnswersEntityList = questionAndAnswersEntityList;
	}

}
