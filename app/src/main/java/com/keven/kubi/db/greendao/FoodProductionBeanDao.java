package com.keven.kubi.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.keven.kubi.bean.FoodProductionBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FOOD_PRODUCTION_BEAN".
*/
public class FoodProductionBeanDao extends AbstractDao<FoodProductionBean, Long> {

    public static final String TABLENAME = "FOOD_PRODUCTION_BEAN";

    /**
     * Properties of entity FoodProductionBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Fid = new Property(0, Long.class, "fid", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Type = new Property(2, String.class, "type", false, "TYPE");
        public final static Property Price = new Property(3, double.class, "price", false, "PRICE");
    };


    public FoodProductionBeanDao(DaoConfig config) {
        super(config);
    }
    
    public FoodProductionBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FOOD_PRODUCTION_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: fid
                "\"NAME\" TEXT NOT NULL UNIQUE ," + // 1: name
                "\"TYPE\" TEXT NOT NULL ," + // 2: type
                "\"PRICE\" REAL NOT NULL );"); // 3: price
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FOOD_PRODUCTION_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FoodProductionBean entity) {
        stmt.clearBindings();
 
        Long fid = entity.getFid();
        if (fid != null) {
            stmt.bindLong(1, fid);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindString(3, entity.getType());
        stmt.bindDouble(4, entity.getPrice());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FoodProductionBean entity) {
        stmt.clearBindings();
 
        Long fid = entity.getFid();
        if (fid != null) {
            stmt.bindLong(1, fid);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindString(3, entity.getType());
        stmt.bindDouble(4, entity.getPrice());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public FoodProductionBean readEntity(Cursor cursor, int offset) {
        FoodProductionBean entity = new FoodProductionBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // fid
            cursor.getString(offset + 1), // name
            cursor.getString(offset + 2), // type
            cursor.getDouble(offset + 3) // price
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FoodProductionBean entity, int offset) {
        entity.setFid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setType(cursor.getString(offset + 2));
        entity.setPrice(cursor.getDouble(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FoodProductionBean entity, long rowId) {
        entity.setFid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FoodProductionBean entity) {
        if(entity != null) {
            return entity.getFid();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
