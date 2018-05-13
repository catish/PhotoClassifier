package view.classify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helper.ClassifyHelper;
import helper.FileInfoHelper;
import view.com.MyFrame;

public class ClassifyStep4Frame extends MyFrame{
	
	private String title = null;
	private String method = null;
	private String outputPath = null;
	private List<String> folderPaths = null;
	
	private FileInfoHelper fih = null;
	private ClassifyHelper cf = null;
	
	public ClassifyStep4Frame(String t, List<String> folders, String m, String op) {
		title = t;
		folderPaths = folders;
		method = m;
		outputPath = op;
		
		initCompent();
		
		// ��ʼ������
		
		// ��ȡ������Ƭ·��
		fih = new FileInfoHelper();
		cf = new ClassifyHelper();
		
		List<String> photoPaths = new ArrayList<String>();
		for(String fp : folderPaths) {
			photoPaths.addAll(fih.readPhotoPaths(fp));
		}
		/*
		for(String test : photoPaths) {
			System.out.println(test);
		}
		*/
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
		
		// ����
		try {
			cf.export(outputPath, res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//dispose();
		
		
	}
	
	private void initCompent() {
		finishCompent();
	};

}
