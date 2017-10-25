package com.njnu.struct;

import android.util.Log;

public class Standard2User
{
	int sIndex;// id-1
	int uIndex;// 0-size()

	public Standard2User(int sIndex, int uIndex)
	{
		super();
		// Log.i("sIndex:" + sIndex, "uIndex:" + uIndex);
		this.sIndex = sIndex;
		this.uIndex = uIndex;
	}

	public int getSIndex()
	{
		return sIndex;
	}

	public void setSIndex(int sIndex)
	{
		this.sIndex = sIndex;
	}

	public int getUIndex()
	{
		return uIndex;
	}

	public void setUIndex(int uIndex)
	{
		this.uIndex = uIndex;
	}

	@Override
	protected Standard2User clone()
	{
		return new Standard2User(sIndex, uIndex);
	}

}
