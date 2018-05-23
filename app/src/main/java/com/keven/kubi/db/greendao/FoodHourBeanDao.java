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

import com.keven.kubi.bean.FoodHourBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FOOD_HOUR_BEAN".
*/
public class FoodHourBeanDao extends AbstractDao<FoodHourBean, Long> {

    public static final String TABLENAME = "FOOD_HOUR_BEAN";

    /**
     * Properties of entity FoodHourBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Fid = new Property(1, long.class, "fid", false, "FID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property HourScaleCount = new Property(3, int.class, "hourScaleCount", false, "HOUR_SCALE_COUNT");
        public final static Property ResidueCount = new Property(4, int.class, "residueCount", false, "RESIDUE_COUNT");
        public final static Property RealityWriteTimeStamp = new Property(5, long.class, "realityWriteTimeStamp", false, "REALITY_WRITE_TIME_STAMP");
        public final static Property PredictWriteTimeStamp = new Property(6, long.class, "predictWriteTimeStamp", false, "PREDICT_WRITE_TIME_STAMP");
        public final static Property WeekStartTimeStamp = new Property(7, long.class, "weekStartTimeStamp", false, "WEEK_START_TIME_STAMP");
        public final static Property StartTimeStamp = new Property(8, long.class, "startTimeStamp", false, "START_TIME_STAMP");
        public final static Property EndTimeStamp = new Property(9, long.class, "endTimeStamp", false, "END_TIME_STAMP");
    };

    private DaoSession daoSession;


    public FoodHourBeanDao(DaoConfig config) {
        super(config);
    }
    
    public FoodHourBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FOOD_HOUR_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"FID\" INTEGER NOT NULL ," + // 1: fid
                "\"NAME\" TEXT NOT NULL ," + // 2: name
                "\"HOUR_SCALE_COUNT\" INTEGER NOT NULL ," + // 3: hourScaleCount
                "\"RESIDUE_COUNT\" INTEGER NOT NULL ," + // 4: residueCount
                "\"REALITY_WRITE_TIME_STAMP\" INTEGER NOT NULL ," + // 5: realityWriteTimeStamp
                "\"PREDICT_WRITE_TIME_STAMP\" INTEGER NOT NULL ," + // 6: predictWriteTimeStamp
                "\"WEEK_START_TIME_STAMP\" INTEGER NOT NULL ," + // 7: weekStartTimeStamp
                "\"START_TIME_STAMP\" INTEGER NOT NULL ," + // 8: startTimeStamp
                "\"END_TIME_STAMP\" INTEGER NOT NULL );"); // 9: endTimeStamp
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_FOOD_HOUR_BEAN_FID ON FOOD_HOUR_BEAN" +
                " (\"FID\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_FOOD_HOUR_BEAN_PREDICT_WRITE_TIME_STAMP ON FOOD_HOUR_BEAN" +
                " (\"PREDICT_WRITE_TIME_STAMP\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_FOOD_HOUR_BEAN_START_TIME_STAMP ON FOOD_HOUR_BEAN" +
                " (\"START_TIME_STAMP\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_FOOD_HOUR_BEAN_END_TIME_STAMP ON FOOD_HOUR_BEAN" +
                " (\"END_TIME_STAMP\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FOOD_HOUR_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FoodHourBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getFid());
        stmt.bindString(3, entity.getName());
        stmt.bindLong(4, entity.getHourScaleCount());
        stmt.bindLong(5, entity.getResidueCount());
        stmt.bindLong(6, entity.getRealityWriteTimeStamp());
        stmt.bindLong(7, entity.getPredictWriteTimeStamp());
        stmt.bindLong(8, entity.getWeekStartTimeStamp());
        stmt.bindLong(9, entity.getStartTimeStamp());
        stmt.bindLong(10, entity.getEndTimeStamp());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FoodHourBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getFid());
        stmt.bindString(3, entity.getName());
        stmt.bindLong(4, entity.getHourScaleCount());
        stmt.bindLong(5, entity.getResidueCount());
        stmt.bindLong(6, entity.getRealityWriteTimeStamp());
        stmt.bindLong(7, entity.getPredictWriteTimeStamp());
        stmt.bindLong(8, entity.getWeekStartTimeStamp());
        stmt.bindLong(9, entity.getStartTimeStamp());
        stmt.bindLong(10, entity.getEndTimeStamp());
    }

    @Override
    protected final void attachEntity(FoodHourBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public FoodHourBean readEntity(Cursor cursor, int offset) {
        FoodHourBean entity = new FoodHourBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // fid
            cursor.getString(offset + 2), // name
            cursor.getInt(offset + 3), // hourScaleCount
            cursor.getInt(offset + 4), // residueCount
            cursor.getLong(offset + 5), // realityWriteTimeStamp
            cursor.getLong(offset + 6), // predictWriteTimeStamp
            cursor.getLong(offset + 7), // weekStartTimeStamp
            cursor.getLong(offset + 8), // startTimeStamp
            cursor.getLong(offset + 9) // endTimeStamp
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FoodHourBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFid(cursor.getLong(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setHourScaleCount(cursor.getInt(offset + 3));
        entity.setResidueCount(cursor.getInt(offset + 4));
        entity.setRealityWriteTimeStamp(cursor.getLong(offset + 5));
        entity.setPredictWriteTimeStamp(cursor.getLong(offset + 6));
        entity.setWeekStartTimeStamp(cursor.getLong(offset + 7));
        entity.setStartTimeStamp(cursor.getLong(offset + 8));
        entity.setEndTimeStamp(cursor.getLong(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FoodHourBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FoodHourBean entity) {
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
            builder.append(" FROM FOOD_HOUR_BEAN T");
            builder.append(" LEFT JOIN FOOD_PRODUCTION_BEAN T0 ON T.\"FID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected FoodHourBean loadCurrentDeep(Cursor cursor, boolean lock) {
        FoodHourBean entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        FoodProductionBean foodProductionBean = loadCurrentOther(daoSession.getFoodProductionBeanDao(), cursor, offset);
         if(foodProductionBean != null) {
            entity.setFoodProductionBean(foodProductionBean);
        }

        return entity;    
    }

    public FoodHourBean loadDeep(Long key) {
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
    public List<FoodHourBean> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<FoodHourBean> list = new ArrayList<FoodHourBean>(count);
        
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
    
    protected List<FoodHourBean> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<FoodHourBean> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
