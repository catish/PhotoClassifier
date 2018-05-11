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
		// ���ñ���
		setTitle(title);
		
		// ������һ��
		btnLast.setEnabled(false);
		
		// ��һ��
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(title.equals("��ʱ�����")||title.equals("���ص����")) {
					// �ǿռ��
					new ClassifyStep2Frame(title, folderPaths);
					
				} else if(title.equals("��������")) {
					new ClassifyStep3Frame(title, folderPaths,"�����");
				}
				dispose();
				
				
			}
		});
		
		// ��ʼ���ؼ�
		btnAdd = getBtnAdd();
		btnDelete = getBtnDelete();
		listFilePath = getListFilePath();
		subPanel.add(btnAdd);
		subPanel.add(btnDelete);
		subPanel.add(listFilePath);
	}
	
	private JButton getBtnAdd() {
		JButton btn = new JButton();
		btn.setText("���");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(5,5);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// �����Ƭ·��
				addFilePath();
			}
		});
		return btn;
	}
	
	private JButton getBtnDelete() {
		JButton btn = new JButton();
		btn.setText("ɾ��");
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
		// ��ȡ�ļ���·��
		List<String> folders = new ArrayList<String>();
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // ֻ��ѡ��Ŀ¼
		chooser.setMultiSelectionEnabled(true);  // ���Զ�ѡ 
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {  
			File[] selectedFiles = chooser.getSelectedFiles();  // ���ѡ����ļ��еľ���·��
			String p = null;
			for(File f : selectedFiles) {
				p = f.getPath();
				folders.add(p);
			}
		}
		
		// ����  ��δʵ��ȥ��
		folderPaths.addAll(folders);
		
		// ����JList
		updateListFilePath(folderPaths);
		
	}
	
	private void deleteFilePath() {
		// ɾ��ѡ�е�·��
		List<String> paths = listFilePath.getSelectedValuesList();
		for(String p:paths) {
			folderPaths.remove(p);
		}
		
		// ����JList
		updateListFilePath(folderPaths);
	};
	
	
}
