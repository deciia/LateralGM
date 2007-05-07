/*
 * Copyright (C) 2006 IsmAvatar <cmagicj@nni.com>
 * Copyright (C) 2007 Quadduc <quadduc@gmail.com>
 * 
 * This file is part of Lateral GM.
 * Lateral GM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.lateralgm.components;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import org.lateralgm.main.LGM;
import org.lateralgm.main.PrefsStore;
import org.lateralgm.main.Util;


public class GmMenuBar extends JMenuBar
	{
	private static final long serialVersionUID = 1L;
	private JMenuItem[] recentFiles = new JMenuItem[0];
	private int recentFilesPos;
	public static GmMenu menu;
	public static GmMenu fileMenu;
	
	public static final void setTextAndAlt(JMenuItem item, String input)
		{
			Matcher m = Pattern.compile("\t+([^\\s])$").matcher(input);
			if (m.find())
				{
				int alt = (int) m.group(1).toUpperCase().charAt(0);
				item.setMnemonic(alt);
				item.setText(input.substring(0,m.start()));
				}
			else
				{
				item.setMnemonic(-1);
				item.setText(input);
				}
		}
	
	public void updateRecentFiles()
		{
		String[] recentList = PrefsStore.getRecentFiles().toArray(new String[0]);
		for (JMenuItem item : recentFiles)
			{
			fileMenu.remove(item);
			}
		recentFiles = new JMenuItem[recentList.length];
		for (int i = 0; i < recentFiles.length; i++)
			{
			File file = new File(recentList[i]).getAbsoluteFile();
			String number = Integer.toString(i + 1);
			JMenuItem item = recentFiles[i] = new JMenuItem(String.format("%s %s  [%s]",number,file.getName(),file.getParent()),number.codePointAt(0));
			item.setActionCommand("GmMenuBar.OPEN " + Util.urlEncode(file.toString()));
			item.addActionListener(LGM.listener);
			fileMenu.insert(item,recentFilesPos + i);
			}
		}

	public GmMenuBar()
		{
		fileMenu = menu = new GmMenu(Messages.getString("GmMenuBar.MENU_FILE")); //$NON-NLS-1$
		add(menu);

		menu.addItem("GmMenuBar.NEW",KeyEvent.VK_N,ActionEvent.CTRL_MASK); //$NON-NLS-1$
		menu.addItem("GmMenuBar.OPEN",KeyEvent.VK_O,ActionEvent.CTRL_MASK); //$NON-NLS-1$
		menu.addItem("GmMenuBar.SAVE",KeyEvent.VK_S,ActionEvent.CTRL_MASK); //$NON-NLS-1$
		menu.addItem("GmMenuBar.SAVEAS"); //$NON-NLS-1$
		menu.add(new JSeparator());
		JCheckBoxMenuItem check = new JCheckBoxMenuItem(); //$NON-NLS-1$
		setTextAndAlt(check,Messages.getString("GmMenuBar.ADVANCED"));
		menu.add(check);
		menu.addItem("GmMenuBar.PREFERENCES"); //$NON-NLS-1$
		menu.add(new JSeparator());
		recentFilesPos = menu.getMenuComponentCount();
		menu.add(new JSeparator());
		menu.addItem("GmMenuBar.EXIT",KeyEvent.VK_F4,ActionEvent.ALT_MASK); //$NON-NLS-1$
		updateRecentFiles();
		
		menu = new GmMenu(Messages.getString("GmMenuBar.MENU_EDIT")); //$NON-NLS-1$
		add(menu);

		GmMenu sub = new GmMenu(Messages.getString("GmMenuBar.MENU_INSERT")); //$NON-NLS-1$
		menu.add(sub);
		sub.addItem("GmMenuBar.INSERT_GROUP"); //$NON-NLS-1$
		sub.add(new JSeparator());
		sub.addItem("GmMenuBar.INSERT_SPRITE"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.INSERT_SOUND"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.INSERT_BACKGROUND"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.INSERT_PATH"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.INSERT_SCRIPT"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.INSERT_FONT"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.INSERT_TIMELINE"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.INSERT_OBJECT"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.INSERT_ROOM"); //$NON-NLS-1$

		sub = new GmMenu(Messages.getString("GmMenuBar.MENU_ADD")); //$NON-NLS-1$
		menu.add(sub);
		sub.addItem("GmMenuBar.ADD_GROUP"); //$NON-NLS-1$
		sub.add(new JSeparator());
		sub.addItem("GmMenuBar.ADD_SPRITE"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.ADD_SOUND"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.ADD_BACKGROUND"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.ADD_PATH"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.ADD_SCRIPT"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.ADD_FONT"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.ADD_TIMELINE"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.ADD_OBJECT"); //$NON-NLS-1$
		sub.addItem("GmMenuBar.ADD_ROOM"); //$NON-NLS-1$

		menu.add(new JSeparator());
		menu.addItem("GmMenuBar.RENAME",KeyEvent.VK_F2,0); //$NON-NLS-1$
		menu.addItem("GmMenuBar.DELETE",KeyEvent.VK_DELETE,ActionEvent.SHIFT_MASK); //$NON-NLS-1$
		menu.addItem("GmMenuBar.COPY",KeyEvent.VK_INSERT,ActionEvent.ALT_MASK); //$NON-NLS-1$
		menu.add(new JSeparator());
		menu.addItem("GmMenuBar.PROPERTIES",KeyEvent.VK_ENTER,ActionEvent.ALT_MASK); //$NON-NLS-1$

		menu = new GmMenu(Messages.getString("GmMenuBar.MENU_RESOURCES")); //$NON-NLS-1$
		add(menu);

		menu.addItem("GmMenuBar.DEFRAGIDS");
		menu.addItem("GmMenuBar.VERIFYNAMES"); //$NON-NLS-1$
		menu.addItem("GmMenuBar.SYNTAXCHECK"); //$NON-NLS-1$
		menu.add(new JSeparator());
		menu.addItem("GmMenuBar.FIND",KeyEvent.VK_F,ActionEvent.ALT_MASK + ActionEvent.CTRL_MASK); //$NON-NLS-1$
		menu.addItem("GmMenuBar.ANNOTATE"); //$NON-NLS-1$
		menu.add(new JSeparator());
		menu.addItem("GmMenuBar.EXPAND"); //$NON-NLS-1$
		menu.addItem("GmMenuBar.COLLAPSE"); //$NON-NLS-1$

		menu = new GmMenu(Messages.getString("GmMenuBar.MENU_HELP")); //$NON-NLS-1$
		add(menu);

		menu.addItem("GmMenuBar.MANUAL",KeyEvent.VK_F1,0); //$NON-NLS-1$
		menu.addItem("GmMenuBar.ABOUT"); //$NON-NLS-1$
		}

	public class GmMenu extends JMenu
		{
		private static final long serialVersionUID = 1L;

		public GmMenu(String s)
			{
			super();
			setTextAndAlt(this,s);
			}

		public JMenuItem addItem()
			{
			return addItem(null);
			}

		public JMenuItem addItem(String key)
			{
			return addItem(key,-1,-1);
			}

		public JMenuItem addItem(String key, int shortcut, int control)
			{
			JMenuItem item = new JMenuItem();
			if (key != null)
				{
				setTextAndAlt(item,Messages.getString(key));
				item.setIcon(LGM.getIconForKey(key));
				item.setActionCommand(key);
				}
			if (shortcut >= 0) item.setAccelerator(KeyStroke.getKeyStroke(shortcut,control));
			item.addActionListener(LGM.listener);
			add(item);
			return item;
			}
		}
	}