package com.roadpilot.ai.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RoadPilotDatabase_Impl extends RoadPilotDatabase {
  private volatile RecordingDao _recordingDao;

  private volatile TripDao _tripDao;

  private volatile EmergencyContactDao _emergencyContactDao;

  private volatile FuelLogDao _fuelLogDao;

  private volatile MaintenanceReminderDao _maintenanceReminderDao;

  private volatile VehicleInfoDao _vehicleInfoDao;

  private volatile AiConversationDao _aiConversationDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `recordings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `filePath` TEXT NOT NULL, `duration` INTEGER NOT NULL, `size` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `isProtected` INTEGER NOT NULL, `isAccident` INTEGER NOT NULL, `latitude` REAL, `longitude` REAL, `thumbnailPath` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trips` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `distance` REAL NOT NULL, `averageSpeed` REAL NOT NULL, `maxSpeed` REAL NOT NULL, `startLatitude` REAL, `startLongitude` REAL, `endLatitude` REAL, `endLongitude` REAL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `emergency_contacts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `isPrimary` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `fuel_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `liters` REAL NOT NULL, `cost` REAL NOT NULL, `odometer` REAL NOT NULL, `latitude` REAL, `longitude` REAL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `maintenance_reminders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` TEXT NOT NULL, `dueDate` INTEGER NOT NULL, `dueOdometer` REAL, `isCompleted` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vehicle_info` (`id` INTEGER NOT NULL, `licensePlate` TEXT NOT NULL, `make` TEXT NOT NULL, `model` TEXT NOT NULL, `year` INTEGER NOT NULL, `fuelType` TEXT NOT NULL, `tankCapacity` REAL NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `ai_conversations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `messagesJson` TEXT NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'dca68e77cea1a5a7949a81c42aa6fe50')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `recordings`");
        db.execSQL("DROP TABLE IF EXISTS `trips`");
        db.execSQL("DROP TABLE IF EXISTS `emergency_contacts`");
        db.execSQL("DROP TABLE IF EXISTS `fuel_logs`");
        db.execSQL("DROP TABLE IF EXISTS `maintenance_reminders`");
        db.execSQL("DROP TABLE IF EXISTS `vehicle_info`");
        db.execSQL("DROP TABLE IF EXISTS `ai_conversations`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsRecordings = new HashMap<String, TableInfo.Column>(10);
        _columnsRecordings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("filePath", new TableInfo.Column("filePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("size", new TableInfo.Column("size", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("isProtected", new TableInfo.Column("isProtected", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("isAccident", new TableInfo.Column("isAccident", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("thumbnailPath", new TableInfo.Column("thumbnailPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecordings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRecordings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRecordings = new TableInfo("recordings", _columnsRecordings, _foreignKeysRecordings, _indicesRecordings);
        final TableInfo _existingRecordings = TableInfo.read(db, "recordings");
        if (!_infoRecordings.equals(_existingRecordings)) {
          return new RoomOpenHelper.ValidationResult(false, "recordings(com.roadpilot.ai.data.local.database.RecordingEntity).\n"
                  + " Expected:\n" + _infoRecordings + "\n"
                  + " Found:\n" + _existingRecordings);
        }
        final HashMap<String, TableInfo.Column> _columnsTrips = new HashMap<String, TableInfo.Column>(10);
        _columnsTrips.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("distance", new TableInfo.Column("distance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("averageSpeed", new TableInfo.Column("averageSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("maxSpeed", new TableInfo.Column("maxSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startLatitude", new TableInfo.Column("startLatitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startLongitude", new TableInfo.Column("startLongitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endLatitude", new TableInfo.Column("endLatitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endLongitude", new TableInfo.Column("endLongitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrips = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrips = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrips = new TableInfo("trips", _columnsTrips, _foreignKeysTrips, _indicesTrips);
        final TableInfo _existingTrips = TableInfo.read(db, "trips");
        if (!_infoTrips.equals(_existingTrips)) {
          return new RoomOpenHelper.ValidationResult(false, "trips(com.roadpilot.ai.data.local.database.TripEntity).\n"
                  + " Expected:\n" + _infoTrips + "\n"
                  + " Found:\n" + _existingTrips);
        }
        final HashMap<String, TableInfo.Column> _columnsEmergencyContacts = new HashMap<String, TableInfo.Column>(4);
        _columnsEmergencyContacts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmergencyContacts.put("isPrimary", new TableInfo.Column("isPrimary", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEmergencyContacts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEmergencyContacts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEmergencyContacts = new TableInfo("emergency_contacts", _columnsEmergencyContacts, _foreignKeysEmergencyContacts, _indicesEmergencyContacts);
        final TableInfo _existingEmergencyContacts = TableInfo.read(db, "emergency_contacts");
        if (!_infoEmergencyContacts.equals(_existingEmergencyContacts)) {
          return new RoomOpenHelper.ValidationResult(false, "emergency_contacts(com.roadpilot.ai.data.local.database.EmergencyContactEntity).\n"
                  + " Expected:\n" + _infoEmergencyContacts + "\n"
                  + " Found:\n" + _existingEmergencyContacts);
        }
        final HashMap<String, TableInfo.Column> _columnsFuelLogs = new HashMap<String, TableInfo.Column>(7);
        _columnsFuelLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFuelLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFuelLogs.put("liters", new TableInfo.Column("liters", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFuelLogs.put("cost", new TableInfo.Column("cost", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFuelLogs.put("odometer", new TableInfo.Column("odometer", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFuelLogs.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFuelLogs.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFuelLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFuelLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFuelLogs = new TableInfo("fuel_logs", _columnsFuelLogs, _foreignKeysFuelLogs, _indicesFuelLogs);
        final TableInfo _existingFuelLogs = TableInfo.read(db, "fuel_logs");
        if (!_infoFuelLogs.equals(_existingFuelLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "fuel_logs(com.roadpilot.ai.data.local.database.FuelLogEntity).\n"
                  + " Expected:\n" + _infoFuelLogs + "\n"
                  + " Found:\n" + _existingFuelLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsMaintenanceReminders = new HashMap<String, TableInfo.Column>(5);
        _columnsMaintenanceReminders.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceReminders.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceReminders.put("dueDate", new TableInfo.Column("dueDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceReminders.put("dueOdometer", new TableInfo.Column("dueOdometer", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceReminders.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMaintenanceReminders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMaintenanceReminders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMaintenanceReminders = new TableInfo("maintenance_reminders", _columnsMaintenanceReminders, _foreignKeysMaintenanceReminders, _indicesMaintenanceReminders);
        final TableInfo _existingMaintenanceReminders = TableInfo.read(db, "maintenance_reminders");
        if (!_infoMaintenanceReminders.equals(_existingMaintenanceReminders)) {
          return new RoomOpenHelper.ValidationResult(false, "maintenance_reminders(com.roadpilot.ai.data.local.database.MaintenanceReminderEntity).\n"
                  + " Expected:\n" + _infoMaintenanceReminders + "\n"
                  + " Found:\n" + _existingMaintenanceReminders);
        }
        final HashMap<String, TableInfo.Column> _columnsVehicleInfo = new HashMap<String, TableInfo.Column>(7);
        _columnsVehicleInfo.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicleInfo.put("licensePlate", new TableInfo.Column("licensePlate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicleInfo.put("make", new TableInfo.Column("make", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicleInfo.put("model", new TableInfo.Column("model", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicleInfo.put("year", new TableInfo.Column("year", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicleInfo.put("fuelType", new TableInfo.Column("fuelType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVehicleInfo.put("tankCapacity", new TableInfo.Column("tankCapacity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVehicleInfo = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVehicleInfo = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVehicleInfo = new TableInfo("vehicle_info", _columnsVehicleInfo, _foreignKeysVehicleInfo, _indicesVehicleInfo);
        final TableInfo _existingVehicleInfo = TableInfo.read(db, "vehicle_info");
        if (!_infoVehicleInfo.equals(_existingVehicleInfo)) {
          return new RoomOpenHelper.ValidationResult(false, "vehicle_info(com.roadpilot.ai.data.local.database.VehicleInfoEntity).\n"
                  + " Expected:\n" + _infoVehicleInfo + "\n"
                  + " Found:\n" + _existingVehicleInfo);
        }
        final HashMap<String, TableInfo.Column> _columnsAiConversations = new HashMap<String, TableInfo.Column>(3);
        _columnsAiConversations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAiConversations.put("messagesJson", new TableInfo.Column("messagesJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAiConversations.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAiConversations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAiConversations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAiConversations = new TableInfo("ai_conversations", _columnsAiConversations, _foreignKeysAiConversations, _indicesAiConversations);
        final TableInfo _existingAiConversations = TableInfo.read(db, "ai_conversations");
        if (!_infoAiConversations.equals(_existingAiConversations)) {
          return new RoomOpenHelper.ValidationResult(false, "ai_conversations(com.roadpilot.ai.data.local.database.AiConversationEntity).\n"
                  + " Expected:\n" + _infoAiConversations + "\n"
                  + " Found:\n" + _existingAiConversations);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "dca68e77cea1a5a7949a81c42aa6fe50", "b0e9b156bb86a77ef860f07345a6c119");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "recordings","trips","emergency_contacts","fuel_logs","maintenance_reminders","vehicle_info","ai_conversations");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `recordings`");
      _db.execSQL("DELETE FROM `trips`");
      _db.execSQL("DELETE FROM `emergency_contacts`");
      _db.execSQL("DELETE FROM `fuel_logs`");
      _db.execSQL("DELETE FROM `maintenance_reminders`");
      _db.execSQL("DELETE FROM `vehicle_info`");
      _db.execSQL("DELETE FROM `ai_conversations`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(RecordingDao.class, RecordingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TripDao.class, TripDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EmergencyContactDao.class, EmergencyContactDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FuelLogDao.class, FuelLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MaintenanceReminderDao.class, MaintenanceReminderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VehicleInfoDao.class, VehicleInfoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AiConversationDao.class, AiConversationDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public RecordingDao recordingDao() {
    if (_recordingDao != null) {
      return _recordingDao;
    } else {
      synchronized(this) {
        if(_recordingDao == null) {
          _recordingDao = new RecordingDao_Impl(this);
        }
        return _recordingDao;
      }
    }
  }

  @Override
  public TripDao tripDao() {
    if (_tripDao != null) {
      return _tripDao;
    } else {
      synchronized(this) {
        if(_tripDao == null) {
          _tripDao = new TripDao_Impl(this);
        }
        return _tripDao;
      }
    }
  }

  @Override
  public EmergencyContactDao emergencyContactDao() {
    if (_emergencyContactDao != null) {
      return _emergencyContactDao;
    } else {
      synchronized(this) {
        if(_emergencyContactDao == null) {
          _emergencyContactDao = new EmergencyContactDao_Impl(this);
        }
        return _emergencyContactDao;
      }
    }
  }

  @Override
  public FuelLogDao fuelLogDao() {
    if (_fuelLogDao != null) {
      return _fuelLogDao;
    } else {
      synchronized(this) {
        if(_fuelLogDao == null) {
          _fuelLogDao = new FuelLogDao_Impl(this);
        }
        return _fuelLogDao;
      }
    }
  }

  @Override
  public MaintenanceReminderDao maintenanceReminderDao() {
    if (_maintenanceReminderDao != null) {
      return _maintenanceReminderDao;
    } else {
      synchronized(this) {
        if(_maintenanceReminderDao == null) {
          _maintenanceReminderDao = new MaintenanceReminderDao_Impl(this);
        }
        return _maintenanceReminderDao;
      }
    }
  }

  @Override
  public VehicleInfoDao vehicleInfoDao() {
    if (_vehicleInfoDao != null) {
      return _vehicleInfoDao;
    } else {
      synchronized(this) {
        if(_vehicleInfoDao == null) {
          _vehicleInfoDao = new VehicleInfoDao_Impl(this);
        }
        return _vehicleInfoDao;
      }
    }
  }

  @Override
  public AiConversationDao aiConversationDao() {
    if (_aiConversationDao != null) {
      return _aiConversationDao;
    } else {
      synchronized(this) {
        if(_aiConversationDao == null) {
          _aiConversationDao = new AiConversationDao_Impl(this);
        }
        return _aiConversationDao;
      }
    }
  }
}
