package view.classify;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import helper.FileInfoHelper;
import view.com.MyFrame;

public class ClassifyStep1Frame extends MyFrame{
	
	private String title = null;
	private List<String> folderPaths = null;
	private List<String> photoPaths = null;
	
	private JButton btnAdd = null;
	private JButton btnDelete = null;
	
	private JScrollPane scrollFilePath = null;
	private JScrollPane scrollPhotoPane = null;
	private JList<String> listFilePath = null;	
	
	private JPanel photoPanel = null;
	
	public ClassifyStep1Frame(String t) {
		title = t;
		folderPaths = new ArrayList<String>();
		photoPaths = new ArrayList<String>();
		
		initCompent();
	}
	
	public ClassifyStep1Frame(String t, List<String> folders) {
		title = t;
		folderPaths = folders;
		photoPaths = new ArrayList<String>();
		
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
				// 导入照片路径
				FileInfoHelper fip = new FileInfoHelper();
				for(String p : folderPaths) {
					photoPaths.addAll( fip.readPhotoPaths(p) );
				}
				
				// 非空检查
				boolean photoIsEmpty = photoPaths.isEmpty();
				//
				if(photoIsEmpty) {
					JOptionPane.showMessageDialog(null, "请导入要分类的照片", "警告", JOptionPane.ERROR_MESSAGE);
				} else {
					if(title.equals("按时间分类")||title.equals("按地点分类")) {
						new ClassifyStep2Frame(title, folderPaths);
						
					} else if(title.equals("按类别分类")) {
						new ClassifyStep3Frame(title, folderPaths,"按类别");
					}
					dispose();
				}
			}
		});
		
		// 初始化控件
		btnAdd = getBtnAdd();
		btnDelete = getBtnDelete();
		scrollFilePath = getScrollFilePath();
		listFilePath = getListFilePath();
		scrollFilePath.setViewportView(listFilePath);
		
		scrollPhotoPane = getScrollPhotoPane();
		//此处缺代码
		
		
		subPanel.add(btnAdd);
		subPanel.add(btnDelete);
		subPanel.add(scrollFilePath);
		subPanel.add(scrollPhotoPane);
		
		/*
		// 也是测试
		photoPanel = new JPanel(new FlowLayout());
		photoPanel.setMinimumSize(new Dimension(450,460));
		//photoPanel.setPreferredSize(new Dimension(450,460));
		photoPanel.setMaximumSize(new Dimension(450,460));
		
		scrollPhotoPane.setViewportView(photoPanel);
		 */

	}
	
	private JButton getBtnAdd() {
		JButton btn = new JButton();
		btn.setText("添加");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(5,5);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 不可能为空，不需要非空检查
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
				// 非空检查
				List<String> deletePaths = listFilePath.getSelectedValuesList();
				if(deletePaths.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请选择要删除的路径", "警告", JOptionPane.ERROR_MESSAGE);
				} else {
					// 删除照片路径
					deleteFilePath(deletePaths);
				}
			}
		});
		return btn;
	}
	
	private JScrollPane getScrollFilePath() {
		JScrollPane scroll = new JScrollPane();
		scroll.setSize(250,450);
		scroll.setLocation(5,35);
		return scroll;
	}
	
	private JScrollPane getScrollPhotoPane() {
		JScrollPane scroll = new JScrollPane();
		scroll.setSize(480,480);
		scroll.setLocation(260,5);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scroll;
	}
	
	private JList<String> getListFilePath() {
		JList<String> list = new JList<String>();
		return list;
	}
	
	private JList<ImageIcon> getListPhoto() {
		JList<ImageIcon> list = new JList<ImageIcon>();
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
		// 获取文件夹路径， 并按照逻辑存入folderPaths
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // 只能选择目录
		chooser.setMultiSelectionEnabled(true);  // 可以多选 
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {  
			File[] selectedFiles = chooser.getSelectedFiles();  // 获得选择的文件夹的绝对路径
			for(File f : selectedFiles) {
				String newPath = f.getAbsolutePath();
				int temp = 0;
				// 路径检查
				for(int i=0; i<folderPaths.size(); i++) {
					// 添加的路径是已经存在的路径本身或子目录 = 什么也不做
					if(newPath.startsWith(folderPaths.get(i))) {
						temp = 1;
						break;
					} else if(folderPaths.get(i).startsWith(newPath)) {  //添加的路径是已经存在父目录  = 移除所有子目录， 添加父目录
						folderPaths.remove(folderPaths.get(i));
					}
				}
				if(temp ==1) {
					// 什么也不做
				} else {
					folderPaths.add(newPath);
				}
			}
		}

		// 更新JList
		updateListFilePath(folderPaths);
		
		//这功能做的我头疼
		/*
		// 更新预览图
		
		
		// 读取照片
		ImageIcon[] icons = new ImageIcon[photoPaths.size()];
		for(int i=0; i<photoPaths.size(); i++) {
			ImageIcon icon = GetImageIcon(new ImageIcon(photoPaths.get(i)));
			//System.out.println(p);
			icons[i] = icon;
			JLabel l = new JLabel();
			l.setIcon(icon);
			l.setPreferredSize(new Dimension(105,105));
			photoPanel.add(l);
			//太慢了看一眼进度
			System.out.println(i);
		}
		// 更新照片

		photoPanel.updateUI();
		
		*/
		
		
		
	}
	

	private void deleteFilePath(List<String> paths) {
		// 删除路径
		for(String p:paths) {
			folderPaths.remove(p);
		}
		
		// 更新JList
		updateListFilePath(folderPaths);
	};
	

	
	/*
	// 都是测试用的方法
	private ImageIcon GetImageIcon(ImageIcon imageicon) {
        //File filetwo = new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText());
        //ImageIcon imageicon = new ImageIcon(filetwo.getAbsolutePath());
        double h1 = imageicon.getIconHeight();
        double w1 = imageicon.getIconWidth();
        if (h1 < 77 && w1 < 100) {
            Image image = imageicon.getImage().getScaledInstance((int) w1, (int) h1, Image.SCALE_DEFAULT);//鏀瑰彉澶у皬
            ImageIcon Finalii = new ImageIcon(image);
            return Finalii;

        } else {
            if (h1 * 180 > w1 * 142) {
                Image image = imageicon.getImage().getScaledInstance((int) (w1 / (h1 / 81)), 81, Image.SCALE_DEFAULT);//鏀瑰彉澶у皬
                ImageIcon Finalii = new ImageIcon(image);
                return Finalii;
            } else {
                Image image = imageicon.getImage().getScaledInstance(105, (int) (h1 / (w1 / 105)), Image.SCALE_DEFAULT);//鏀瑰彉澶у皬
                ImageIcon Finalii = new ImageIcon(image);
                return Finalii;
            }
        }
    }
    */
}
