package com.esp.geniesms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.beans.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

public class GenieSMSFrame extends JFrame
{
	JPanel contentPane;
	JButton jButton1 = new JButton();
	JTextField jTextField1 = new JTextField();
	JTextArea jTextArea1 = new JTextArea();
	Border border1;
	Border border2;
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JButton jButton2 = new JButton();
	JLabel jLabel3 = new JLabel();

	/**Construct the frame*/
	public GenieSMSFrame()
	{
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**Component initialization*/
	private void jbInit() throws Exception
	{
		jButton1.setMaximumSize(new Dimension(87, 27));
		jButton1.setMinimumSize(new Dimension(87, 27));
		jButton1.setPreferredSize(new Dimension(87, 27));
		jButton1.setText("Send");
		jButton1.setBounds(new Rectangle(219, 220, 87, 27));
		jButton1.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton1_actionPerformed(e);
			}
		});
		//setIconImage(Toolkit.getDefaultToolkit().createImage(GenieSMSFrame.class.getResource("[Your Icon]")));
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(null);
		this.setResizable(false);
		this.setSize(new Dimension(400, 300));
		this.setTitle("GenieSMS");
		jTextField1.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextField1.setBounds(new Rectangle(126, 25, 162, 21));
		jTextArea1.setWrapStyleWord(true);
		jTextArea1.setLineWrap(true);
		jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextArea1.setBounds(new Rectangle(126, 58, 223, 140));
		jTextArea1.addKeyListener(new java.awt.event.KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				jTextArea1_keyTyped(e);
			}
		});
		jLabel1.setText("Mobile number:");
		jLabel1.setBounds(new Rectangle(31, 27, 97, 17));
		jLabel2.setText("Message:");
		jLabel2.setBounds(new Rectangle(31, 61, 56, 17));
		jButton2.setText("Set Login");
		jButton2.setBounds(new Rectangle(78, 220, 90, 27));
		jButton2.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
		jLabel3.setText("jLabel3");
		jLabel3.setBounds(new Rectangle(32, 89, 41, 17));
		contentPane.add(jTextArea1, null);
		contentPane.add(jLabel1, null);
		contentPane.add(jTextField1, null);
		contentPane.add(jLabel2, null);
		contentPane.add(jButton1, null);
		contentPane.add(jButton2, null);
		contentPane.add(jLabel3, null);
	}
	/**Overridden so we can exit when window is closed*/
	protected void processWindowEvent(WindowEvent e)
	{
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING)
		{
			Properties prop = new Properties();
			prop.setProperty("username",System.getProperty("dave.geniesms.username"));
			prop.setProperty("password",System.getProperty("dave.geniesms.password"));
			try
			{
				prop.store(new FileOutputStream(System.getProperty("user.home")+File.separator+"geniesms.cfg"),"Login information for geniesms");
			}
			catch (IOException err)
			{
			}
			System.exit(0);
		}
	}

	void jButton1_actionPerformed(ActionEvent e)
	{
		this.hide();
		GenieConnection gc = new GenieConnection("dtownsend","78Cthulhu20");
		gc.login();
		StringTokenizer tokens = new StringTokenizer(jTextField1.getText(),",");
		while (tokens.hasMoreTokens())
		{
			gc.sendTextMessage(tokens.nextToken(),jTextArea1.getText());
		}
		gc.logout();
		System.exit(0);
	}

	void jButton2_actionPerformed(ActionEvent e)
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

	void jTextArea1_keyTyped(KeyEvent e)
	{
		jLabel3.setText(Integer.toString(jTextArea1.getText().length())+" characters");
	}
}