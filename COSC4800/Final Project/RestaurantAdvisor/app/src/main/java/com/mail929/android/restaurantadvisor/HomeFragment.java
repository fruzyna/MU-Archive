package com.mail929.android.restaurantadvisor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by mail929 on 11/13/17.
 */

public class HomeFragment extends Fragment
{
	ProgressDialog dialog;
	ListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.restaurantlist_fragment, container, false);

		list = view.findViewById(R.id.restaurants_list);
		(new ListRestsTask()).execute();

		return view;
	}

	protected class ListRestsTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected void onPreExecute() {
			FragmentManager fm = getFragmentManager();
			dialog = ProgressDialog.newInstance("Loading Restaurants");
			dialog.show(fm, "Progress");
		}
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("listrests/");
		}
		protected void onPostExecute(final List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				list.setAdapter(new ArrayAdapter<Object[]>(getActivity(), R.layout.restaurant_item, R.id.restaurant_name, result)
				{
					public View getView(int position, View convertView, ViewGroup parent)
					{
						View view;
						if (convertView == null)
						{
							LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
							convertView = infl.inflate(R.layout.restaurant_item, parent, false);
						}
						view = super.getView(position, convertView, parent);

						Object[] row = result.get(position);

						SQL.setText(R.id.restaurant_name, row[1], view);
						SQL.setText(R.id.restaurant_hours, row[2], view);
						SQL.setText(R.id.restaurant_price, "$" + row[3], view, "No Price");
						SQL.setText(R.id.restaurant_category, row[4], view);
						SQL.setText(R.id.restaurant_rating, row[5] + " Stars", view, "No Stars");

						return view;
					}
				});
				list.setOnItemClickListener(new AdapterView.OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
					{
						Object[] selectedRow = result.get(i);

						Bundle bundle = new Bundle();
						bundle.putInt("restid", (Integer) selectedRow[0]);
						RestaurantFragment frag = new RestaurantFragment();
						frag.setArguments(bundle);
						FragmentTransaction transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.container, frag);
						transaction.addToBackStack(null);
						transaction.commit();
					}
				});
			}
			else
			{
				//failure
				Toast.makeText(getActivity(), "Loading Failed! Check network connection.", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}
}
