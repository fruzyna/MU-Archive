package com.mail929.android.restaurantadvisor;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

/**
 * Created by mail929 on 11/29/17.
 */

public class SearchFragment extends Fragment implements TextWatcher
{
	EditText bar;
	LinearLayout listing;
	SearchTask task;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.search_fragment, container, false);

		bar = view.findViewById(R.id.search_bar);
		listing = view.findViewById(R.id.search_listing);

		task = new SearchTask();
		bar.addTextChangedListener(this);
		return view;
	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
	{
		task.cancel(true);
		task = new SearchTask();
		if(charSequence.length() == 0)
		{
			listing.removeAllViews();
		}
		else
		{
			task.execute(charSequence.toString());
		}
	}

	@Override
	public void afterTextChanged(Editable editable){}

	protected class SearchTask extends AsyncTask<String, Void, List<Object[]>>
	{
		protected List<Object[]> doInBackground(String... search) {
			return SQL.query("search/?search=" + search[0]);
		}
		protected void onPostExecute(List<Object[]> result) {
			listing.removeAllViews();
			if(result != null && result.size() > 0)
			{
				for(Object obj[] : result)
				{
					RelativeLayout item = (RelativeLayout) getLayoutInflater().inflate(R.layout.restaurant_item, listing, false);
					new ClickListener(item, (Integer) obj[0]);
					SQL.setText(R.id.restaurant_name, obj[1], item);
					SQL.setText(R.id.restaurant_category, obj[2], item, "No Type");
					SQL.setText(R.id.restaurant_price, "$" + obj[3], item, "No Price Range");
					item.findViewById(R.id.restaurant_rating).setVisibility(View.GONE);
					item.findViewById(R.id.restaurant_hours).setVisibility(View.GONE);
					listing.addView(item);
					listing.addView(getLayoutInflater().inflate(R.layout.line, listing, false));
				}
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Search Loading Failed!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	protected class ClickListener implements View.OnClickListener
	{
		int restid;

		public ClickListener(View item, int restid)
		{
			this.restid = restid;
			item.setOnClickListener(this);
		}

		@Override
		public void onClick(View view)
		{
			Bundle bundle = new Bundle();
			bundle.putInt("restid", restid);
			RestaurantFragment frag = new RestaurantFragment();
			frag.setArguments(bundle);
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.container, frag);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}
}
