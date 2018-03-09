package com.ford.navigation;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ford.navigation.R;

public class ModalFragment extends DialogFragment {
    Button btnYes;
    static String DialogBoxTitle;
    static String DialogBoxContent;

    TextView popUpContent;

    public interface YesNoDialogListener {
        void onFinishYesNoDialog(boolean state);
    }

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

        return view;
    }

    private OnClickListener btnListener = new OnClickListener() {
        public void onClick(View v) {
            dismiss();
        }
    };
}