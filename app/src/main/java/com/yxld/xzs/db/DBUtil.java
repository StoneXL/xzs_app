package com.yxld.xzs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.entity.XunJianShiJianEntity;
import com.yxld.xzs.entity.XunJianShijianClassifyEntity;
import com.yxld.xzs.entity.XunJianXiangEntity;
import com.yxld.xzs.utils.GsonHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作,操作完成后记得调用close()函数
 *
 * @author wwx
 */
public class DBUtil {
    private MyDbHelper dbHelper;
    private SQLiteDatabase db;
    private static DBUtil mInstance;

    private DBUtil(Context context) {
        dbHelper = new MyDbHelper(context);
        // 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
        // mFactory);
        // 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = dbHelper.getWritableDatabase();
    }

    public static DBUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBUtil.class) {
                if (mInstance == null) {
                    mInstance = new DBUtil(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 关闭数据库，</br> 在每一个context中使用数据库操作完成后必须调用此函数
     */
//    public void close() {
//        if (db != null) {
//            if (db.isOpen()) {
//                db.close();
//                db = null;
//            }
//        }
//        dbHelper.close();
//    }

    /**
     * 清除表中的数据
     *
     * @param clazz
     */
    public <T> void clearData(Class<T> clazz) {
        if (db == null) {
            return;
        }
        String sql = "delete from " + clazz.getSimpleName();
        db.execSQL(sql);
    }

    //------------------------------------------------------------------------------------------------------------------------

    public boolean insertOneJiLu(XunJianJiLuEntity jiluEntity) {
        if (db == null) {
            return false;
        }
        try {
            db.beginTransaction();
            long result = insert(jiluEntity);
            if (result <= 0) {
                return false;
            }
            for (XunJianDianEntity checkPoint : jiluEntity.xunJianDianDatas) {
                checkPoint.jiluId = jiluEntity.jiluId;
                long dianResult = insert(checkPoint);
                for (XunJianXiangEntity xunJianXiangEntity : checkPoint.xunJianXiangDatas) {
                    xunJianXiangEntity.jiluId = jiluEntity.jiluId;
                    xunJianXiangEntity.dianId = checkPoint.dianId;
                    long xiangResult = insert(xunJianXiangEntity);
                    Log.d("tag", "" + xiangResult);
                }

                for (XunJianShijianClassifyEntity classifyEntity : checkPoint.xunJianShijianClassifies) {
                    classifyEntity.jiluId = jiluEntity.jiluId;
                    classifyEntity.dianId = checkPoint.dianId;
                    long classifyResult = insert(classifyEntity);

                    for (XunJianShiJianEntity xunJianShiJianEntity : classifyEntity.list) {
                        xunJianShiJianEntity.jiluId = classifyEntity.jiluId;
                        xunJianShiJianEntity.dianId = classifyEntity.dianId;
                        xunJianShiJianEntity.clazzId = classifyEntity.clazzId;
                        long shijianResult = insert(xunJianShiJianEntity);
                        Log.d("tag", "ShijianResult" + shijianResult);
                    }
                }
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return false;
    }

    public <T> long insert(T t) throws Exception {
        return db.insert(t.getClass().getSimpleName(), null,
                getNfcFiledValues(t));
    }

    public boolean updateOneXunJianDian(XunJianDianEntity dianEntity) {
        if (db == null) {
            return false;
        }
        try {
            db.beginTransaction();
            long result = update(dianEntity, "jiluId = ? and dianId = ?", new String[]{dianEntity.jiluId + "", dianEntity.dianId + ""});
            for (XunJianXiangEntity xunJianXiangEntity : dianEntity.xunJianXiangDatas) {
                update(xunJianXiangEntity, "jiluId = ? and dianId = ? and xunjianxiangId = ?", new String[]{dianEntity.jiluId + "", dianEntity.dianId + "", xunJianXiangEntity.xunjianxiangId + ""});
            }
            for (XunJianShijianClassifyEntity classifyEntity : dianEntity.xunJianShijianClassifies) {
                for (XunJianShiJianEntity shiJianEntity : classifyEntity.list) {
                    String jiluid = shiJianEntity.jiluId + "";
                    String dianId = shiJianEntity.dianId + "";
                    String clazzId = shiJianEntity.clazzId + "";
                    String shijianId = shiJianEntity.shijianId + "";
                    long update = update(shiJianEntity, "jiluId = ? and dianId = ? and clazzId = ? and shijianId = ?", new String[]{jiluid, dianId, clazzId, shijianId});
                    Log.i("tag","update : "+update);
                }
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return false;
    }

    public boolean updateJustOneJiLu(XunJianJiLuEntity entity) {
        if (db == null) {
            return false;
        }
        try {
            db.beginTransaction();
            long result = update(entity, "jiluId = ?", new String[]{entity.jiluId + ""});
            db.setTransactionSuccessful();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return false;
    }

    public boolean updateOneJiLu(XunJianJiLuEntity entity) {
        if (db == null) {
            return false;
        }
        try {
            db.beginTransaction();
            long jiluResult = update(entity, "jiluId = ?", new String[]{entity.jiluId + ""});
            for (XunJianDianEntity dianEntity : entity.xunJianDianDatas) {
                long dianResult = update(dianEntity, "jiluId = ? and dianId = ?", new String[]{entity.jiluId + "", dianEntity.dianId + ""});
                for (XunJianXiangEntity xunJianXiangEntity : dianEntity.xunJianXiangDatas) {
                    long xiangResult = update(xunJianXiangEntity, "jiluId = ? and dianId = ? and xunjianxiangId = ?",
                            new String[]{entity.jiluId + "", dianEntity.dianId + "", xunJianXiangEntity.xunjianxiangId + ""});
                }
                for (XunJianShijianClassifyEntity classifyEntity : dianEntity.xunJianShijianClassifies) {
                    for (XunJianShiJianEntity shiJianEntity : classifyEntity.list) {
                        long shijianResult = update(shiJianEntity, "jiluId = ? and dianId = ? and clazzId = ? and shijianId = ?",
                                new String[]{entity.jiluId + "", dianEntity.dianId + "", classifyEntity.clazzId + "", shiJianEntity.shijianId + ""});
                    }
                }

            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return false;
    }

    private <T> long update(T t, String where, String[] values) throws Exception {
        return db.update(t.getClass().getSimpleName(), getNfcFiledValues(t), where, values);
    }


    public List<XunJianJiLuEntity> getAllCompleteXunJianJiLuByUserId(String userId) {
        return queryList(XunJianJiLuEntity.class, "userId = ? and jiluWancheng = ?", new String[]{userId, "2"});
    }

    public XunJianJiLuEntity getJiLuById(String userId, String jiluId) {
        XunJianJiLuEntity entity = query(XunJianJiLuEntity.class, "userId = ? and jiluId = ?",
                new String[]{userId, jiluId});
        List<XunJianDianEntity> dianEntities = queryList(XunJianDianEntity.class, "jiluId = ?", new String[]{jiluId});
        for (XunJianDianEntity dianEntity : dianEntities) {
            dianEntity.xunJianXiangDatas =
                    queryList(XunJianXiangEntity.class, "jiluId = ? and dianId = ?", new String[]{jiluId, dianEntity.dianId + ""});

            dianEntity.xunJianShijianClassifies = queryList(XunJianShijianClassifyEntity.class, "jiluId = ? and dianId = ?", new String[]{jiluId, dianEntity.dianId + ""});
            for (XunJianShijianClassifyEntity classifyEntity : dianEntity.xunJianShijianClassifies) {
                classifyEntity.list = queryList(XunJianShiJianEntity.class, "jiluId = ? and dianId = ? and clazzId = ?", new String[]{jiluId, dianEntity.dianId + "", classifyEntity.clazzId + ""});
            }
        }
        entity.xunJianDianDatas = dianEntities;
        return entity;
    }

    public XunJianDianEntity getXunJianDianById(String jiluId, String dianId) {
        XunJianDianEntity dian = query(XunJianDianEntity.class, "jiluId = ? and dianId = ?", new String[]{jiluId, dianId});
        List<XunJianXiangEntity> xiangEntities = queryList(XunJianXiangEntity.class, "jiluId = ? and dianId = ?", new String[]{jiluId, dianId});
        List<XunJianShijianClassifyEntity> classifyEntities = queryList(XunJianShijianClassifyEntity.class, "jiluId = ? and dianId = ?", new String[]{jiluId, dianId});
        for (XunJianShijianClassifyEntity classifyEntity : classifyEntities) {
            classifyEntity.list = queryList(XunJianShiJianEntity.class, "jiluId = ? and dianId = ? and clazzId = ?", new String[]{jiluId, dianId,classifyEntity.clazzId+""});
        }
        dian.xunJianShijianClassifies = classifyEntities;
        dian.xunJianXiangDatas = xiangEntities;
        return dian;
    }

    public <T> T query(Class<T> clazz, String selection,
                       String[] selectionArg) {
        if (db == null) {
            return null;
        }
        Cursor c = null;
        try {
            c = db.query(clazz.getSimpleName(), null, selection,
                    selectionArg, null, null, null);
            JsonObject jsonObject = new JsonObject();
            Map<String, String> filedNames = dbHelper.getNfcFiledNameAndType(clazz);
            if (c.moveToNext()) {
                for (Map.Entry<String, String> entry : filedNames.entrySet()) {
                    String fileName = entry.getKey();
                    String value = c.getString(c.getColumnIndex(fileName));
                    jsonObject.addProperty(fileName, value);
                }
                return GsonHelper.fromJson(jsonObject.toString(), clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return null;
    }


    public <T> List<T> queryList(Class<T> clazz, String selection,
                                 String[] selectionArg) {
        if (db == null) {
            return null;
        }
        List<T> objects = new ArrayList<T>();
        Cursor c = db.query(clazz.getSimpleName(), null, selection,
                selectionArg, null, null, null);

        JsonObject jsonObject = new JsonObject();
        Map<String, String> filedNames = dbHelper.getNfcFiledNameAndType(clazz);
        while (c.moveToNext()) {
            for (Map.Entry<String, String> entry : filedNames.entrySet()) {
                String fileName = entry.getKey();
                String value = c.getString(c.getColumnIndex(fileName));
                jsonObject.addProperty(fileName, value);
            }
            T result = GsonHelper.fromJson(jsonObject.toString(), clazz);
            objects.add(result);
        }
        c.close();
        return objects;
    }

    public void deleteJiluById(String jiluId) {
        String sql = "delete from " + XunJianJiLuEntity.class.getSimpleName() + " where jiluId = " + jiluId;
        db.execSQL(sql);

        sql = "delete from " + XunJianDianEntity.class.getSimpleName() + " where jiluId = " + jiluId;
        db.execSQL(sql);

        sql = "delete from " + XunJianXiangEntity.class.getSimpleName() + " where jiluId = " + jiluId;
        db.execSQL(sql);

        sql = "delete from " + XunJianShijianClassifyEntity.class.getSimpleName() + " where jiluId = " + jiluId;
        db.execSQL(sql);

        sql = "delete from " + XunJianShiJianEntity.class.getSimpleName() + " where jiluId = " + jiluId;
        db.execSQL(sql);
    }


    private ContentValues getNfcFiledValues(Object o) throws Exception {
        Field[] fields = o.getClass().getDeclaredFields();
        ContentValues values = new ContentValues();
        for (int i = 0; i < fields.length; i++) {
            //剔除不需要的属性
            String type = fields[i].getGenericType().toString();
            if ("serialVersionUID".equals(fields[i].getName()) || "CREATOR".equals(fields[i].getName())
                    || type.startsWith("java.util.List") || type.startsWith("class com.yxld.xzs.entity")) {
                continue;
            }
            Field field = fields[i];
            if (field.isSynthetic()) {
                continue;
            }
            field.setAccessible(true);
            Object value = handlerValue(field.get(o), field.getGenericType().toString());
            values.put(field.getName(), String.valueOf(value));
        }
        return values;
    }

    private Object handlerValue(Object o, String type) {
        if ("boolean".equals(type)) {
            return (boolean) o ? 1 : 0;
        }
        if ("int".equals(type)) {
            return o;
        }
        if (o == null) {
            return "";
        }
        return String.valueOf(o);
    }


}
