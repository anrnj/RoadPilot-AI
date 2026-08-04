package com.roadpilot.ai.data.local.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VehicleInfoDao_Impl implements VehicleInfoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VehicleInfoEntity> __insertionAdapterOfVehicleInfoEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearVehicleInfo;

  public VehicleInfoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVehicleInfoEntity = new EntityInsertionAdapter<VehicleInfoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vehicle_info` (`id`,`licensePlate`,`make`,`model`,`year`,`fuelType`,`tankCapacity`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VehicleInfoEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getLicensePlate());
        statement.bindString(3, entity.getMake());
        statement.bindString(4, entity.getModel());
        statement.bindLong(5, entity.getYear());
        statement.bindString(6, entity.getFuelType());
        statement.bindDouble(7, entity.getTankCapacity());
      }
    };
    this.__preparedStmtOfClearVehicleInfo = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vehicle_info";
        return _query;
      }
    };
  }

  @Override
  public Object insertVehicleInfo(final VehicleInfoEntity info,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVehicleInfoEntity.insert(info);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearVehicleInfo(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearVehicleInfo.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearVehicleInfo.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<VehicleInfoEntity> getVehicleInfo() {
    final String _sql = "SELECT * FROM vehicle_info WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vehicle_info"}, new Callable<VehicleInfoEntity>() {
      @Override
      @Nullable
      public VehicleInfoEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLicensePlate = CursorUtil.getColumnIndexOrThrow(_cursor, "licensePlate");
          final int _cursorIndexOfMake = CursorUtil.getColumnIndexOrThrow(_cursor, "make");
          final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfFuelType = CursorUtil.getColumnIndexOrThrow(_cursor, "fuelType");
          final int _cursorIndexOfTankCapacity = CursorUtil.getColumnIndexOrThrow(_cursor, "tankCapacity");
          final VehicleInfoEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpLicensePlate;
            _tmpLicensePlate = _cursor.getString(_cursorIndexOfLicensePlate);
            final String _tmpMake;
            _tmpMake = _cursor.getString(_cursorIndexOfMake);
            final String _tmpModel;
            _tmpModel = _cursor.getString(_cursorIndexOfModel);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final String _tmpFuelType;
            _tmpFuelType = _cursor.getString(_cursorIndexOfFuelType);
            final float _tmpTankCapacity;
            _tmpTankCapacity = _cursor.getFloat(_cursorIndexOfTankCapacity);
            _result = new VehicleInfoEntity(_tmpId,_tmpLicensePlate,_tmpMake,_tmpModel,_tmpYear,_tmpFuelType,_tmpTankCapacity);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
