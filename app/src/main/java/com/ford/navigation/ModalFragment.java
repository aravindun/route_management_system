package com.ford.navigation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ModalFragment extends DialogFragment {
    Button btnYes;
    static String DialogBoxTitle;
    static String DialogBoxContent;

    TextView popUpContent;

    //---empty constructor required
    public ModalFragment() {

    }

    //---set the title of the dialog window---
    public void setDialogTitle(String title) {
        DialogBoxTitle = title;
    }

    public void setDialogContent(String content) {
        DialogBoxContent = content;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_pop_up, container);
        btnYes = (Button) view.findViewById(R.id.btnYes);
        popUpContent = view.findViewById(R.id.pop_up_content);
        popUpContent.setText(DialogBoxContent);
        btnYes.setOnClickListener(btnListener);

        getDialog().setTitle(DialogBoxTitle);

        animation(view);

        return view;
    }

    private void animation(View view) {
        ImageView wheel = (ImageView)view.findViewById(R.id.animation);

        AnimatorSet wheelSet = (AnimatorSet)
                AnimatorInflater.loadAnimator(getContext(), R.animator.sun_swing);
        //set the view as target
        wheelSet.setTarget(wheel);
        //start the animation
        wheelSet.start();

    }

    private OnClickListener btnListener = new OnClickListener() {
        public void onClick(View v) {
            dismiss();
        }
    };
}