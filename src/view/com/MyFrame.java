package view.com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
		
		// ���������е�����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setLayout(null);
		// ����JFrame��С
		setSize(800,600);
		// ����JFrameλ��
		setLocation(500,200);
		// ���ô����С�̶�
		setResizable(false);
		// ����JFrame�ɼ�
		setVisible(true);
				
		
		// ���ùرհ�ť������
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		
		initCompent();
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
		btn.setText("��һ��");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(550,490);
		return btn;
	}
	
	private JButton getBtnNext() {
		JButton btn = new JButton();
		btn.setText("��һ��");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(615,490);
		return btn;
	}
	
	private JButton getBtnCancel() {
		JButton btn = new JButton();
		btn.setText("ȡ��");
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
		btn.setText("���");
		btn.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
		btn.setLocation(680,490);
		btn.setEnabled(false);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		return btn;
	}
	
	public void finishCompent() {
		mainPanel.removeAll();
		mainPanel.add(btnFinish);
		mainPanel.repaint();
	}
	
}
