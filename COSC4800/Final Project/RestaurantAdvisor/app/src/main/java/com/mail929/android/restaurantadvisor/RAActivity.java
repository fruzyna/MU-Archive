package com.mail929.android.restaurantadvisor;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RAActivity extends AppCompatActivity
{
	String user;
	static Cart cart;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener()
	{

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item)
		{
			Fragment frag;
			switch (item.getItemId())
			{
				case R.id.navigation_search:
					frag = new SearchFragment();
					break;
				case R.id.navigation_user:
					frag = new UserFragment();
					break;
				case R.id.navigation_home:
				default:
					frag = new HomeFragment();
					break;
			}
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.container, frag);
			transaction.commit();
			return true;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ra);

		cart = new Cart();

		user = getIntent().getExtras().getString("user");

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.container, new HomeFragment());
		transaction.commit();

		BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
		bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
	}

	public static Cart getCart()
	{
		return cart;
	}

	public class Cart
	{
		ArrayList<Food> foods;

		public Cart()
		{
			foods = new ArrayList<>();
		}

		public Food getFood(String name, int restid, int price)
		{
			for(Food food : foods)
			{
				if(food.name.equals(name) && food.restid == restid)
				{
					return food;
				}
			}
			Food food = new Food(name, restid, 0, price);
			foods.add(food);
			return food;
		}

		public ArrayList<Food> getCart()
		{
			ArrayList<Food> cart = new ArrayList<>();
			for(Food food : foods)
			{
				if(food.qty > 0)
				{
					cart.add(food);
				}
			}
			return cart;
		}

		public void reset()
		{
			foods = new ArrayList<>();
		}
	}
}