package helper;

import java.util.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
	public List<String> readFilePaths(String fileDir) {  
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
            	if(f.getName().endsWith(".JPG") || f.getName().endsWith(".jpg") ||
            			f.getName().endsWith(".PNG") || f.getName().endsWith(".png") )
            	fileList.add(f);
            } else if (f.isDirectory()) {  
            	photoPaths.addAll(readFilePaths(f.getAbsolutePath()));  
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
	
	
}
