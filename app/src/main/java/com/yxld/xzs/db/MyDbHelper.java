package com.yxld.xzs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yxld.xzs.entity.NfcUserEntity;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.entity.XunJianShiJianEntity;
import com.yxld.xzs.entity.XunJianShijianClassifyEntity;
import com.yxld.xzs.entity.XunJianXiangEntity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MyDbHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "Nfc.db";

	private static final int DATABASE_VERSION = 1; //v1.1.1
//	private static final int DATABASE_VERSION = 2; //v1.2.0

	public MyDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		creatNefTable(db, XunJianJiLuEntity.class,true);
		creatNefTable(db, XunJianDianEntity.class,false);
		creatNefTable(db, XunJianShijianClassifyEntity.class,false);
		creatNefTable(db, XunJianXiangEntity.class,false);
		creatNefTable(db, XunJianShiJianEntity.class,false);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(newVersion == 2){
			upGradeToV2(db);
		}
	}

	private void upGradeToV2(SQLiteDatabase db) {
		//TODO 加入新字段和新表
	}

	private <T> void creatNefTable(SQLiteDatabase db, Class<T> clazz,boolean hasUnique) {
		StringBuilder buffer = new StringBuilder();
		Map<String,String> fieldNames = getNfcFiledNameAndType(clazz);

		buffer.append("CREATE TABLE IF NOT EXISTS ")
				.append(clazz.getSimpleName())
				.append("(_id INTEGER PRIMARY KEY AUTOINCREMENT,");
		Log.d("geek","creatTable="+buffer.toString());
		int pos = 0;
		int size = fieldNames.size();
		for(Map.Entry<String,String> entry : fieldNames.entrySet()){
			String name = entry.getKey();
			String type = entry.getValue();
			buffer.append(name).append(" ").append(getType(type));

			if("jiluId".equals(name) && hasUnique){
				buffer.append(" UNIQUE");
			}

			if(pos!=size-1){
				buffer.append(",");
			}
			pos++;
		}
		buffer.append(")");
		db.execSQL(buffer.toString());
	}

	private String getType(String type){
		if("int".equals(type) || "boolean".equals(type)){
			return "INTEGER";
		} else {
			return "TEXT";
		}
	}

	public   <T> Map<String,String> getNfcFiledNameAndType(Class<T> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		//剔除不需要的属性
		Map<String,String> map= new HashMap<>();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if(field.isSynthetic()){
				continue;
			}
			String fieldName = field.getName();
			String type = field.getGenericType().toString();
			Log.d("geek",i+"获取属性名fieldName="+fieldName);
			if("serialVersionUID".equals(fieldName) || "CREATOR".equals(fieldName)
					|| type.startsWith("java.util.List")||type.startsWith("class com.yxld.xzs.entity")){
				continue;
			}
			map.put(fieldName,type);
		}
		Log.d("geek","获取属性名fieldNames="+map.toString());

		return map;
	}
}
