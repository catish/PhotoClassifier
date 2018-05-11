package view.classify;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;

import view.com.MyFrame;

public class ClassifyStep1Frame extends MyFrame{
	
	private String title = null;
	private List<String> folderPaths = null;
	
	private JButton btnAdd = null;
	private JButton btnDelete = null;
	
	private JList<String> listFilePath = null;
	
	
	public ClassifyStep1Frame(String t) {
		title = t;
		folderPaths = new ArrayList<String>();
		initCompent();
	}
	
	public ClassifyStep1Frame(String t, List<String> folders) {
		title = t;
		folderPaths = folders;
		initCompent();
		updateListFilePath(folderPaths);
	}

	private void initCompent() {
		// 设置标题
		setTitle(title);
		
		// 禁用上一步
		btnLast.setEnabled(false);
		
		// 下一步
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(title.equals("按时间分类")||title.equals("按地点分类")) {
					// 非空检查
					new ClassifyStep2Frame(title, folderPaths);
					
				} else if(title.equals("按类别分类")) {
					new ClassifyStep3Frame(title, folderPaths,"按类别");
				}
				dispose();
				
				
			}
		});
		
		// 初始化控件
		btnAdd = getBtnAdd();
		btnDelete = getBtnDelete();
		listFilePath = getListFilePath();
		subPanel.add(btnAdd);
		subPanel.add(btnDelete);
		subPanel.add(listFilePath);
	}
	
	private JButton getBtnAdd() {
		JButton btn = new JButton();
		btn.setText("添加");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(5,5);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 添加照片路径
				addFilePath();
			}
		});
		return btn;
	}
	
	private JButton getBtnDelete() {
		JButton btn = new JButton();
		btn.setText("删除");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(70,5);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteFilePath();
			}
		});
		return btn;
	}
	
	private JList<String> getListFilePath() {
		JList<String> list = new JList<String>();
		list.setSize(250,680);
		list.setLocation(5,35);
		return list;
	}
	
	private void updateListFilePath(List<String> data) {
		String[] ld = new String[data.size()];
		for(int i=0; i<data.size(); i++) {
			ld[i] = data.get(i);
		}
		listFilePath.setListData(ld);
		listFilePath.updateUI();
	}
	
	private void addFilePath() {
		// 获取文件夹路径
		List<String> folders = new ArrayList<String>();
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // 只能选择目录
		chooser.setMultiSelectionEnabled(true);  // 可以多选 
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {  
			File[] selectedFiles = chooser.getSelectedFiles();  // 获得选择的文件夹的绝对路径
			String p = null;
			for(File f : selectedFiles) {
				p = f.getPath();
				folders.add(p);
			}
		}
		
		// 存入  尚未实现去重
		folderPaths.addAll(folders);
		
		// 更新JList
		updateListFilePath(folderPaths);
		
	}
	
	private void deleteFilePath() {
		// 删除选中的路径
		List<String> paths = listFilePath.getSelectedValuesList();
		for(String p:paths) {
			folderPaths.remove(p);
		}
		
		// 更新JList
		updateListFilePath(folderPaths);
	};
	
	
}
