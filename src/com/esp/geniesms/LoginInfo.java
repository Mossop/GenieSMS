package com.esp.geniesms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LoginInfo extends JFrame
{
	JPanel contentPane;
	JButton jButton1 = new JButton();
	JTextField jTextField1 = new JTextField();
	JTextField jTextField2 = new JTextField();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();

	/**Construct the frame*/
	public LoginInfo()
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
		jButton1.setText("Save");
		jButton1.setBounds(new Rectangle(103, 93, 79, 27));
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
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(new Dimension(290, 163));
		this.setTitle("Change Login Information");
		jTextField1.setText(System.getProperty("dave.geniesms.username",""));
		jTextField1.setBounds(new Rectangle(103, 19, 155, 21));
		jTextField2.setText(System.getProperty("dave.geniesms.password",""));
		jTextField2.setBounds(new Rectangle(103, 50, 155, 21));
		jLabel1.setText("Username:");
		jLabel1.setBounds(new Rectangle(24, 21, 69, 17));
		jLabel2.setText("Password:");
		jLabel2.setBounds(new Rectangle(25, 51, 66, 17));
		contentPane.add(jLabel1, null);
		contentPane.add(jLabel2, null);
		contentPane.add(jButton1, null);
		contentPane.add(jTextField1, null);
		contentPane.add(jTextField2, null);
	}

	void jButton1_actionPerformed(ActionEvent e)
	{
		System.setProperty("dave.geniesms.username",jTextField1.getText());
		System.setProperty("dave.geniesms.password",jTextField2.getText());
		this.hide();
	}
}