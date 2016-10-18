package Fragments;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.anwar.magineproject.R;

/**
 * network dialoge extends DialogeFragment  for network issue
 */
public class NetworkDialoge
        extends DialogFragment {

    public static int DIALOG_ONE_BUTTON = 1;
    private OnDialogListener onDialogListener;
    int type;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert, container, false);
        TextView warningOK = (TextView) v.findViewById(R.id.warning_ok);
//        warningOK.setText(okey);
        if (type == DIALOG_ONE_BUTTON)
            warningOK.setVisibility(View.GONE);

        warningOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogListener.onNegativeClickListener();
            }
        });

        return v;
    }

    public interface OnDialogListener {

        public void onNegativeClickListener();
    }

    public void OnDialogClickListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }
}