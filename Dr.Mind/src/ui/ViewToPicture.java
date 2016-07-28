package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;

/**
 * Created by Liu on 2016/7/11.
 */
public class ViewToPicture {

	public void save(View view, String name) {

		Bitmap bitmap = getBitmapFromView(view);
		System.out.println("bitmap "+saveBitmap(bitmap, name));
	}
	
	public  boolean saveBitmap(Bitmap bitmap, String fileName) {  
	    File file = new File(Environment.getExternalStorageDirectory().getPath());  
	    System.out.println("bitmap "+Environment.getExternalStorageDirectory().getPath());
	    if (!file.exists()) {  
	        file.mkdir();  
	    }  
	    File imageFile = new File(file, fileName);  
	    try {  
	        imageFile.createNewFile();  
	        FileOutputStream fos = new FileOutputStream(imageFile);  
	        bitmap.compress(CompressFormat.JPEG, 50, fos);  
	        fos.flush();  
	        fos.close();  
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

	public void saveMyBitmap(Bitmap mBitmap, String bitName) {
		File f = new File(Environment.getExternalStorageDirectory().getPath()+ bitName + ".jpg");
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
