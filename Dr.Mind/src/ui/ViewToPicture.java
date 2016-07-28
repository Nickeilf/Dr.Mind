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
        saveBitmap(bitmap, name);
	}

	public boolean saveBitmap(Bitmap bitmap, String fileName) {
		String path = Environment.getExternalStorageDirectory().getPath();
		File file = new File(path);
		System.out.println("bitmap path :" + path);
		if (!file.exists()) {
			file.mkdir();
		}
		File imageFile = new File(file, fileName);
		try {
			System.out.println("bitmap saveBitmap");
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
