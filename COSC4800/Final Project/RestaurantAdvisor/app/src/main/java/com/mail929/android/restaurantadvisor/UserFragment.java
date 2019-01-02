package com.mail929.android.restaurantadvisor;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by mail929 on 11/29/17.
 */

public class UserFragment extends Fragment implements View.OnClickListener
{
	LinearLayout cart;
	LinearLayout orders;
	LinearLayout reviews;
	Button checkout;
	Button logout;

	String email;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.user_fragment, container, false);

		email = getActivity().getSharedPreferences("RA", Context.MODE_PRIVATE).getString("email", "");

		cart = view.findViewById(R.id.user_cart);
		orders = view.findViewById(R.id.user_orders);
		reviews = view.findViewById(R.id.user_reviews);
		checkout = view.findViewById(R.id.user_checkout);
		logout = view.findViewById(R.id.user_logout);

		checkout.setEnabled(false);
		for(Food food : RAActivity.getCart().getCart())
		{
			checkout.setEnabled(true);
			RelativeLayout item = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.order_food_item, cart, false);
			SQL.setText(R.id.orderitem_name, food.name, item);
			SQL.setText(R.id.orderitem_qty, food.qty, item);
			SQL.setText(R.id.orderitem_rest, food.restid, item); //todo actually get name
			SQL.setText(R.id.orderitem_price, "$" + (food.qty * food.price), item);
			cart.addView(item);
			cart.addView(getActivity().getLayoutInflater().inflate(R.layout.line, cart, false));
		}

		checkout.setOnClickListener(this);
		logout.setOnClickListener(this);

		(new ReviewTask()).execute();

		return view;
	}

	protected class ReviewTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("userreviews/?email=" + email);
		}
		protected void onPostExecute(List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				for(Object obj[] : result)
				{
					LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.rating_item, reviews, false);
					SQL.setText(R.id.review_title, obj[1], item);
					SQL.setText(R.id.review_text, obj[5], item);
					SQL.setText(R.id.review_user, "for: " + obj[3], item);
					SQL.setText(R.id.review_rating, obj[2], item);
					reviews.addView(item);
					reviews.addView(getLayoutInflater().inflate(R.layout.line, reviews, false));
				}
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Review Loading Failed!", Toast.LENGTH_SHORT).show();
				LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.error_item, reviews, false);
				SQL.setText(R.id.error_text, "No Reviews Found", item);
				reviews.addView(item);
			}
			(new OrderTask()).execute();
		}
	}

	protected class OrderTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("userorders/?email=" + email);
		}
		protected void onPostExecute(List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				for(Object obj[] : result)
				{
					LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.order_item, orders, false);
					SQL.setText(R.id.order_id, obj[0], item);
					SQL.setText(R.id.order_date, obj[1], item);
					String type = "";
					String date = "";
					CheckBox box = item.findViewById(R.id.order_status);
					TextView dateView = item.findViewById(R.id.order_status_date);
					box.setClickable(false);
					if(obj[2].equals("Y"))
					{
						type = "Delivered";
						date = obj[5].toString();
					}
					else if(obj[3].equals("Y"))
					{
						type = "Carried Out";
						date = obj[6].toString();
					}
					else if(obj[4].equals("Y"))
					{
						type = "Dined In";
						date = obj[7].toString();
					}

					if(date.equals("null"))
					{
						box.setChecked(false);
						dateView.setVisibility(View.GONE);
					}
					else
					{
						box.setChecked(true);
						dateView.setText(date);
					}
					box.setText(type);

					item.setOnClickListener(new OrderClickListener((Integer) obj[0]));

					orders.addView(item);
					orders.addView(getLayoutInflater().inflate(R.layout.line, reviews, false));
				}
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Order Loading Failed!", Toast.LENGTH_SHORT).show();
				LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.error_item, orders, false);
				SQL.setText(R.id.error_text, "No Orders Found", item);
				orders.addView(item);
			}
		}
	}

	ProgressDialog dialog;

	protected class CheckoutTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected List<Object[]> doInBackground(Void... unused) {
			dialog = ProgressDialog.newInstance("Checking Out...");
			dialog.show(getFragmentManager(), "Progress");
			return SQL.query("neworder/?email=" + email + "&delivery=" + radioToSQL(R.id.user_delivery) + "&dinein=" + radioToSQL(R.id.user_dinein) + "&carryout=" + radioToSQL(R.id.user_carryout));
		}
		protected void onPostExecute(List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				(new OnoTask()).execute();
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Order Processing Failed!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	protected class OnoTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("lastono/");
		}
		protected void onPostExecute(List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				for(Food food : RAActivity.getCart().getCart())
				{
					(new OrderItemTask()).execute(result.get(0)[0], food.name, food.restid, food.qty);
				}
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Order Processing Failed!", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}

	protected class OrderItemTask extends AsyncTask<Object, Void, List<Object[]>>
	{
		protected List<Object[]> doInBackground(Object... data) {
			return SQL.query("newitem/?ono=" + data[0] + "&food=" + data[1] + "&restid=" + data[2] + "&qty=" + data[3]);
		}
		protected void onPostExecute(List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				RAActivity.getCart().reset();
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Order Processing Failed!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onClick(View view)
	{
		if(view.equals(checkout))
		{
			(new CheckoutTask()).execute();
		}
		else if(view.equals(logout))
		{
			SharedPreferences.Editor edit = getActivity().getSharedPreferences("RA", Context.MODE_PRIVATE).edit();
			edit.putString("email", "");
			edit.putString("password", "");
			edit.commit();
			getActivity().finish();
		}
	}

	public String radioToSQL(int view)
	{
		if(((RadioButton) getView().findViewById(view)).isChecked())
		{
			return "Y";
		}
		return "N";
	}

	public class OrderClickListener implements View.OnClickListener
	{
		int ono;

		public OrderClickListener(int ono)
		{
			this.ono = ono;
		}

		@Override
		public void onClick(View view)
		{
			Bundle bundle = new Bundle();
			bundle.putInt("ono", ono);
			OrderFragment frag = new OrderFragment();
			frag.setArguments(bundle);
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.container, frag);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}
}
