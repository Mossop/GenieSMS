package com.esp.geniesms;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class GenieConnection
{
	private HashMap cookies;
	private boolean loggedin;
	private String username;
	private String password;

	public GenieConnection(String username, String password)
	{
		cookies = new HashMap();
		this.username=username;
		this.password=password;
		loggedin=false;
	}

	public void login()
	{
		try
		{
			HttpURLConnection con = (HttpURLConnection)(new URL("http://www.genie.co.uk/login/doLogin")).openConnection();
			con.setRequestMethod("POST");
			con.setInstanceFollowRedirects(false);
			con.setDoOutput(true);
			PrintWriter out = new PrintWriter(con.getOutputStream());
			out.print("username="+username+"&password="+password);
			out.close();
			int loop=1;
			while (con.getHeaderFieldKey(loop)!=null)
			{
				if (con.getHeaderFieldKey(loop).equals("Set-Cookie"))
				{
					String thiscookie=con.getHeaderField(loop);
					thiscookie=thiscookie.substring(0,thiscookie.indexOf(";"));
					String cookiename=thiscookie.substring(0,thiscookie.indexOf("="));
					String cookievalue=thiscookie.substring(thiscookie.indexOf("=")+1);
					//System.out.println(cookiename+" = "+cookievalue);
					cookies.put(cookiename,cookievalue);
				}
				loop++;
			}
			loggedin=true;
			System.out.println("Successful Login ("+con.getResponseCode()+")");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String getCookieList()
	{
		StringBuffer list = new StringBuffer();
		Iterator loop = cookies.keySet().iterator();
		while (loop.hasNext())
		{
			String name = (String)loop.next();
			list.append(name+"="+cookies.get(name));
			if (loop.hasNext())
			{
				list.append("; ");
			}
		}
		return list.toString();
	}
	
	public void sendTextMessage(String number, String message)
	{
		if (loggedin)
		{
			try
			{
				HttpURLConnection con = (HttpURLConnection)(new URL("http://www.genie.co.uk/gmail/sms")).openConnection();
				con.setRequestMethod("POST");
				con.setInstanceFollowRedirects(false);
				con.setRequestProperty("Cookie",getCookieList());
				con.setDoOutput(true);
				PrintWriter out = new PrintWriter(con.getOutputStream());
				out.print("RECIPIENT="+number+"&MESSAGE="+message+"&action=Send");
				out.close();
				con.connect();
				con.getHeaderField(1);
				System.out.println("Message Sent ("+con.getResponseCode()+")");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void logout()
	{
		if (loggedin)
		{
			try
			{
				HttpURLConnection con = (HttpURLConnection)(new URL("http://www.genie.co.uk/logout")).openConnection();
				con.setRequestProperty("Cookie",getCookieList());
				con.getHeaderField(1);
				loggedin=false;
				System.out.println("Logged out ("+con.getResponseCode()+")");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			cookies.clear();
		}
	}
}