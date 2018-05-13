package view.classify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import helper.ClassifyHelper;
import helper.FileInfoHelper;
import view.com.MyFrame;

public class ClassifyStep4Frame extends MyFrame{
	
	private String title = null;
	private String method = null;
	private String outputPath = null;
	private List<String> folderPaths = null;
	
	private JLabel message = null;
	private JProgressBar progressBar = null;
	private JCheckBox cbOpenFolder = null;
	
	private FileInfoHelper fih = null;
	private ClassifyHelper cf = null;
	
	public ClassifyStep4Frame(String t, List<String> folders, String m, String op) {
		title = t;
		folderPaths = folders;
		method = m;
		outputPath = op;
		
		initCompent();
		
			
		Thread classfyThread = new Thread(){
		
		   public void run(){
		     	// ��ʼ������
				
			   	double progress = 0;
			   
			    message.setText("���ڶ�ȡ��Ƭ");
			    progress = 3.0;
			    progressBar.setValue((int) progress);
				
				// ��ȡ������Ƭ·��
				fih = new FileInfoHelper();
				cf = new ClassifyHelper();
				
				List<String> photoPaths = new ArrayList<String>();
				for(String fp : folderPaths) {
					photoPaths.addAll(fih.readPhotoPaths(fp));
				}
				
				int photoAmount = photoPaths.size();
				
				// ��ȡ��Ƭ���
				progress = 7.0;

				//
				message.setText("���ڻ�ȡ��Ƭ�����Ϣ");
				progressBar.setValue((int) progress);
				
				
				// ����method��ȡ��ǩ��Ϣ
				Map<String, List<String>> res = null;
				if(title.equals("��ʱ�����")) {
					// ��ȡ��Ƭʱ����Ϣ
					Map<String,String> photoInfos = new HashMap<String,String>();
					for(String k : photoPaths) {
						photoInfos.put(k, fih.readPhotoTime(k));
					}
					// ��ȡ������
					res = cf.byTime(photoInfos, method);
				} else if(title.equals("���ص����")) {
					// ��ȡ��Ƭ�ص���Ϣ
					Map<String,String> photoInfos = new HashMap<String,String>();
					for(String k : photoPaths) {
						photoInfos.put(k, fih.readPhotoAddress(k));
					}
					// ��ȡ������
					res = cf.byAddress(photoInfos, method);
				} else if(title.equals("��������")) {
					// ��ȡ��Ƭ�����Ϣ
					Map<String,String> photoInfos = new HashMap<String,String>();
					photoInfos = fih.readPhotoClass(photoPaths);
					// ��ȡ������
					res = cf.byClass(photoInfos);
				}
				progress = 10.0;
				progressBar.setValue((int) progress);
				
				/*
				// ����
				try {
					cf.export(outputPath, res);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
				// ������Ƭ
				Set<String> keys = res.keySet();
				List<String> values = null;
				String newpath =  null;
				
				for (String key : keys) {  
					cf.CreateFolder(outputPath,key);
					values = res.get(key);
					for(int i=0; i<values.size(); i++) {
						String oldpath = values.get(i);
						newpath =  outputPath+"\\"+key + "\\" + oldpath.substring(oldpath.lastIndexOf("\\")+1);
						try {
							cf.copyFile(oldpath, newpath);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						message.setText("���ڵ���"+newpath);
						progress += 1.0/photoAmount*90;
						//System.out.println(progress);
						progressBar.setValue((int) progress);
					}
					
				} 
				
				progressBar.setValue(100);
				// ��ɺ���ɰ�ť�ɵ�
				btnFinish.setEnabled(true);
		   }
		};
		classfyThread.start();
		
	}
	
	private void initCompent() {
		// ���ñ���
		setTitle(title);
				
		mainPanel.remove(btnLast);
		mainPanel.remove(btnNext);
		mainPanel.remove(btnCancel);
		mainPanel.add(btnFinish);
		
		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean open = cbOpenFolder.isSelected();
				if(open) {
					try {
						Runtime.getRuntime().exec("explorer " + outputPath);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					
				}
				dispose();
			}
		});
		
		progressBar = getProgressBar();
		message = getMessage();
		cbOpenFolder = getCbOpenFolder();
		
		subPanel.add(progressBar);
		subPanel.add(message);
		subPanel.add(cbOpenFolder);
		
	};
	
	private JProgressBar getProgressBar() {
		JProgressBar progressBar = new JProgressBar();
		progressBar.setSize(600, 30);
		progressBar.setLocation(75,50);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		return progressBar;
	}
	
	private JLabel getMessage() {
		JLabel label = new JLabel();
		label.setText("���ڽ�����Ƭ����");
		label.setSize(550,30);
		label.setLocation(75,80);
		return label;
	}
	
	private JCheckBox getCbOpenFolder() {
		JCheckBox cb = new JCheckBox();
		cb.setSelected(true);  // Ĭ�ϴ�
		cb.setText("��ɺ���ļ���");
		cb.setSize(250,30);
		cb.setLocation(75,110);
		return cb;
	}

}
