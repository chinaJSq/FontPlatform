package com.njnu.struct;

import java.util.ArrayList;

import android.util.Log;

public class StrokeMatch
{
	ArrayList<Standard2User> sus = null;

	public StrokeMatch(ArrayList<Standard2User> sus)
	{
		this.sus = sus;
	}

	public StrokeMatch()
	{
		sus = new ArrayList<Standard2User>();
	}

	// 用户笔画存在
	public Boolean uIndexExist(int uIndex)
	{
		for (Standard2User su : sus)
		{
			if (su.getUIndex() == uIndex)
				return true;
		}
		return false;
	}

	// 标准ID存在(index=id-1)
	public Boolean sIndexExist(int id)
	{
		for (Standard2User su : sus)
		{
			if (su.getSIndex() == (id - 1))
				return true;
		}
		return false;
	}

	// 通过ID来获取对应的笔画
	public int getuIndex(int id)
	{
		for (Standard2User su : sus)
		{
			if (su.getSIndex() == (id - 1))
				return su.getUIndex();
		}
		return -1;
	}

	public Boolean addS2u(int id, int uIndex)
	{
		// Log.i("Bid:" + (id - 1), "BuIndex:" + uIndex);
		if (!uIndexExist(uIndex) && !sIndexExist(id))
		{
			Log.i("id:" + (id - 1), "uIndex:" + uIndex);
			sus.add(new Standard2User(id - 1, uIndex));
			return true;
		} else
			return false;
	}

	public Boolean addS2u(Standard2User su)
	{
		// Log.i("Bid:" + (su.getSIndex()), "BuIndex:" + su.getUIndex());
		if (!uIndexExist(su.getUIndex()) && !sIndexExist(su.getSIndex() + 1))
		{
			Log.i("id:" + (su.getSIndex()), "uIndex:" + su.getUIndex());
			sus.add(su);
			return true;
		} else
			return false;
	}

	public ArrayList<Standard2User> getSus()
	{
		return sus;
	}

	@Override
	public StrokeMatch clone()
	{
		ArrayList<Standard2User> newSus = new ArrayList<Standard2User>();
		for (Standard2User su : sus)
		{
			newSus.add(su.clone());
		}
		return new StrokeMatch(newSus);
	}
}
