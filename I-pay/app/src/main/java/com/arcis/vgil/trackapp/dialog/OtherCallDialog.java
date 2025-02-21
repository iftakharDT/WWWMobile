package com.arcis.vgil.trackapp.dialog;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.fragment.NavigationOtherFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class OtherCallDialog extends DialogFragment {



    private View rootView;
    private NavigationOtherFragment currentFragment;
    private CountDownTimer countDownTimer;
    private TextView tv_count_down_timer;
    private TextView tv_call_start_date_time;
    private String dateTime;

    public static OtherCallDialog newInstance(NavigationOtherFragment fragment, String dateTime) {
        OtherCallDialog dialog = new OtherCallDialog();
        dialog.currentFragment = fragment;
        dialog.dateTime = dateTime;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
       final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle(R.string.call_dialog_title)
                .setCancelable(false)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
               // onDialogShow(alertDialog);
            }
        });

        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.call_dialog, null, false);

          tv_count_down_timer = rootView.findViewById(R.id.tv_count_down_timer);
        tv_call_start_date_time = rootView.findViewById(R.id.tv_call_start_date_time);
        View[] images = {rootView.findViewById(R.id.img1), rootView.findViewById(R.id.img2), rootView.findViewById(R.id.img3), rootView.findViewById(R.id.img4)}; //array of views that we want to animate

        //we will have 2 animator foreach view, fade in & fade out
        //prepare animators - creating array of animators & instantiating Object animators
        ArrayList<ObjectAnimator> anims = new ArrayList<>(images.length * 2);
        for (View v : images) anims.add(ObjectAnimator.ofFloat(v, View.ALPHA, 0f, 1f).setDuration(80)); //fade in animator
        for (View v : images) anims.add(ObjectAnimator.ofFloat(v, View.ALPHA, 1f, 0f).setDuration(80)); //fade out animator

        final AnimatorSet set = new AnimatorSet(); //create Animator set object
        //if we want to repeat the animations then we set listener to start again in 'onAnimationEnd' method
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                set.start(); //repeat animator set indefinitely
            }
        });

        set.setStartDelay(600); //set delay every time we start the chain of animations

        for (int i = 0; i < anims.size() - 1; i++) set.play(anims.get(i)).before(anims.get(i + 1)); //put all animations in set by order (from first to last)

         set.start();

         if(dateTime!=null)
             tv_call_start_date_time.setText(dateTime);


        rootView.findViewById(R.id.iv_end_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //start the animations on click

                    android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                            getActivity());
                    alertDialog.setMessage("Your have an Ongoing call which will be disconnected. Are you sure to exit?");
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            cancelCountDownTimer();
                            currentFragment.saveAMTerettoryCustomersMeetingLog(false);
                            dialog.dismiss();
                            dismissAllowingStateLoss();

                        }
                    });

                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    android.app.AlertDialog dialog = alertDialog.create();
                    dialog.setCancelable(true);
                    dialog.show();




            }
        });

        startCountDownTimer();
    }


    private void startCountDownTimer(){


        countDownTimer = new CountDownTimer(2*60*60*1000, 1000) {                     //geriye sayma

            public void onTick(long millisUntilFinished) {

                try {

                    NumberFormat f = new DecimalFormat("00");
                    long hour = (millisUntilFinished / 3600000) % 24;
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;
                    tv_count_down_timer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            public void onFinish() {
                tv_count_down_timer.setText("00:00:00");
                currentFragment.saveAMTerettoryCustomersMeetingLog(true);
                dismissAllowingStateLoss();
            }
        }.start();

    }

    private void cancelCountDownTimer(){

        if(countDownTimer!=null)
            countDownTimer.cancel();
    }


   /* private void onDialogShow(AlertDialog dialog) {
       *//* Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // onDoneClicked();
            }
        });*//*

    }*/

   /* private void onDoneClicked() {
        if (isAValidName(player1Layout, player1) & isAValidName(player2Layout, player2)) {
            activity.onPlayersSet(player1, player2);
            dismiss();
        }
    }*/




}
