package com.keven.kubi.db.greendao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.keven.kubi.bean.FoodProductionBean;

import com.keven.kubi.bean.DailyProductBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DAILY_PRODUCT_BEAN".
*/
public class DailyProductBeanDao extends AbstractDao<DailyProductBean, Long> {

    public static final String TABLENAME = "DAILY_PRODUCT_BEAN";

    /**
     * Properties of entity DailyProductBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Fid = new Property(1, long.class, "fid", false, "FID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property DateTime = new Property(3, long.class, "dateTime", false, "DATE_TIME");
        public final static Property ProductCount = new Property(4, int.class, "productCount", false, "PRODUCT_COUNT");
    };

    private DaoSession daoSession;


    public DailyProductBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DailyProductBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DAILY_PRODUCT_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"FID\" INTEGER NOT NULL ," + // 1: fid
                "\"NAME\" TEXT NOT NULL ," + // 2: name
                "\"DATE_TIME\" INTEGER NOT NULL ," + // 3: dateTime
                "\"PRODUCT_COUNT\" INTEGER NOT NULL );"); // 4: productCount
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_DAILY_PRODUCT_BEAN_FID ON DAILY_PRODUCT_BEAN" +
                " (\"FID\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DAILY_PRODUCT_BEAN_DATE_TIME ON DAILY_PRODUCT_BEAN" +
                " (\"DATE_TIME\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DAILY_PRODUCT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DailyProductBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getFid());
        stmt.bindString(3, entity.getName());
        stmt.bindLong(4, entity.getDateTime());
        stmt.bindLong(5, entity.getProductCount());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DailyProductBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getFid());
        stmt.bindString(3, entity.getName());
        stmt.bindLong(4, entity.getDateTime());
        stmt.bindLong(5, entity.getProductCount());
    }

    @Override
    protected final void attachEntity(DailyProductBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DailyProductBean readEntity(Cursor cursor, int offset) {
        DailyProductBean entity = new DailyProductBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // fid
            cursor.getString(offset + 2), // name
            cursor.getLong(offset + 3), // dateTime
            cursor.getInt(offset + 4) // productCount
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DailyProductBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFid(cursor.getLong(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setDateTime(cursor.getLong(offset + 3));
        entity.setProductCount(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DailyProductBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DailyProductBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getFoodProductionBeanDao().getAllColumns());
            builder.append(" FROM DAILY_PRODUCT_BEAN T");
            builder.append(" LEFT JOIN FOOD_PRODUCTION_BEAN T0 ON T.\"FID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected DailyProductBean loadCurrentDeep(Cursor cursor, boolean lock) {
        DailyProductBean entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        FoodProductionBean foodProductionBean = loadCurrentOther(daoSession.getFoodProductionBeanDao(), cursor, offset);
         if(foodProductionBean != null) {
            entity.setFoodProductionBean(foodProductionBean);
        }

        return entity;    
    }

    public DailyProductBean loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<DailyProductBean> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<DailyProductBean> list = new ArrayList<DailyProductBean>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<DailyProductBean> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<DailyProductBean> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
