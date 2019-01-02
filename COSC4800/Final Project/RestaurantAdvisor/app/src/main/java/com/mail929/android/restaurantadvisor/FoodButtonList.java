package com.mail929.android.restaurantadvisor;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mail929 on 12/4/17.
 */

public class FoodButtonList implements View.OnClickListener
{
	Button add;
	Button sub;
	TextView value;
	int count;
	Food food;

	public FoodButtonList(Button add, Button sub, TextView value, Food food)
	{
		this.add = add;
		this.sub = sub;
		this.value = value;
		this.food = food;

		int count = food.qty;
		value.setText(count + "");

		add.setOnClickListener(this);
		sub.setOnClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		if (view.equals(add))
		{
			count++;
		}
		else if(view.equals(sub) && count > 0)
		{
			count--;
		}
		food.qty = count;
		value.setText(count + "");
	}
}