package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class paintDao {

	private SqliteDBHelper sqliteDBHelper;
	private SQLiteDatabase db;
	
	//重写构造方法
	public paintDao(Context context){
		this.sqliteDBHelper = new SqliteDBHelper(context);
		db = sqliteDBHelper.getWritableDatabase();
	}
	
	//读操作
	public String execQuery(final String strSQL){
		try{
			System.out.println("strSQL>"+strSQL);
			Cursor cursor =db.rawQuery(strSQL, null);
			//始终让cursor指向数据库表的第1行记录
			cursor.moveToFirst();
			//定义一个StringBuffer的对象，用于动态拼接字符串
			StringBuffer sb = new StringBuffer();
			//循环游标，如果不是最后一项记录
			while(!cursor.isAfterLast()){
				
				  //cursor游标移动
			    cursor.moveToNext();
			}
			db.close();
			return sb.deleteCharAt(sb.length()-1).toString();
	 }catch(RuntimeException e){
		 e.printStackTrace();
		 return null;
	 }
}
}