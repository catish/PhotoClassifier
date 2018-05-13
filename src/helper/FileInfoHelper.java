package helper;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import com.drew.*;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.exif.GpsDirectory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FileInfoHelper {
	
	// 给定路径获取读取所有图片的绝对路径
	public List<String> readPhotoPaths(String fileDir) {  
		List<String> photoPaths = new ArrayList<String>();
		List<File> fileList = new ArrayList<File>();  
        File file = new File(fileDir);  
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹  
        if (files == null) {// 如果目录为空，直接退出  
            return null;  
        }  
        // 遍历，目录下的所有文件  
        for (File f : files) {  
            if (f.isFile()) {  
            	if(f.getName().endsWith(".JPG") || f.getName().endsWith(".jpg")) {
            		fileList.add(f);
            	}
            } else if (f.isDirectory()) {  
            	photoPaths.addAll(readPhotoPaths(f.getAbsolutePath()));  
            }  
        }  
        for (File f1 : fileList) {  
        	photoPaths.add(f1.getAbsolutePath());  
        }  
        return photoPaths;
    }  
	
	// 读取图片时间信息
		public String readPhotoTime(String photoDir) {
			String s = null;
			File jpegFile = new File(photoDir);
			Metadata metadata = null;
			try {
				metadata = JpegMetadataReader.readMetadata(jpegFile);
				
			} catch (JpegProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			Directory exif = metadata.getDirectory(ExifDirectory.class);
			s = exif.getString(ExifDirectory.TAG_DATETIME_ORIGINAL);
			//System.out.println(s);
			return(s);
		}
	
		
	// 读取图片经度
	private String readPhotoLong(String photoDir) {
		String s = null;
		File jpegFile = new File(photoDir);
		Metadata metadata = null;
		try {
			metadata = JpegMetadataReader.readMetadata(jpegFile);
			
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(photoDir + "is wrong"); 
		}  
		Directory gps = metadata.getDirectory(GpsDirectory.class);
		String s1 = gps.getString(GpsDirectory.TAG_GPS_LONGITUDE);
		String s2 = gps.getString(GpsDirectory.TAG_GPS_LONGITUDE_REF);
		
		if(hasGps(s1)) {
			s = gpsTrans(s1);
			if(s2.equals("W")) {
				s = "-"+s;
			}
		}	
		return s;
	}
	
	// 读取图片纬度
	private String readPhotoLat(String photoDir) {
		String s = null;
		File jpegFile = new File(photoDir);
		Metadata metadata = null;
		try {
			metadata = JpegMetadataReader.readMetadata(jpegFile);
			
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		Directory gps = metadata.getDirectory(GpsDirectory.class);
		String s1 = gps.getString(GpsDirectory.TAG_GPS_LATITUDE);
		String s2 = gps.getString(GpsDirectory.TAG_GPS_LATITUDE_REF);
		
		if(hasGps(s1)) {
			s = gpsTrans(s1);
			if(s2.equals("S")) {
				s = "-"+s;
			}
		}	
		return s;
	}
	
	// 给定经纬度获取地址信息
	public String readPhotoAddress(String photoDir){
		String log = readPhotoLong(photoDir);
		String lat = readPhotoLat(photoDir);
		if(log==null || lat==null){
			return "未知";
		}
		//lat 小  log  大    
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)   
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+log+"&type=010";    
        String add = "";       
        try {       
            URL url = new URL(urlString);      
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();      
            conn.setDoOutput(true);      
            conn.setRequestMethod("POST");      
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));      
            String line;      
           while ((line = in.readLine()) != null) {      
               add += line+"\n";      
         }      
            in.close();      
        } catch (Exception e) {      
            System.out.println("error in wapaction,and e is " + e.getMessage());      
        }     
        
        JSONObject jsonObject = JSONObject.fromObject(add);    
        JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList"));    
        JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0));    
        String allAdd = j_2.getString("admName");    
        return allAdd;      
    }  
	
	// 判断照片是否有
	private boolean hasGps(String s) {
		//System.out.println(s);
		if(s==null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// 经纬度转换小助手
	private String gpsTrans(String si) {
		String so = null;
		float d,f,m;
		String s[] = si.split(" ");
    	
    	s[0] = s[0].split("/")[0];
    	d = Integer.parseInt(s[0]);
    	
    	s[1] = s[1].split("/")[0];
    	f = Integer.parseInt(s[1]);
    	f = f/60;
    	/*
    	s[2] = s[2].split("/")[0];
    	m = Integer.parseInt(s[2]);
    	m = m/1000000/3600;
    	*/
    	//so = String.valueOf(d+f+m);
    	so = String.valueOf(d+f);
		return so;
	}
	
	// 读取图片类别信息
	public Map<String,String> readPhotoClass(List<String> photoPaths) {
		Map<String,String> photoInfos = new HashMap<String,String>();
		try {
			int size = photoPaths.size();
			String[] args1 = new String[size+4]; 
			for(int i=0; i<size; i++) {
				args1[i+4] = photoPaths.get(i);
			}
			args1[0] = "python";
			args1[1] = "python/photoClassify.py";
			args1[2] = "python/model/my_photo_classify.pb";
			args1[3] = String.valueOf(size); 
			
            Process pr=Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(
            		pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
            	//System.out.println(line);
            	String[] temp = line.split(",");
            	photoInfos.put(temp[0], temp[1]);
            }
            in.close();
            pr.waitFor();
       } catch (Exception e) {
        e.printStackTrace();
       }
		return photoInfos;
	}
	
}
