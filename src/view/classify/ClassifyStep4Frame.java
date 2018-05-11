package view.classify;

import java.util.ArrayList;
import java.util.List;

import helper.FileInfoHelper;
import view.com.MyFrame;

public class ClassifyStep4Frame extends MyFrame{
	
	private String title = null;
	private String method = null;
	private List<String> folderPaths = null;
	
	private FileInfoHelper fih = null;
	
	public ClassifyStep4Frame(String t, List<String> folders, String m) {
		title = t;
		folderPaths = folders;
		method = m;
		
		initCompent();
		
		// ��ʼ������
		fih = new FileInfoHelper();
		List<String> photoPaths = new ArrayList<String>();
		for(String fp : folderPaths) {
			photoPaths.addAll(fih.readFilePaths(fp));
		}
		
		for(String p:photoPaths) {
			System.out.println(p);
		}
		// ��ȡ������Ƭ·��
		// ����method��ȡ��ǩ��Ϣ
		// ����
	}
	
	private void initCompent() {
		finishCompent();
	};

}
