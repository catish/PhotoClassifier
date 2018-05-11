package view.classify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import view.com.MyFrame;

public class ClassifyStep2Frame extends MyFrame{
	
	private String title = null;
	private String method = null;
	private List<String> folderPaths = null;
	
	private JRadioButton[] rbtns = null;
	private ButtonGroup btnGroup = null;
	
	
	public ClassifyStep2Frame(String t, List<String> folders) {
		title = t;
		folderPaths = folders;
		initCompent();
	}
	
	public ClassifyStep2Frame(String t, List<String> folders, String m) {
		title = t;
		folderPaths = folders;
		method = m;
		initCompent();
		for(int i=0; i<rbtns.length; i++) {
			if(rbtns[i].getText().equals(method)) {
				rbtns[i].setSelected(true);
				break;
			}
		}
	}
	
	private void initCompent() {
		btnLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ClassifyStep1Frame(title, folderPaths);
				dispose();
				
			}
		});
		
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<rbtns.length; i++) {
					if(rbtns[i].isSelected()==true) {
						method = rbtns[i].getText();
						break;
					}
				}
				new ClassifyStep3Frame(title, folderPaths, method);
				dispose();
				
			}
		});
		
		rbtns = getRbtns(title);
		btnGroup = getBtnGroup(rbtns);
		for(int i=0; i<rbtns.length; i++) {
			subPanel.add(rbtns[i]);
		}
		btnGroup = getBtnGroup(rbtns);
	}
	
	private JRadioButton[] getRbtns(String t) {
		JRadioButton[] btns = null;
		JRadioButton btn = null;
		if(t.equals("按时间分类")) {
			btns = new JRadioButton[2];
			btn = new JRadioButton("按月份");
			btn.setSize(100, 25);
			btn.setLocation(100,50);
			btn.setSelected(true);
			btns[0] = btn;
			btn = new JRadioButton("按年份");
			btn.setSize(100, 25);
			btn.setLocation(100,50+35);
			btns[1] = btn;
			
		} else if(t.equals("按地点分类")) {
			btns = new JRadioButton[3];
			btn = new JRadioButton("按省份");
			btn.setSize(100, 25);
			btn.setLocation(100,50);
			btn.setSelected(true);
			btns[0] = btn;
			btn = new JRadioButton("按城市");
			btn.setSize(100, 25);
			btn.setLocation(100,50+35);
			btns[1] = btn;
			btn = new JRadioButton("按行政区");
			btn.setSize(100, 25);
			btn.setLocation(100,50+35*2);
			btns[2] = btn;
		}
		return btns;
	}
	
	private ButtonGroup getBtnGroup(JRadioButton[] rbtn) {
		ButtonGroup bg = new ButtonGroup();
		for(int i=0; i<rbtns.length; i++) {
			bg.add(rbtn[i]);
		}
		
		return bg;
	}
	
	
	
	
}
