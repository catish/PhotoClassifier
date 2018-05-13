package view.classify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import view.com.MyFrame;

public class ClassifyStep3Frame extends MyFrame{
	
	private String title = null;
	private String method = null;
	private String outputPath = null;
	private List<String> folderPaths = null;
	
	private JLabel message = null;
	private JTextField txOutputPath = null;
	private JButton btnChoosePath = null;
	
	FileSystemView fsv = null;
	
	public ClassifyStep3Frame(String t, List<String> folders, String m) {
		title = t;
		folderPaths = folders;
		method = m;
		
		// ����Ĭ��·��Ϊ  �û�/ͼƬ
		fsv = javax.swing.filechooser.FileSystemView.getFileSystemView();
		outputPath = fsv.getDefaultDirectory().getParent() + "\\Pictures";
		
		
		initCompent();
	}
	
	private void initCompent() {
		// ���ñ���
		setTitle(title);
				
		btnLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(title.equals("��ʱ�����")||title.equals("���ص����")) {
					new ClassifyStep2Frame(title, folderPaths, method);	
				} else if(title.equals("��������")) {
					new ClassifyStep1Frame(title, folderPaths);
				}
				dispose();
			}
		});
		
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// �ǿռ��
				dispose();
				new ClassifyStep4Frame(title, folderPaths, method, outputPath);
			}
		});
		
		message = getMessage();
		txOutputPath = getTxOutputPath();
		btnChoosePath = getChoosePath();
		subPanel.add(message);
		subPanel.add(txOutputPath);
		subPanel.add(btnChoosePath);
	}
	
	private JLabel getMessage() {
		JLabel label = new JLabel();
		label.setText("��ѡ�񵼳�ͼƬ·��:");
		label.setSize(200,50);
		label.setLocation(75,150);
		return label;
	}
	
	private JTextField getTxOutputPath() {
		JTextField tf = new JTextField();
		tf.setText(outputPath);
		tf.setSize(470,25);
		tf.setLocation(100, 200);
		return tf;
	}
	
	private JButton getChoosePath() {
		JButton btn = new JButton();
		btn.setText("���");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(575,200);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseOutputPath();
			}
		});
		return btn;
	}
	
	
	
	private void chooseOutputPath() {
		// ��ȡ�ļ���·��
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // ֻ��ѡ��Ŀ¼
		chooser.setMultiSelectionEnabled(false);  // ���� 
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {  
			File selectedFile = chooser.getSelectedFile();
			outputPath = selectedFile.getAbsolutePath();
		}
		
		// ����TextField
		txOutputPath.setText(outputPath);
		
	}

}
