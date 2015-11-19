package util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.algonquincollege.wieg0002.mvcstopwatch.R;


/**
 * Created by thomaswiegand on 2015-11-06.
 */
public class CustomDialogFragment extends DialogFragment {

    public static String TAG = "ca.surrealranch.custom_dialog";

    public static CustomDialogFragment newInstance(int title, String msg) {
        CustomDialogFragment frag = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putCharSequence("msg", msg);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = getArguments().getInt("title");
        String msg = getArguments().getString("msg","Your Message goes here.");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.custom_dialog, null);

        TextView txtTitle = (TextView)v.findViewById(R.id.dialogTitleTextView);
        txtTitle.setText(getString(R.string.dialog_title));

        TextView txtMsg = (TextView)v.findViewById(R.id.dialogMsgTextView);
        txtMsg.setText(msg);

        builder.setView(v);

        /*
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        */

        final Dialog _me= builder.create();

        Button btnOk = (Button)v.findViewById(R.id.buttonOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _me.dismiss();
            }
        });

        return _me;
    }
}
