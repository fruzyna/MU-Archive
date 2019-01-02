package com.mail929.android.restaurantadvisor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

/**
 * Created by mail929 on 11/29/17.
 */

public class NewReviewFragment extends Fragment
{
	EditText title;
	RatingBar rating;
	EditText body;
	Button submit;

	int restid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.review_fragment, container, false);

		restid = getArguments().getInt("restid");

		title = view.findViewById(R.id.newreview_title);
		rating = view.findViewById(R.id.newreview_rating);
		body = view.findViewById(R.id.newreview_body);
		submit = view.findViewById(R.id.newreview_submit);

		submit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				(new SubmitTask()).execute();
			}
		});

		return view;
	}

	ProgressDialog dialog;
	protected class SubmitTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected void onPreExecute() {
			FragmentManager fm = getFragmentManager();
			dialog = ProgressDialog.newInstance("Submitting");
			dialog.show(fm, "Progress");
		}
		protected List<Object[]> doInBackground(Void... unused) {
			//return SQL.query("login/?email=" + email.getText().toString() + "&password=" + password.getText().toString());
			return SQL.query("addreview/?email=" + ((RAActivity) getActivity()).user + "&restid=" + restid + "&title=" + title.getText().toString() + "&body=" + body.getText().toString() + "&rating=" + rating.getRating());
		}
		protected void onPostExecute(List<Object[]> result) {
			dialog.dismiss();
			//if(result != null && result.size() > 0 && result.get(0)[3].equals(email.getText().toString()))
			if(result != null && result.size() > 0 && result.get(0)[0].equals("Success"))
			{
				//success
				getActivity().getFragmentManager().popBackStack();
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Bad Review! Fix and try again.", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
