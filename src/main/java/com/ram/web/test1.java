package com.ram.web;

public class test1 {

	public enum Days {Mon,TUE,WED};

	public static void main(String[] args) throws InterruptedException {

		for(Days D:Days.values())
		{
			Days []d2=Days.values();
			System.out.println(d2[2]);
		}
		
		
	}

}
