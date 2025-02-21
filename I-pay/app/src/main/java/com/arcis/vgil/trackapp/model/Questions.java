package com.arcis.vgil.trackapp.model;

public class Questions {

	private String id;
	private String question;
	private String contacttypeid;
	private String active;
	private String answerId;
	private String answer; 
	private String setvisitLogID;
	private String QuestionSequenceID;
	
	public String getQuestionSequenceID() {
		return QuestionSequenceID;
	}
	public void setQuestionSequenceID(String questionSequenceID) {
		QuestionSequenceID = questionSequenceID;
	}
	public String getSetvisitLogID() {
		return setvisitLogID;
	}
	public void setSetvisitLogID(String setvisitLogID) {
		this.setvisitLogID = setvisitLogID;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getContacttypeid() {
		return contacttypeid;
	}
	public void setContacttypeid(String contacttypeid) {
		this.contacttypeid = contacttypeid;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	
	
}
