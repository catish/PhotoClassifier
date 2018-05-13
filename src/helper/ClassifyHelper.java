package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class ClassifyHelper {

	// 按时间
	public Map<String, List<String>> byTime(Map<String,String> photoInfos,  String method) {
		for(String path : photoInfos.keySet()) {
			String time = photoInfos.get(path);
			if(time.equals("未知")) {
				photoInfos.put(path, "未知");
			} else {
				String[] temp = time.split(":");
				if(method.equals("按月份")) {
					photoInfos.put(path, temp[0] + temp[1]); 
				} else if(method.equals("按年份")) {
					photoInfos.put(path, temp[0]);
				}
			}
			
			
		}
		
		Map<String, List<String>> res = new HashMap<String, List<String>>();
		for(String time : photoInfos.values()) {
			res.put(time, new ArrayList<String>());
		}
	    for (String path : photoInfos.keySet()) {
		    String t = photoInfos.get(path);
		    ArrayList<String> l = (ArrayList<String>) res.get(t);
		    l.add(path);
		    res.put(t, l);		    
	    }
		
		return res;
	}
	
	// 按地点
	public Map<String, List<String>> byAddress(Map<String,String> photoInfos,  String method) {
		for(String path : photoInfos.keySet()) {
			String add = photoInfos.get(path);
			if(add.equals("未知")) {
				photoInfos.put(path, "未知");
			} else {
				String[] temp = add.split(",");
				if(method.equals("按省份")) {
					photoInfos.put(path, temp[0]); 
				} else if(method.equals("按城市")) {
					photoInfos.put(path, temp[0] + temp[1]);
				}
				else if(method.equals("按行政区")) {
					photoInfos.put(path, temp[0] + temp[1] + temp[2]);
				}
			}
		}
		
		Map<String, List<String>> res = new HashMap<String, List<String>>();
		for(String add : photoInfos.values()) {
			res.put(add, new ArrayList<String>());
		}
	    for (String path : photoInfos.keySet()) {
		    String t = photoInfos.get(path);
		    ArrayList<String> l = (ArrayList<String>) res.get(t);
		    l.add(path);
		    res.put(t, l);		    
	    }
		
		return res;
	}
	
	//按类别
	public Map<String, List<String>> byClass(Map<String,String> photoInfos) {
		for(String path : photoInfos.keySet()) {
			String photoclass = photoInfos.get(path);
		}
		
		Map<String, List<String>> res = new HashMap<String, List<String>>();
		for(String c : photoInfos.values()) {
			res.put(c, new ArrayList<String>());
		}
	    for (String path : photoInfos.keySet()) {
		    String t = photoInfos.get(path);
		    ArrayList<String> l = (ArrayList<String>) res.get(t);
		    l.add(path);
		    res.put(t, l);		    
	    }
		
		return res;
	}
	
	//导出（文件读写）
		public void export(String p, Map<String, List<String>> res) throws IOException {
			Set<String> keys = res.keySet();
			List<String> values = null;
			String newpath =  null;
			for (String key : keys) {  
				//System.out.println(key);  
				CreateFolder(p,key);
				values = res.get(key);
				for( String oldpath : values) {
					newpath =  p+"\\"+key + "\\" + oldpath.substring(oldpath.lastIndexOf("\\")+1);
					//System.out.println(newpath);
					copyFile(oldpath,newpath);
				}
			} 
		}
		
		// 新建文件夹
		private void CreateFolder(String p, String n) {
			String path = p+"\\"+n;
			try {
	            File foler = new File(path);
	            if(!foler .exists()){ // 如果文件夹不存在 则建立新文件夹
	                (new File(path)).mkdirs(); 
	            }
			}catch (Exception e) {
	            System.out.println("创建文件夹失败");
	            e.printStackTrace();
			}		
		}
		
		// 复制图片
		private void copyFile(String srcPath, String destPath) throws IOException {
	        try {
	        	// 打开输入流
	            FileInputStream fis = new FileInputStream(srcPath);
	            // 打开输出流
	            FileOutputStream fos = new FileOutputStream(destPath);
	            
	            // 读取和写入信息
	            int len = 0;
	            // 创建一个字节数组，当做缓冲区
	            byte[] b = new byte[1024];
	            while ((len = fis.read(b)) != -1) {
	                fos.write(b, 0, len);
	            }
	            
	            // 关闭流  先开后关  后开先关
	            fos.close(); // 后开先关
	            fis.close(); // 先开后关
	        }catch(IOException e) {
	        	e.printStackTrace();
	        }
	        
	        
	    }
		
}
