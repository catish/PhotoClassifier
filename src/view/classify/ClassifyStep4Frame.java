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
		
		// 开始工作了
		
		// 读取所有照片路径
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
		
		// 导出
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
