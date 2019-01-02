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
import android.widget.Toast;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by mail929 on 12/7/17.
 */

public class OrderFragment extends Fragment
{
	ListView list;
	ProgressDialog dialog;

	int ono;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.restaurantlist_fragment, container, false);

		ono = getArguments().getInt("ono");

		list = view.findViewById(R.id.restaurants_list);

		(new OrderTask()).execute();

		return view;
	}

	protected class OrderTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected void onPreExecute() {
			FragmentManager fm = getFragmentManager();
			dialog = ProgressDialog.newInstance("Loading Orders");
			dialog.show(fm, "Progress");
		}
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("orderitems/?ono=" + ono);
		}
		protected void onPostExecute(final List<Object[]> result) {
			if(result != null && result.size() > 0)
			{
				list.setAdapter(new ArrayAdapter<Object[]>(getActivity(), R.layout.order_food_item, R.id.orderitem_name, result)
				{
					public View getView(int position, View convertView, ViewGroup parent)
					{
						View view;
						if (convertView == null)
						{
							LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
							convertView = infl.inflate(R.layout.order_food_item, parent, false);
						}
						view = super.getView(position, convertView, parent);

						Object[] row = result.get(position);

						SQL.setText(R.id.orderitem_name, row[0], view);
						SQL.setText(R.id.orderitem_rest, row[4], view);
						SQL.setText(R.id.orderitem_price, "$" + ((Integer)row[3] * (Integer)row[2]), view, "No Price");
						SQL.setText(R.id.orderitem_qty, row[2], view);

						return view;
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