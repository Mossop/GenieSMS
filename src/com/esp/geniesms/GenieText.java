package com.esp.geniesms;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GenieText
{
	public static void main(String[] args)
	{
		if (args.length<3)
		{
		}
		else
		{
			try
			{
				StringBuffer message = new StringBuffer();
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String line = in.readLine();
				while (line!=null)
				{
					message.append(line);
					message.append('\n');
					line=in.readLine();
				}
				GenieConnection gc = new GenieConnection(args[0],args[1]);
				System.out.println("Logging in");
				gc.login();
				System.out.println("Sending message");
				gc.sendTextMessage(args[2],message.toString());
				System.out.println("Logging out");
				gc.logout();
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
}
