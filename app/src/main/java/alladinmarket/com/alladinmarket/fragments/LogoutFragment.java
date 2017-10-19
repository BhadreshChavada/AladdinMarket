package alladinmarket.com.alladinmarket.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.adapters.CartAdaper;

/**
 * Created by nmn on 8/6/17.
 */

public class LogoutFragment extends DialogFragment {


    public interface  DialogClickListener
    {
        public  void positiveClick(Context c) ;

        public  void negativeClick(Context c) ;

    }

    public  DialogClickListener dialogClickListener ;

    public  void setDialogClickListener(DialogClickListener dialogClickListener)
    {
        this.dialogClickListener = dialogClickListener ;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_logout, null);
        builder.setView(dialogView);

        TextView okButton = (TextView) dialogView.findViewById (R.id.tv_ok_logout);
        TextView cancelButton = (TextView) dialogView.findViewById (R.id.tv_cancel_logout);

        okButton.setOnClickListener (new View.OnClickListener () {
            @Override public void onClick (View v) {
                dialogClickListener.positiveClick(getContext());
            }
        });

        cancelButton.setOnClickListener (new View.OnClickListener () {
            @Override public void onClick (View v) {
                dialogClickListener.negativeClick(getContext());
            }
        });

        return builder.create ();
    }


}
