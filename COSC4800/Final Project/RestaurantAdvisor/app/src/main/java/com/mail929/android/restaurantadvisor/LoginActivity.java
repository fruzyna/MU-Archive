package com.mail929.android.restaurantadvisor;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by mail929 on 11/28/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
	Button login;
	Button create;
	TextView email;
	TextView password;
	LinearLayout extra;
	TextView fname;
	TextView minit;
	TextView lname;
	TextView phone;
	TextView address;
	TextView city;
	TextView state;
	TextView zip;

	Context c;

	ProgressDialog dialog;

	SharedPreferences prefs;

	String user;
	String pass;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		prefs = getSharedPreferences("RA", MODE_PRIVATE);
		user = prefs.getString("email", "");
		pass = prefs.getString("password", "");
		if(!user.equals("") && !pass.equals(""))
		{
			(new LoginTask()).execute();
		}


		login = findViewById(R.id.login_button);
		create = findViewById(R.id.create_button);
		email = findViewById(R.id.login_email);
		password = findViewById(R.id.login_password);
		extra = findViewById(R.id.create_extra_info);
		fname = findViewById(R.id.create_fname);
		minit = findViewById(R.id.create_minit);
		lname = findViewById(R.id.create_lname);
		phone = findViewById(R.id.create_phone);
		address = findViewById(R.id.create_address);
		city = findViewById(R.id.create_city);
		state = findViewById(R.id.create_state);
		zip = findViewById(R.id.create_zip);

		login.setOnClickListener(this);
		create.setOnClickListener(this);

		c = this;
	}

	@Override
	public void onClick(View view)
	{
		user = email.getText().toString();
		pass = password.getText().toString();
		if(view.equals(login))
		{
			if(!email.equals("") && !pass.equals(""))
			{
				(new LoginTask()).execute();
			}
			else
			{
				Toast.makeText(c, "Invalid login!", Toast.LENGTH_SHORT).show();
			}
		}
		else if(view.equals(create))
		{
			if(extra.getVisibility() == View.GONE)
			{
				extra.setVisibility(View.VISIBLE);
			}
			else if(!user.equals("") && !pass.equals("")
					&& !fname.getText().toString().equals("") && !lname.getText().toString().equals(""))
			{
				(new CreateTask()).execute();
			}
			else
			{
				Toast.makeText(c, "Invalid login!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	protected class LoginTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected void onPreExecute() {
			FragmentManager fm = getFragmentManager();
			dialog = ProgressDialog.newInstance("Logging In");
			dialog.show(fm, "Progress");
		}
		protected List<Object[]> doInBackground(Void... unused) {
			return SQL.query("login/?email=" + user + "&password=" + pass);
		}
		protected void onPostExecute(List<Object[]> result) {
			dialog.dismiss();
			if(result != null && result.size() > 0 && result.get(0)[3].equals(user))
			{
				//success
				saveCreds();
				Toast.makeText(c, "Welcome " + result.get(0)[0] + "!", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(c, RAActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
			else
			{
				//failure
				Toast.makeText(c, "Login Failed, try again!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	protected class CreateTask extends AsyncTask<Void, Void, List<Object[]>>
	{
		protected void onPreExecute() {
			FragmentManager fm = getFragmentManager();
			dialog = ProgressDialog.newInstance("Creating Account");
			dialog.show(fm, "Progress");
		}
		protected List<Object[]> doInBackground(Void... unused) {
			String ad = address.getText().toString();
			String no = ad.split(" ")[0];
			String name = ad.substring(no.length());
			String zipCode = zip.getText().toString();
			if(no.length() == 0)
			{
				no = "0";
			}
			if(zipCode.length() == 0)
			{
				zipCode = "0";
			}
			return SQL.query("create/?email=" + user + "&password=" + pass
					+ "&fname=" + fname.getText().toString() + "&lname=" + lname.getText().toString()
					+ "&minit=" + minit.getText().toString() + "&phone=" + phone.getText().toString()
					+ "&streetno=" + no + "&streetname=" + name
					+ "&city=" + city.getText().toString() + "&state=" + state.getText().toString()
					+ "&zip=" + zipCode);
		}
		protected void onPostExecute(List<Object[]> result) {
			dialog.dismiss();
			if(result != null && result.size() > 0 && result.get(0)[0].equals("Success"))
			{
				//success
				saveCreds();
				Toast.makeText(c, "Welcome" + result.get(0)[0] + "!", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(c, RAActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
			else
			{
				Toast.makeText(c, "Account Creation Failed, try again!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void saveCreds()
	{
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("email", user);
		edit.putString("password", pass);
		edit.commit();
	}
}
