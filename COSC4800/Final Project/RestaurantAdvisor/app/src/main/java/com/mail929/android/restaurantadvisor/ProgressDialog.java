package com.mail929.android.restaurantadvisor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mail929 on 11/28/17.
 */

public class ProgressDialog extends DialogFragment
{
	String text = "Loading";

	public static ProgressDialog newInstance(String text)
	{
		ProgressDialog dialog = new ProgressDialog();
		dialog.text = text;
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.loading_spinner, null);
		TextView textV = view.findViewById(R.id.loading_text);
		textV.setText(text);
		builder.setView(view);
		return builder.create();
	}
}