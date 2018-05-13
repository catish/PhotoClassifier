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
		     	// 开始工作了
				
			   	double progress = 0;
			   
			    message.setText("正在读取照片");
			    progress = 3.0;
			    progressBar.setValue((int) progress);
				
				// 读取所有照片路径
				fih = new FileInfoHelper();
				cf = new ClassifyHelper();
				
				List<String> photoPaths = new ArrayList<String>();
				for(String fp : folderPaths) {
					photoPaths.addAll(fih.readPhotoPaths(fp));
				}
				
				int photoAmount = photoPaths.size();
				
				// 读取照片完成
				progress = 7.0;

				//
				message.setText("正在获取照片类别信息");
				progressBar.setValue((int) progress);
				
				
				// 根据method获取标签信息
				Map<String, List<String>> res = null;
				if(title.equals("按时间分类")) {
					// 读取照片时间信息
					Map<String,String> photoInfos = new HashMap<String,String>();
					for(String k : photoPaths) {
						photoInfos.put(k, fih.readPhotoTime(k));
					}
					// 获取分类结果
					res = cf.byTime(photoInfos, method);
				} else if(title.equals("按地点分类")) {
					// 读取照片地点信息
					Map<String,String> photoInfos = new HashMap<String,String>();
					for(String k : photoPaths) {
						photoInfos.put(k, fih.readPhotoAddress(k));
					}
					// 获取分类结果
					res = cf.byAddress(photoInfos, method);
				} else if(title.equals("按类别分类")) {
					// 读取照片类别信息
					Map<String,String> photoInfos = new HashMap<String,String>();
					photoInfos = fih.readPhotoClass(photoPaths);
					// 获取分类结果
					res = cf.byClass(photoInfos);
				}
				progress = 10.0;
				progressBar.setValue((int) progress);
				
				/*
				// 导出
				try {
					cf.export(outputPath, res);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
				// 导出照片
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
						message.setText("正在导出"+newpath);
						progress += 1.0/photoAmount*90;
						//System.out.println(progress);
						progressBar.setValue((int) progress);
					}
					
				} 
				
				progressBar.setValue(100);
				// 完成后完成按钮可点
				btnFinish.setEnabled(true);
		   }
		};
		classfyThread.start();
		
	}
	
	private void initCompent() {
		// 设置标题
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
		label.setText("正在进行照片分类");
		label.setSize(550,30);
		label.setLocation(75,80);
		return label;
	}
	
	private JCheckBox getCbOpenFolder() {
		JCheckBox cb = new JCheckBox();
		cb.setSelected(true);  // 默认打开
		cb.setText("完成后打开文件夹");
		cb.setSize(250,30);
		cb.setLocation(75,110);
		return cb;
	}

}
