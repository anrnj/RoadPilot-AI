package com.roadpilot.ai.data.local.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TripDao_Impl implements TripDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TripEntity> __insertionAdapterOfTripEntity;

  private final EntityDeletionOrUpdateAdapter<TripEntity> __deletionAdapterOfTripEntity;

  private final EntityDeletionOrUpdateAdapter<TripEntity> __updateAdapterOfTripEntity;

  private final SharedSQLiteStatement __preparedStmtOfEndTrip;

  public TripDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTripEntity = new EntityInsertionAdapter<TripEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `trips` (`id`,`startTime`,`endTime`,`distance`,`averageSpeed`,`maxSpeed`,`startLatitude`,`startLongitude`,`endLatitude`,`endLongitude`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TripEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getEndTime());
        }
        statement.bindDouble(4, entity.getDistance());
        statement.bindDouble(5, entity.getAverageSpeed());
        statement.bindDouble(6, entity.getMaxSpeed());
        if (entity.getStartLatitude() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getStartLatitude());
        }
        if (entity.getStartLongitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getStartLongitude());
        }
        if (entity.getEndLatitude() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getEndLatitude());
        }
        if (entity.getEndLongitude() == null) {
          statement.bindNull(10);
        } else {
          statement.bindDouble(10, entity.getEndLongitude());
        }
      }
    };
    this.__deletionAdapterOfTripEntity = new EntityDeletionOrUpdateAdapter<TripEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `trips` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TripEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTripEntity = new EntityDeletionOrUpdateAdapter<TripEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `trips` SET `id` = ?,`startTime` = ?,`endTime` = ?,`distance` = ?,`averageSpeed` = ?,`maxSpeed` = ?,`startLatitude` = ?,`startLongitude` = ?,`endLatitude` = ?,`endLongitude` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TripEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getEndTime());
        }
        statement.bindDouble(4, entity.getDistance());
        statement.bindDouble(5, entity.getAverageSpeed());
        statement.bindDouble(6, entity.getMaxSpeed());
        if (entity.getStartLatitude() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getStartLatitude());
        }
        if (entity.getStartLongitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getStartLongitude());
        }
        if (entity.getEndLatitude() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getEndLatitude());
        }
        if (entity.getEndLongitude() == null) {
          statement.bindNull(10);
        } else {
          statement.bindDouble(10, entity.getEndLongitude());
        }
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfEndTrip = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE trips SET endTime = ?, endLatitude = ?, endLongitude = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertTrip(final TripEntity trip, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTripEntity.insertAndReturnId(trip);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTrip(final TripEntity trip, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTripEntity.handle(trip);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTrip(final TripEntity trip, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTripEntity.handle(trip);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object endTrip(final long id, final long endTime, final Double lat, final Double lng,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfEndTrip.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, endTime);
        _argIndex = 2;
        if (lat == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindDouble(_argIndex, lat);
        }
        _argIndex = 3;
        if (lng == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindDouble(_argIndex, lng);
        }
        _argIndex = 4;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfEndTrip.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TripEntity>> getAllTrips() {
    final String _sql = "SELECT * FROM trips ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trips"}, new Callable<List<TripEntity>>() {
      @Override
      @NonNull
      public List<TripEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfAverageSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "averageSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfStartLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "startLatitude");
          final int _cursorIndexOfStartLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "startLongitude");
          final int _cursorIndexOfEndLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "endLatitude");
          final int _cursorIndexOfEndLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "endLongitude");
          final List<TripEntity> _result = new ArrayList<TripEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TripEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final float _tmpDistance;
            _tmpDistance = _cursor.getFloat(_cursorIndexOfDistance);
            final float _tmpAverageSpeed;
            _tmpAverageSpeed = _cursor.getFloat(_cursorIndexOfAverageSpeed);
            final float _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getFloat(_cursorIndexOfMaxSpeed);
            final Double _tmpStartLatitude;
            if (_cursor.isNull(_cursorIndexOfStartLatitude)) {
              _tmpStartLatitude = null;
            } else {
              _tmpStartLatitude = _cursor.getDouble(_cursorIndexOfStartLatitude);
            }
            final Double _tmpStartLongitude;
            if (_cursor.isNull(_cursorIndexOfStartLongitude)) {
              _tmpStartLongitude = null;
            } else {
              _tmpStartLongitude = _cursor.getDouble(_cursorIndexOfStartLongitude);
            }
            final Double _tmpEndLatitude;
            if (_cursor.isNull(_cursorIndexOfEndLatitude)) {
              _tmpEndLatitude = null;
            } else {
              _tmpEndLatitude = _cursor.getDouble(_cursorIndexOfEndLatitude);
            }
            final Double _tmpEndLongitude;
            if (_cursor.isNull(_cursorIndexOfEndLongitude)) {
              _tmpEndLongitude = null;
            } else {
              _tmpEndLongitude = _cursor.getDouble(_cursorIndexOfEndLongitude);
            }
            _item = new TripEntity(_tmpId,_tmpStartTime,_tmpEndTime,_tmpDistance,_tmpAverageSpeed,_tmpMaxSpeed,_tmpStartLatitude,_tmpStartLongitude,_tmpEndLatitude,_tmpEndLongitude);
            _result.add(_item);
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

  @Override
  public Flow<TripEntity> getCurrentTrip() {
    final String _sql = "SELECT * FROM trips WHERE endTime IS NULL ORDER BY startTime DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trips"}, new Callable<TripEntity>() {
      @Override
      @Nullable
      public TripEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfAverageSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "averageSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfStartLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "startLatitude");
          final int _cursorIndexOfStartLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "startLongitude");
          final int _cursorIndexOfEndLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "endLatitude");
          final int _cursorIndexOfEndLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "endLongitude");
          final TripEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final float _tmpDistance;
            _tmpDistance = _cursor.getFloat(_cursorIndexOfDistance);
            final float _tmpAverageSpeed;
            _tmpAverageSpeed = _cursor.getFloat(_cursorIndexOfAverageSpeed);
            final float _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getFloat(_cursorIndexOfMaxSpeed);
            final Double _tmpStartLatitude;
            if (_cursor.isNull(_cursorIndexOfStartLatitude)) {
              _tmpStartLatitude = null;
            } else {
              _tmpStartLatitude = _cursor.getDouble(_cursorIndexOfStartLatitude);
            }
            final Double _tmpStartLongitude;
            if (_cursor.isNull(_cursorIndexOfStartLongitude)) {
              _tmpStartLongitude = null;
            } else {
              _tmpStartLongitude = _cursor.getDouble(_cursorIndexOfStartLongitude);
            }
            final Double _tmpEndLatitude;
            if (_cursor.isNull(_cursorIndexOfEndLatitude)) {
              _tmpEndLatitude = null;
            } else {
              _tmpEndLatitude = _cursor.getDouble(_cursorIndexOfEndLatitude);
            }
            final Double _tmpEndLongitude;
            if (_cursor.isNull(_cursorIndexOfEndLongitude)) {
              _tmpEndLongitude = null;
            } else {
              _tmpEndLongitude = _cursor.getDouble(_cursorIndexOfEndLongitude);
            }
            _result = new TripEntity(_tmpId,_tmpStartTime,_tmpEndTime,_tmpDistance,_tmpAverageSpeed,_tmpMaxSpeed,_tmpStartLatitude,_tmpStartLongitude,_tmpEndLatitude,_tmpEndLongitude);
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

  @Override
  public Object getTripById(final long id, final Continuation<? super TripEntity> $completion) {
    final String _sql = "SELECT * FROM trips WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TripEntity>() {
      @Override
      @Nullable
      public TripEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfAverageSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "averageSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfStartLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "startLatitude");
          final int _cursorIndexOfStartLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "startLongitude");
          final int _cursorIndexOfEndLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "endLatitude");
          final int _cursorIndexOfEndLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "endLongitude");
          final TripEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final float _tmpDistance;
            _tmpDistance = _cursor.getFloat(_cursorIndexOfDistance);
            final float _tmpAverageSpeed;
            _tmpAverageSpeed = _cursor.getFloat(_cursorIndexOfAverageSpeed);
            final float _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getFloat(_cursorIndexOfMaxSpeed);
            final Double _tmpStartLatitude;
            if (_cursor.isNull(_cursorIndexOfStartLatitude)) {
              _tmpStartLatitude = null;
            } else {
              _tmpStartLatitude = _cursor.getDouble(_cursorIndexOfStartLatitude);
            }
            final Double _tmpStartLongitude;
            if (_cursor.isNull(_cursorIndexOfStartLongitude)) {
              _tmpStartLongitude = null;
            } else {
              _tmpStartLongitude = _cursor.getDouble(_cursorIndexOfStartLongitude);
            }
            final Double _tmpEndLatitude;
            if (_cursor.isNull(_cursorIndexOfEndLatitude)) {
              _tmpEndLatitude = null;
            } else {
              _tmpEndLatitude = _cursor.getDouble(_cursorIndexOfEndLatitude);
            }
            final Double _tmpEndLongitude;
            if (_cursor.isNull(_cursorIndexOfEndLongitude)) {
              _tmpEndLongitude = null;
            } else {
              _tmpEndLongitude = _cursor.getDouble(_cursorIndexOfEndLongitude);
            }
            _result = new TripEntity(_tmpId,_tmpStartTime,_tmpEndTime,_tmpDistance,_tmpAverageSpeed,_tmpMaxSpeed,_tmpStartLatitude,_tmpStartLongitude,_tmpEndLatitude,_tmpEndLongitude);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
