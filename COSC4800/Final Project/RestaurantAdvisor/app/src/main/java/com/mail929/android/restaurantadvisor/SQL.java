package com.mail929.android.restaurantadvisor;

import android.app.Fragment;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mail929 on 11/27/17.
 */

public class SQL
{
	final static String address = "http://lfruzyna.ddns.net:4800";

	public static List<Object[]> query(String query)
	{
		String result = "";
		OkHttpClient client = new OkHttpClient();
		String url = address + "/api/" + query;
		System.out.println("Requesting: " + url);
		Request request = new Request.Builder().url(url).build();
		try
		{
			Response response = client.newCall(request).execute();
			result = response.body().string();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		System.out.println("Result: " + result);

		List<Object[]> rows = new ArrayList<>();
		if(result.charAt(0) == '[')
		{
			try
			{
				JSONArray json = new JSONArray(result);
				for(int i = 0; i < json.length(); i++)
				{
					JSONArray jrow = json.getJSONArray(i);
					System.out.println("Row: " + jrow.toString());
					Object[] row = new Object[jrow.length()];
					for(int j = 0; j < jrow.length(); j++)
					{
						row[j] = jrow.get(j);
					}
					rows.add(row);
				}
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Object row[] = new Object[1];
			row[0] = result;
			rows.add(row);
		}
		return rows;
	}

	public static void setText(int id, Object text, View parent)
	{
		setText(id, text, parent, "null");
	}

	public static void setText(int id, Object text, View parent, String ifnull)
	{
		text = parse(text, ifnull);

		((TextView) parent.findViewById(id)).setText((String) text);
	}

	public static void setText(View view, Object text)
	{
		setText(view, text, "null");
	}

	public static void setText(View view, Object text, String ifnull)
	{
		text = parse(text, ifnull);

		if(view instanceof CheckBox)
		{
			((CheckBox) view).setChecked(text.equals("Y"));
		}
		else if(view instanceof Button)
		{
			((Button) view).setText((String) text);
		}
		else if(view instanceof TextView)
		{
			((TextView) view).setText((String) text);
		}
	}

	public static String parse(Object text)
	{
		return parse(text, "null");
	}

	public static String parse(Object text, String ifnull)
	{
		if(text instanceof Integer)
		{
			text = Integer.toString((Integer) text);
		}
		else if(text instanceof Double)
		{
			text = Double.toString((Double) text);
		}
		else if(text instanceof Boolean)
		{
			text = Boolean.toString((Boolean) text);
		}
		else if(!(text instanceof String) || ((String) text).contains("null"))
		{
			text = ifnull;
		}

		return (String) text;
	}
}
