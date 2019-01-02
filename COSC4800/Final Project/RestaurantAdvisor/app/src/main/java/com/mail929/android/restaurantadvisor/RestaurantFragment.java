package com.mail929.android.restaurantadvisor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by mail929 on 11/29/17.
 */

public class RestaurantFragment extends Fragment implements View.OnClickListener
{
	TextView name;
	TextView cuisine;
	TextView type;
	TextView rating;
	TextView price;
	TextView address;
	TextView hours;
	Button phone;
	Button email;
	CheckBox outdoor;
	CheckBox delivery;
	Button menu;
	LinearLayout highlights;
	Button newReview;
	LinearLayout ratings;

	int restid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.restaurant_fragment, container, false);

		restid = getArguments().getInt("restid");

		name = view.findViewById(R.id.restaurant_name);
		cuisine = view.findViewById(R.id.restaurant_cuisine);
		type = view.findViewById(R.id.restaurant_type);
		rating = view.findViewById(R.id.restaurant_rating);
		price = view.findViewById(R.id.restaurant_price);
		address = view.findViewById(R.id.restaurant_address);
		hours = view.findViewById(R.id.restaurant_hours);
		phone = view.findViewById(R.id.restaurant_phone);
		email = view.findViewById(R.id.restaurant_email);
		outdoor = view.findViewById(R.id.restaurant_outdoor);
		delivery = view.findViewById(R.id.restaurant_deliver);
		menu = view.findViewById(R.id.restaurant_menu);
		highlights = view.findViewById(R.id.restaurant_highlights);
		newReview = view.findViewById(R.id.restaurant_button_review);
		ratings = view.findViewById(R.id.restaurant_ratings);

		phone.setOnClickListener(this);
		email.setOnClickListener(this);
		menu.setOnClickListener(this);
		newReview.setOnClickListener(this);

		outdoor.setOnClickListener(this);
		delivery.setOnClickListener(this);
		outdoor.setClickable(false);
		delivery.setClickable(false);

		(new RestTask()).execute();

		return view;
	}

	@Override
	public void onClick(View view)
	{
		if(view.equals(phone))
		{
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
			startActivity(callIntent);
		}
		else if(view.equals(email))
		{
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.setType("text/plain");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\n Sent from the Restaurant Advisor App");
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		}
		else if(view.equals(menu))
		{
			Bundle bundle = new Bundle();
			bundle.putInt("restid", restid);
			bundle.putString("rname", name.getText().toString());
			MenuFragment frag = new MenuFragment();
			frag.setArguments(bundle);
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.container, frag);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		else if(view.equals(newReview))
		{
			Bundle bundle = new Bundle();
			bundle.putInt("restid", restid);
			NewReviewFragment frag = new NewReviewFragment();
			frag.setArguments(bundle);
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.container, frag);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		else if(view.equals(outdoor))
		{

		}
		else if(view.equals(delivery))
		{

		}
	}

	ProgressDialog dialog;
	protected class RestTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected void onPreExecute() {
			FragmentManager fm = getFragmentManager();
			dialog = ProgressDialog.newInstance("Loading Restaurant");
			dialog.show(fm, "Progress");
		}
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("restaurant/?restid=" + restid);
		}
		protected void onPostExecute(List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				Object r[] = result.get(0);
				SQL.setText(name, r[0]);
				SQL.setText(type, r[4], "No Dining Type");
				SQL.setText(rating, r[13] + " Stars from " + r[14] + " Reviews", "No Reviews");
				SQL.setText(price, r[10], "Unknown");
				SQL.setText(address, r[5] + " " + r[6] + " " + r[7] + ", " + r[8] + " " + r[9], "No Address");
				SQL.setText(hours, r[3], "No Hours");
				SQL.setText(phone, r[1], "No Phone");
				if(SQL.parse(r[1]).equals("null"))
				{
					phone.setEnabled(false);
				}
				SQL.setText(email, r[2], "No Email");
				if(SQL.parse(r[2]).equals("null"))
				{
					email.setEnabled(false);
				}
				SQL.setText(delivery, r[11]);
				SQL.setText(outdoor, r[12]);
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Restaurant Loading Failed!", Toast.LENGTH_SHORT).show();
			}
			(new ReviewTask()).execute();
		}
	}

	protected class ReviewTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("restreview/?restid=" + restid);
		}
		protected void onPostExecute(List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				for(Object obj[] : result)
				{
					LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.rating_item, ratings, false);
					SQL.setText(R.id.review_title, obj[3], item);
					SQL.setText(R.id.review_text, obj[4], item);
					SQL.setText(R.id.review_user, "by: " + obj[1], item);
					SQL.setText(R.id.review_rating, obj[6], item);
					ratings.addView(item);
					ratings.addView(getLayoutInflater().inflate(R.layout.line, ratings, false));
				}
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Review Loading Failed!", Toast.LENGTH_SHORT).show();
				LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.error_item, ratings, false);
				SQL.setText(R.id.error_text, "No Reviews Found", item);
				ratings.addView(item);
			}
			(new MenuTask()).execute();
		}
	}

	protected class MenuTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("resttopmenu/?restid=" + restid);
		}
		protected void onPostExecute(List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				for(Object obj[] : result)
				{
					LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.menu_item, highlights, false);
					SQL.setText(R.id.menuitem_name, obj[0], item);
					SQL.setText(R.id.menuitem_price, "$" + obj[1], item);
					item.findViewById(R.id.menuitem_counter).setVisibility(View.GONE);
					highlights.addView(item);
					highlights.addView(getLayoutInflater().inflate(R.layout.line, ratings, false));
				}
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Menu Loading Failed!", Toast.LENGTH_SHORT).show();
				Toast.makeText(getActivity(), "Review Loading Failed!", Toast.LENGTH_SHORT).show();
				LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.error_item, highlights, false);
				SQL.setText(R.id.error_text, "No Menu Items Found", item);
				highlights.addView(item);
			}
			(new CuisineTask()).execute();
		}
	}

	protected class CuisineTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("restcuisine/?restid=" + restid);
		}
		protected void onPostExecute(List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				String text = "";
				for(Object obj[] : result)
				{
					text += obj[0] + " ";
				}
				SQL.setText(cuisine, text);
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Cuisine Loading Failed!", Toast.LENGTH_SHORT).show();
				SQL.setText(cuisine, "No Cuisine");
			}
			dialog.dismiss();
		}
	}
}
