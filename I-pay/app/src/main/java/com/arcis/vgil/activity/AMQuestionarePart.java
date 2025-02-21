package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.FifthAnswer;
import com.arcis.vgil.model.FirstAnswer;
import com.arcis.vgil.model.FourthAnswer;
import com.arcis.vgil.model.Questions;
import com.arcis.vgil.model.SecondAnswer;
import com.arcis.vgil.model.Singleton;
import com.arcis.vgil.model.ThirdAnswer;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AMQuestionarePart extends BaseActivity {

	TextView q1, q2, q3, q4, q5;
	Spinner mspinnerAnswer_Q1, mspinnerAnswer_Q2, mspinnerAnswer_Q3,
			mspinnerAnswer_Q4, mspinnerAnswer_Q5;
	// String a1, a2, a3, a4, a5, a6, a7, a8, a9, a10;

	private List<FirstAnswer> answers1 = new ArrayList<FirstAnswer>();
	private List<SecondAnswer> answers2 = new ArrayList<SecondAnswer>();
	private List<ThirdAnswer> answers3 = new ArrayList<ThirdAnswer>();
	private List<FourthAnswer> answers4 = new ArrayList<FourthAnswer>();
	private List<FifthAnswer> answers5 = new ArrayList<FifthAnswer>();

	private String[] arrAnswer_Q1 = null; /* { "Please Select", a1, a2 }; */
	private String[] arrAnswer_Q2 = null;
	private String[] arrAnswer_Q3 = null;
	private String[] arrAnswer_Q4 = null;
	private String[] arrAnswer_Q5 = null;

	private String contactType = null;
	private String visit_Log = null;
	String question_ID = null;
	Button submit;
    EditText meetingNotes;
	@Override
	public void inti() {
		// TODO Auto-generated method stub

		setContentView(R.layout.amquestionpart);
		setCurrentContext(AMQuestionarePart.this);
		Intent intent = getIntent();
		contactType = intent.getStringExtra("CONTACT_TYPE");
		question_ID = intent.getStringExtra("VISITOG");

		mspinnerAnswer_Q1 = (Spinner) findViewById(R.id.answer_Q1);
		mspinnerAnswer_Q2 = (Spinner) findViewById(R.id.answer_Q2);
		mspinnerAnswer_Q3 = (Spinner) findViewById(R.id.answer_Q3);
		mspinnerAnswer_Q4 = (Spinner) findViewById(R.id.answer_Q4);
		mspinnerAnswer_Q5 = (Spinner) findViewById(R.id.answer_Q5);

		submit = (Button) findViewById(R.id.button1);

		submit.setOnClickListener(this);

		q1 = (TextView) findViewById(R.id.editText_Q1);
		q2 = (TextView) findViewById(R.id.editText_Q2);
		q3 = (TextView) findViewById(R.id.editText_Q3);
		q4 = (TextView) findViewById(R.id.editText_Q4);
		q5 = (TextView) findViewById(R.id.editText_Q5);
		meetingNotes=(EditText) findViewById(R.id.editText_meetingnotes);
		GetPartyTypeQuestions();

		mspinnerAnswer_Q1
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView,
                                               View selectedItemView, int position, long id) {
						// your code here

						String ss = answers1.get(position).getAnswerID();
						System.out.println(ss);
						System.out.println(ss);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}

				});

		mspinnerAnswer_Q1
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView,
                                               View selectedItemView, int position, long id) {
						// your code here

						if (position > 0) {
							Singleton.FirstAnswerID = answers1
									.get(position - 1).getAnswerID();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}

				});

		mspinnerAnswer_Q2
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView,
                                               View selectedItemView, int position, long id) {
						// your code here

						if (position > 0) {
							Singleton.SecondAnswerID = answers2.get(
									position - 1).getAnswerID();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}

				});

		mspinnerAnswer_Q3
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView,
                                               View selectedItemView, int position, long id) {
						// your code here

						if (position > 0) {
							Singleton.ThirdAnswerID = answers3
									.get(position - 1).getAnswerID();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}

				});

		mspinnerAnswer_Q4
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView,
                                               View selectedItemView, int position, long id) {
						// your code here

						if (position > 0) {
							Singleton.FourthAnswerID = answers4.get(
									position - 1).getAnswerID();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}

				});

		mspinnerAnswer_Q5
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView,
                                               View selectedItemView, int position, long id) {
						// your code here

						if (position > 0) {
							Singleton.FifthAnswerID = answers5
									.get(position - 1).getAnswerID();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}

				});

	} // end of init

	private void GetPartyTypeQuestions() {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
				getCurrentContext(), ProgressDialog.STYLE_SPINNER,
				"Loading Party..", new GetDataCallBack() {
					@Override
					public void processResponse(Object response) {
						if (response == null) {
							Toast.makeText(getCurrentContext(),
									getResources().getString(R.string.error4),
									Toast.LENGTH_SHORT).show();
						} else {
							if (response != null) {
								ArrayList<Questions> questionList = FetchingdataParser
										.getAMVisitLogQuestions(response
												.toString());
								if (questionList.size() == 0) {
									Toast.makeText(
											getCurrentContext(),
											getResources().getString(
													R.string.message4),
											Toast.LENGTH_SHORT).show();
								} else {

									for (int i = 0; i < questionList.size(); i++) {
										Questions q = questionList.get(i);

										if (q.getQuestionSequenceID()
												.equalsIgnoreCase("1")) {

											Singleton.FirstQuestionID = q
													.getId();

											FirstAnswer firstAnswer = new FirstAnswer();
											firstAnswer.setAnswer(q.getAnswer());
											firstAnswer.setAnswerID(q
													.getAnswerId());

											q1.setVisibility(View.VISIBLE);
											q1.setText("Q.1:" + q.getQuestion());
											q1.setTag("Q.1:" + q.getQuestion());
											// a1 = q.getAnswer();
											// a2 = q.getAnswer();

											answers1.add(firstAnswer);

										} else if (q.getQuestionSequenceID()
												.equalsIgnoreCase("2")) {

											Singleton.SecondQuestionID = q
													.getId();

											SecondAnswer secondAnswer = new SecondAnswer();
											secondAnswer.setAnswer(q
													.getAnswer());
											secondAnswer.setAnswerID(q
													.getAnswerId());
											answers2.add(secondAnswer);

											q2.setVisibility(View.VISIBLE);
											q2.setTag("Q.2:" + q.getQuestion());
											q2.setText("Q.2:" + q.getQuestion());

										} else if (q.getQuestionSequenceID()
												.equalsIgnoreCase("3")) {

											Singleton.ThirdQuestionID = q
													.getId();

											ThirdAnswer thirdAnswer = new ThirdAnswer();
											thirdAnswer.setAnswer(q.getAnswer());
											thirdAnswer.setAnswerID(q
													.getAnswerId());
											answers3.add(thirdAnswer);

											q3.setVisibility(View.VISIBLE);
											q3.setTag("Q.3:" + q.getQuestion());
											q3.setText("Q.3:" + q.getQuestion());

										} else if (q.getQuestionSequenceID()
												.equalsIgnoreCase("4")) {

											Singleton.FourthQuestionID = q
													.getId();

											FourthAnswer fourthAnswer = new FourthAnswer();
											fourthAnswer.setAnswer(q
													.getAnswer());
											fourthAnswer.setAnswerID(q
													.getAnswerId());
											answers4.add(fourthAnswer);

											q4.setVisibility(View.VISIBLE);
											q4.setTag("Q.4:" + q.getQuestion());
											q4.setText("Q.4:" + q.getQuestion());

										} else if (q.getQuestionSequenceID()
												.equalsIgnoreCase("5")) {

											Singleton.FifthQuestionID = q
													.getId();

											FifthAnswer fifthAnswer = new FifthAnswer();
											fifthAnswer.setAnswer(q.getAnswer());
											fifthAnswer.setAnswerID(q
													.getAnswerId());
											answers5.add(fifthAnswer);

											q5.setVisibility(View.VISIBLE);
											q5.setTag("Q.5:" + q.getQuestion());
											q5.setText("Q.5:" + q.getQuestion());

										}

									}

								}

					GetPartyAnswerQuestiont();

							}
						}
					}
				});

		LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails = getSharedPreferences("PASSWORD",
				MODE_PRIVATE);
		try {

			request.put(Constants.contacttype, contactType);
			request.put(Constants.username,
					passworddetails.getString(Constants.ID, ""));
			request.put(Constants.password,
					passworddetails.getString("password", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress = getResources().getString(R.string.ipaddress);
		String webService = getResources().getString(R.string.webService);
		String nameSpace = getResources().getString(R.string.nameSpace);
		String methodName = "GetAllVisitLogQuestionAnswers";
		String soapcomponent = getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName,
				soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:

			SaveAMTerettoryCustomersMeetingLog();

		}
	}

	private void GetPartyAnswerQuestiont() {
		try {

			if (contactType.equalsIgnoreCase("15")) {
				// retailer
				arrAnswer_Q1 = new String[] { "Please Select",
						answers1.get(0).getAnswer(),
						answers1.get(1).getAnswer() };

				arrAnswer_Q2 = new String[] { "Please Select",
						answers2.get(0).getAnswer(),
						answers2.get(1).getAnswer() };

				arrAnswer_Q3 = new String[] { "Please Select",
						answers3.get(0).getAnswer(),
						answers3.get(1).getAnswer(),
						answers3.get(2).getAnswer(),
						answers3.get(3).getAnswer() };

				arrAnswer_Q4 = new String[] { "Please Select",
						answers4.get(0).getAnswer(),
						answers4.get(1).getAnswer() };

				arrAnswer_Q5 = new String[] { "Please Select",
						answers5.get(0).getAnswer(),
						answers5.get(1).getAnswer(),
						answers5.get(2).getAnswer(),
						answers5.get(3).getAnswer(),
						answers5.get(4).getAnswer() };

			} else if (contactType.equalsIgnoreCase("16")) {
				// mechanics
				arrAnswer_Q1 = new String[] { "Please Select",
						answers1.get(0).getAnswer(),
						answers1.get(1).getAnswer() };

				arrAnswer_Q2 = new String[] { "Please Select",
						answers2.get(0).getAnswer(),
						answers2.get(1).getAnswer(),
						answers2.get(2).getAnswer() };
				

				arrAnswer_Q3 = new String[] { "Please Select",
						answers3.get(0).getAnswer(),
						answers3.get(1).getAnswer(),
						answers3.get(2).getAnswer(),
						answers3.get(3).getAnswer() };

				arrAnswer_Q4 = new String[] { "Please Select",
						answers4.get(0).getAnswer(),
						answers4.get(1).getAnswer() };

				arrAnswer_Q5 = new String[] { "Please Select",
						answers5.get(0).getAnswer(),
						answers5.get(1).getAnswer(),
						answers5.get(2).getAnswer(),
						answers5.get(3).getAnswer(),
						answers5.get(4).getAnswer() };

			} else if (contactType.equalsIgnoreCase("14")) {
				// dealer

				arrAnswer_Q1 = new String[] { "Please Select",
						answers1.get(0).getAnswer(),
						answers1.get(1).getAnswer(),
						answers1.get(2).getAnswer(),
						answers1.get(3).getAnswer() };

				arrAnswer_Q2 = new String[] { "Please Select",
						answers2.get(0).getAnswer(),
						answers2.get(1).getAnswer(),
						answers2.get(2).getAnswer(),
						answers2.get(3).getAnswer() };

				arrAnswer_Q3 = new String[] { "Please Select",
						answers3.get(0).getAnswer(),
						answers3.get(1).getAnswer() };

				arrAnswer_Q4 = new String[] { "Please Select",
						answers4.get(0).getAnswer(),
						answers4.get(1).getAnswer() };

				arrAnswer_Q5 = new String[] { "Please Select",
						answers5.get(0).getAnswer(),
						answers5.get(1).getAnswer() };

			}

			ArrayAdapter<String> stateAdapter1 = new ArrayAdapter<String>(
					getCurrentContext(), android.R.layout.simple_spinner_item,
					arrAnswer_Q1);

			stateAdapter1
					.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
			mspinnerAnswer_Q1.setAdapter(stateAdapter1);

			ArrayAdapter<String> stateAdapter2 = new ArrayAdapter<String>(
					getCurrentContext(), android.R.layout.simple_spinner_item,
					arrAnswer_Q2);
			stateAdapter2
					.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
			mspinnerAnswer_Q2.setAdapter(stateAdapter2);

			ArrayAdapter<String> stateAdapter3 = new ArrayAdapter<String>(
					getCurrentContext(), android.R.layout.simple_spinner_item,
					arrAnswer_Q3);
			stateAdapter3
					.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
			mspinnerAnswer_Q3.setAdapter(stateAdapter3);

			ArrayAdapter<String> stateAdapter4 = new ArrayAdapter<String>(
					getCurrentContext(), android.R.layout.simple_spinner_item,
					arrAnswer_Q4);
			stateAdapter4
					.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
			mspinnerAnswer_Q4.setAdapter(stateAdapter4);

			ArrayAdapter<String> stateAdapter5 = new ArrayAdapter<String>(
					getCurrentContext(), android.R.layout.simple_spinner_item,
					arrAnswer_Q5);
			stateAdapter5
					.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
			mspinnerAnswer_Q5.setAdapter(stateAdapter5);
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();

			Toast.makeText(getCurrentContext(), "Data not Found",
					Toast.LENGTH_LONG).show();
		}

	}

	private void SaveAMTerettoryCustomersMeetingLog() {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
				getCurrentContext(), ProgressDialog.STYLE_SPINNER,
				"Uploading data...", new GetDataCallBack() {
					@Override
					public void processResponse(Object result) {

						SoapObject responce = null;
						try {

							responce = (SoapObject) result;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						AlertDialog.Builder errordialog = new AlertDialog.Builder(
								getCurrentContext());
						if (responce == null) {
							errordialog
									.setTitle(getStringFromResource(R.string.error6));
							if (result != null) {
								errordialog.setMessage(result.toString());
							} else {
								errordialog
										.setMessage(getStringFromResource(R.string.error4));
							}

						} else {
							errordialog
									.setMessage(getStringFromResource(R.string.message5));

						}
						errordialog.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if (question_ID == null
												|| "".equals(question_ID)) {
											Intent intent = new Intent(
													AMQuestionarePart.this,
													DailyCallsActivity.class);
											startActivity(intent);
										} else {
											Intent intent = new Intent(
													AMQuestionarePart.this,
													PendingCalls.class);
											intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				        					   startActivity(intent);
				        					   finish();
										}
									}
								});
						AlertDialog dialog = errordialog.create();
						dialog.show();
					}
				});

		LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails = getSharedPreferences("PASSWORD",
				MODE_PRIVATE);

		try {

			/*
			 * Function SaveVisitLogDetails(ByVal VisitLogID As String, ByVal
			 * QuestionID As String, ByVal AnswerID As String, ByVal UserID As
			 * String, ByVal Password As String) As String
			 */
			if (question_ID == null || "".equals(question_ID)) {
				request.put(Constants.visitLogID, Singleton.VisitLogID);
			} else {
				request.put(Constants.visitLogID, question_ID);
			}

			request.put(Constants.q1, Singleton.FirstQuestionID);
			request.put(Constants.answer1, Singleton.FirstAnswerID);

			request.put(Constants.q2, Singleton.SecondQuestionID);
			request.put(Constants.answer2, Singleton.SecondAnswerID);

			request.put(Constants.q3, Singleton.ThirdQuestionID);
			request.put(Constants.answer3, Singleton.ThirdAnswerID);

			request.put(Constants.q4, Singleton.FourthQuestionID);
			request.put(Constants.answer4, Singleton.FourthAnswerID);

			request.put(Constants.q5, Singleton.FifthQuestionID);
			request.put(Constants.answer5, Singleton.FifthAnswerID);
			
			if (meetingNotes.getText().toString().length()>0) {
				request.put("MeetingNotes", meetingNotes.getText().toString());
			}
			else {
				request.put("MeetingNotes", "");
			}
			
	           
			request.put(Constants.username,
					passworddetails.getString(Constants.ID, ""));
			request.put(Constants.password,
					passworddetails.getString("password", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress = getResources().getString(R.string.ipaddress);
		String webService = getResources().getString(R.string.webService);
		String nameSpace = getResources().getString(R.string.nameSpace);
		String methodName = "SaveAllVisitLogDetails";
		String soapcomponent = getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName,
				soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();

	}

}
