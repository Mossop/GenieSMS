package com.esp.geniesms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.UIManager;
import java.awt.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

public class GenieSMS
{
	boolean packFrame = false;

	/**Construct the application*/
	public GenieSMS(String number)
	{
		GenieSMSFrame frame = new GenieSMSFrame();
		if (number!=null)
		{
			frame.jTextField1.setText(number);
		}
		//Validate frames that have preset sizes
		//Pack frames that have useful preferred size info, e.g. from their layout
		if (packFrame)
		{
			frame.pack();
		}
		else
		{
			frame.validate();
		}
		//Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height)
		{
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width)
		{
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		frame.setVisible(true);
	}

	/**Main method*/
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			if (args.length>0)
			{
				new GenieSMS(args[0]);
			}
			else
			{
				new GenieSMS(null);
			}
			Properties prop = new Properties();
			try
			{
				prop.load(new FileInputStream(System.getProperty("user.home")+File.separator+"geniesms.cfg"));
			}
			catch (IOException e)
			{
			}
			if (prop.getProperty("username")==null)
			{
				LoginInfo frame = new LoginInfo();
				//Validate frames that have preset sizes
				frame.validate();
				//Center the window
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frameSize = frame.getSize();
				if (frameSize.height > screenSize.height)
				{
					frameSize.height = screenSize.height;
				}
				if (frameSize.width > screenSize.width)
				{
					frameSize.width = screenSize.width;
				}
				frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
				frame.setVisible(true);
			}
			else
			{
				System.setProperty("dave.geniesms.username",prop.getProperty("username"));
				System.setProperty("dave.geniesms.password",prop.getProperty("password"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}