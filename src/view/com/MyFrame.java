package view.com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;

public class MyFrame extends JFrame{

	public static int BUTTON_WIDTH = 60;
	public static int BUTTON_HEIGHT = 25;
	
	public JPanel mainPanel = null;
	public JPanel subPanel = null;
	
	public JButton btnLast = null;
	public JButton btnNext = null;
	public JButton btnCancel = null;
	public JButton btnFinish = null;
	
	
	public MyFrame() {
	
		
		
		initCompent();
		
		// 布局模式
		setLayout(null);
		// 设置JFrame大小
		setSize(800,600);
		// 设置JFrame位置
		setLocation(500,200);
		// 设置窗体大小固定
		setResizable(false);
		// 设置JFrame可见
		setVisible(true);
		
		
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	}
	
	private void initCompent() {
		mainPanel = getMainPanel();
		this.add(mainPanel);
		
		subPanel = getSubPanel();
		mainPanel.add(subPanel);
		
		btnLast = getBtnLast();
		btnNext = getBtnNext();
		btnCancel = getBtnCancel();
		mainPanel.add(btnLast);
		mainPanel.add(btnNext);
		mainPanel.add(btnCancel);
		
		btnFinish = getBtnFinish();
	}
	
	private JPanel getMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(750,520);
		panel.setLocation(0,0);
		return panel;
	}
	
	private JPanel getSubPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(750,485);
		panel.setLocation(0,0);
		return panel;
	}
	
	private JButton getBtnLast() {
		JButton btn = new JButton();
		btn.setText("上一步");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(550,490);
		return btn;
	}
	
	private JButton getBtnNext() {
		JButton btn = new JButton();
		btn.setText("下一步");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(615,490);
		return btn;
	}
	
	private JButton getBtnCancel() {
		JButton btn = new JButton();
		btn.setText("取消");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(680,490);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		return btn;
	}
	
	private JButton getBtnFinish() {
		JButton btn = new JButton();
		btn.setText("完成");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(680,490);
		btn.setEnabled(false);
		return btn;
	}
	
}
