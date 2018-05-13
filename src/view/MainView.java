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
		// ��ʼ�����
		initCompent();

		// ����JFrame�ɼ�
		setVisible(true);
		// ����JFrame��С
		setSize(800,600);
		// ����JFrameλ��
		setLocation(500,200);
		// ����JFrame���ַ�ʽ
		//setLayout(null);
		// ��رհ�ťʱ�˳�����
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
		btn.setText("ʱ��");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ��ʱ�����
				ClassifyStep1Frame cf = new ClassifyStep1Frame("��ʱ�����");
			}
		});
		return btn;
	}

	private JButton getBtnAddress() {
		JButton btn = new JButton();
		btn.setText("�ص�");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ���ص����
				ClassifyStep1Frame cf = new ClassifyStep1Frame("���ص����");
			}
		});
		return btn;
	}

	private JButton getBtnClass() {
		JButton btn = new JButton();
		btn.setText("���");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//����� ����
				ClassifyStep1Frame cf = new ClassifyStep1Frame("��������");
			}
		});
		return btn;
	}

	public static void main(String[] args) {
		
		// ʹ�����
		try{
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        
	    }
	    catch(Exception e){
	        //TODO exception
	    }
	
		
		MainView mv = new MainView();
		
	}

}
