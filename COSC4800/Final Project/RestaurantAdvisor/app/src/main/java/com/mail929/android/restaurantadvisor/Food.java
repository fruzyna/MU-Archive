package com.mail929.android.restaurantadvisor;

/**
 * Created by mail929 on 12/4/17.
 */

public class Food
{
	String name;
	int restid;
	int qty;
	int price;

	public Food(String name, int restid, int qty, int price)
	{
		this.name = name;
		this.restid = restid;
		this.qty = qty;
		this.price = price;
	}
}