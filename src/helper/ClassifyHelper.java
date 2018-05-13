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

	// ��ʱ��
	public Map<String, List<String>> byTime(Map<String,String> photoInfos,  String method) {
		for(String path : photoInfos.keySet()) {
			String time = photoInfos.get(path);
			if(time.equals("δ֪")) {
				photoInfos.put(path, "δ֪");
			} else {
				String[] temp = time.split(":");
				if(method.equals("���·�")) {
					photoInfos.put(path, temp[0] + temp[1]); 
				} else if(method.equals("�����")) {
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
	
	// ���ص�
	public Map<String, List<String>> byAddress(Map<String,String> photoInfos,  String method) {
		for(String path : photoInfos.keySet()) {
			String add = photoInfos.get(path);
			if(add.equals("δ֪")) {
				photoInfos.put(path, "δ֪");
			} else {
				String[] temp = add.split(",");
				if(method.equals("��ʡ��")) {
					photoInfos.put(path, temp[0]); 
				} else if(method.equals("������")) {
					photoInfos.put(path, temp[0] + temp[1]);
				}
				else if(method.equals("��������")) {
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
	
	//�����
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
	
	//�������ļ���д��
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
		
		// �½��ļ���
		private void CreateFolder(String p, String n) {
			String path = p+"\\"+n;
			try {
	            File foler = new File(path);
	            if(!foler .exists()){ // ����ļ��в����� �������ļ���
	                (new File(path)).mkdirs(); 
	            }
			}catch (Exception e) {
	            System.out.println("�����ļ���ʧ��");
	            e.printStackTrace();
			}		
		}
		
		// ����ͼƬ
		private void copyFile(String srcPath, String destPath) throws IOException {
	        try {
	        	// ��������
	            FileInputStream fis = new FileInputStream(srcPath);
	            // �������
	            FileOutputStream fos = new FileOutputStream(destPath);
	            
	            // ��ȡ��д����Ϣ
	            int len = 0;
	            // ����һ���ֽ����飬����������
	            byte[] b = new byte[1024];
	            while ((len = fis.read(b)) != -1) {
	                fos.write(b, 0, len);
	            }
	            
	            // �ر���  �ȿ����  ���ȹ�
	            fos.close(); // ���ȹ�
	            fis.close(); // �ȿ����
	        }catch(IOException e) {
	        	e.printStackTrace();
	        }
	        
	        
	    }
		
}
