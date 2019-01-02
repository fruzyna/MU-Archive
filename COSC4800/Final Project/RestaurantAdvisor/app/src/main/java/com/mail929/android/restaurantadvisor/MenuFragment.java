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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by mail929 on 11/30/17.
 */

public class MenuFragment extends Fragment
{
	TextView name;
	ListView list;

	int restid;
	String rname;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.menu_fragment, container, false);

		restid = getArguments().getInt("restid");
		rname = getArguments().getString("rname");

		name = view.findViewById(R.id.menu_restaurant);
		list = view.findViewById(R.id.menu_list);

		name.setText(rname);

		(new MenuTask()).execute();

		return view;
	}

	ProgressDialog dialog;
	protected class MenuTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected void onPreExecute() {
			FragmentManager fm = getFragmentManager();
			dialog = ProgressDialog.newInstance("Loading Menu");
			dialog.show(fm, "Progress");
		}
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("restmenu/?restid=" + restid);
		}
		protected void onPostExecute(final List<Object[]> result) {
			dialog.dismiss();
			if(result != null && result.size() > 0)
			{
				//success
				list.setAdapter(new ArrayAdapter<Object[]>(getActivity(), R.layout.menu_item, R.id.menuitem_name, result)
				{
					public View getView(int position, View convertView, ViewGroup parent)
					{
						View view;
						if (convertView == null)
						{
							LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
							convertView = infl.inflate(R.layout.menu_item, parent, false);
						}
						view = super.getView(position, convertView, parent);

						Object[] row = result.get(position);

						SQL.setText(R.id.menuitem_name, row[0], view);
						SQL.setText(R.id.menuitem_price, "$" + row[1], view);

						new FoodButtonList((Button) view.findViewById(R.id.menuitem_add), (Button) view.findViewById(R.id.menuitem_sub), (TextView) view.findViewById(R.id.menuitem_qty), RAActivity.getCart().getFood((String) row[0], restid, (Integer) row[1]));

						return view;
					}
				});
			}
		}
	}
}