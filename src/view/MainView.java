package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import view.classify.ClassifyStep1Frame;
import view.classify.ClassifyStep4Frame;


public class MainView extends JFrame{

	private JPanel mainPanel = null;
	
	private JButton btnTime = null;
	private JButton btnAddress = null;
	private JButton btnClass = null;
	
	public MainView(){
		// 初始化组件
		initCompent();

		// 设置JFrame可见
		setVisible(true);
		// 设置JFrame大小
		setSize(800,600);
		// 设置JFrame位置
		setLocation(500,200);
		// 设置JFrame布局方式
		//setLayout(null);
		// 点关闭按钮时退出程序
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void initCompent() {
		mainPanel = getMainPanel();
		this.add(mainPanel);
		
		btnTime = getBtnTime();
		btnAddress = getBtnAddress();
		btnClass = getBtnClass();
		mainPanel.add(btnTime);
		mainPanel.add(btnAddress);
		mainPanel.add(btnClass);
		
				
		
	}
	

	
	
	
	
	private JPanel getMainPanel() {
		JPanel panel = new JPanel();
		//panel.setLayout(null);
		return panel;
	}

	private JButton getBtnTime() {
		JButton btn = new JButton();
		btn.setText("时间");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 按时间分类
				ClassifyStep1Frame cf = new ClassifyStep1Frame("按时间分类");
			}
		});
		return btn;
	}

	private JButton getBtnAddress() {
		JButton btn = new JButton();
		btn.setText("地点");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 按地点分类
				ClassifyStep1Frame cf = new ClassifyStep1Frame("按地点分类");
			}
		});
		return btn;
	}

	private JButton getBtnClass() {
		JButton btn = new JButton();
		btn.setText("类别");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//按类别 分类
				ClassifyStep1Frame cf = new ClassifyStep1Frame("按类别分类");
			}
		});
		return btn;
	}

	public static void main(String[] args) {
		
		// 使用外观
		try{
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        
	    }
	    catch(Exception e){
	        //TODO exception
	    }
	
		
		MainView mv = new MainView();
		
	}

}
