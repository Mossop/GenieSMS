package com.esp.geniesms;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GenieConnection
{
	private String ssuid,qss,qsg;
	private boolean loggedin;
	private String username;
	private String password;

	public GenieConnection(String username, String password)
	{
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
					// System.out.println(thiscookie);
					if (thiscookie.startsWith("QSS"))
					{
						qss=thiscookie;
					}
					if (thiscookie.startsWith("QSG"))
					{
						qsg=thiscookie;
					}
					if (thiscookie.startsWith("ssuid"))
					{
						ssuid=thiscookie;
					}
				}
				loop++;
			}
			loggedin=true;
			System.out.println("Successfull Login");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
				con.setRequestProperty("Cookie",ssuid+"; "+qss+"; "+qsg);
				con.setDoOutput(true);
				PrintWriter out = new PrintWriter(con.getOutputStream());
				out.print("RECIPIENT="+number+"&MESSAGE="+message+"&action=Send");
				out.close();
				con.connect();
				con.getHeaderField(1);
				System.out.println("Message Sent");
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
				con.setRequestProperty("Cookie",ssuid+"; "+qss+"; "+qsg);
				con.getHeaderField(1);
				loggedin=false;
				System.out.println("Logged out");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}