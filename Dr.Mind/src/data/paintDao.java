package data;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Columns;

public class paintDao {

	private SqliteDBHelper sqliteDBHelper;
	private SQLiteDatabase db;
	//private DBManager;
	
	//重写构造方法
	public paintDao(Context context){
		sqliteDBHelper = new SqliteDBHelper(context);
		DBManager.initializeInstance(sqliteDBHelper);
		//db = DBManager.getInstance().openDatabase();
		db = sqliteDBHelper.getWritableDatabase();
		//db1=sqliteDBHelper.getReadableDatabase();
	}
	
	//根据表名读操作
	public String execQuery(String paintName ){
		try{
			Cursor cursor =db.query("Paint",null,null,null,null,null,null);
			//始终让cursor指向数据库表的第1行记录
			cursor.moveToFirst();
			//定义一个StringBuffer的对象，用于动态拼接字符串
			StringBuffer sb = new StringBuffer();
			//循环游标，如果不是最后一项记录
			int i=0;
			while(!cursor.isAfterLast()){
				if(cursor.getString(cursor.getColumnIndex("paintName")).equals(paintName)){
				  sb.append(cursor.getInt(cursor.getColumnIndex("id")) + "/" + cursor.getString(cursor.getColumnIndex("paintName")) + "/"
	                        + cursor.getInt(cursor.getColumnIndex("root")) + "/" + cursor.getInt(cursor.getColumnIndex("parent")) + "/"
	                        + cursor.getInt(cursor.getColumnIndex("leftChild"))+"/"+cursor.getInt(cursor.getColumnIndex("rightChild"))+"/"
	                        +cursor.getString(cursor.getColumnIndex("textValue"))+"/"+cursor.getInt(cursor.getColumnIndex("level"))+"#");
				  i++;
				}
				  //cursor游标移动
			    cursor.moveToNext();
			}
			DBManager.getInstance().closeDatabase();
			//db.close();
			//cursor.close();
			//return sb.deleteCharAt(sb.length()-1).toString();
			System.out.println(sb.toString());
			return sb.toString();
	 }catch(RuntimeException e){
		 e.printStackTrace();
		 return null;
	 }
}
	
	// 写操作
    public boolean execOther(final String strSQL) {
        db.beginTransaction();  //开始事务
        try {
            System.out.println("strSQL" + strSQL);
            db.execSQL(strSQL);
            db.setTransactionSuccessful();  //设置事务成功完成 
            db.close();
            DBManager.getInstance().closeDatabase();
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }finally {  
            db.endTransaction();    //结束事务  
        }  

    }
    
    //插入
    public boolean insert(int id,String paintname,int root,int parent,int leftchild,int rightchild,
    		String textvalue,int level){
		 ContentValues cv =new ContentValues();
		 cv.put("id",id);
		 cv.put("paintName", paintname);
		 cv.put("root", root);
		 cv.put("parent", parent);
		 cv.put("leftChild", leftchild);
		 cv.put("rightChild", rightchild);
		 cv.put("textValue", textvalue);
		 cv.put("level", level);
		 System.out.println(id+"+"+paintname+"+"+leftchild+"+"+rightchild+"+"+textvalue+"+"+level+"..................");
    	 db.insert("Paint",null,cv);
    	 System.out.println("Insert successfully!");
    	 //DBManager.getInstance().closeDatabase();
    	 //db.close();
    	return true;
    	
    }
    
    //delete
    public void delete(String paintName){
    	db.delete("Paint","paintName=?",new String[]{paintName});
    	System.out.println("delete "+paintName);
    }
    
    //update
    public void update(String paintName){
    	
    }
    
    //isExistPaint
    public boolean isExistPaint(String paintname){
    	boolean isExist=false;
    	try{
			Cursor cursor =db.query("Paint",null,null,null,null,null,null);
			//始终让cursor指向数据库表的第1行记录
			cursor.moveToFirst();
			//定义一个StringBuffer的对象，用于动态拼接字符串
			StringBuffer sb = new StringBuffer();
			//循环游标，如果不是最后一项记录
			while(!cursor.isAfterLast()){
				if(cursor.getString(cursor.getColumnIndex("paintName")).equals(paintname)){
				   isExist=true;
				}
				  //cursor游标移动
			    cursor.moveToNext();
			}
		//	db.close();
	 }catch(RuntimeException e){
		 e.printStackTrace();
	 }
    	System.out.println("is Exist "+isExist);
		return isExist;
    	
    }
    
}

