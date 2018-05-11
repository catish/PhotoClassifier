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
		
		// 开始工作了
		fih = new FileInfoHelper();
		List<String> photoPaths = new ArrayList<String>();
		for(String fp : folderPaths) {
			photoPaths.addAll(fih.readFilePaths(fp));
		}
		
		for(String p:photoPaths) {
			System.out.println(p);
		}
		// 读取所有照片路径
		// 根据method获取标签信息
		// 导出
	}
	
	private void initCompent() {
		finishCompent();
	};

}
