package cn.edu.nju.drmind.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

/**
 * Created by Liu on 2016/7/11.
 */
public class ViewToPicture {

	public boolean save(View view, String name,Context context) {

		Bitmap bitmap = getBitmapFromView(view);
        if(saveBitmap(bitmap, name,context)) return true;
        return false;
	}

	public boolean saveBitmap(Bitmap bitmap, String fileName,Context context) {
		String path = Environment.getExternalStorageDirectory().getPath();
		File file = new File(path+"/Dr.Mind");
		System.out.println("bitmap path :" + path);
		
		fileName=fileName+".jpg";
		
		File[] fileList = file.listFiles();
		for(int i=0;i<fileList.length;i++){
			System.out.println(fileList[i].getName());
			if(fileName.equals(fileList[i].getName())){
				return false;
			}
		}
		
		
		if (!file.exists()) {
			file.mkdir();
		}
	

		//File f=new File(path+"/Dr.Mind"+fileName+".jpg");
		
		File imageFile = new File(file, fileName);
		

		try {
			System.out.println("bitmap saveBitmap");
			imageFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
			fos.flush();
			fos.close();
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(file);
			intent.setData(uri);
			context.sendBroadcast(intent);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	private Bitmap getBitmapFromView(View view) {
		Bitmap bitmap = null;
		try {
			System.out.println("bitmap getBitmapFromView");
			int width = view.getWidth();
			int height = view.getHeight();
			if (width != 0 && height != 0) {
				bitmap = Bitmap.createBitmap(width, height,
						Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				view.layout(0, 0, width, height);
				view.draw(canvas);
			}
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}

		return bitmap;
	}

}