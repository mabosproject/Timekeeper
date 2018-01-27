package mabo_com.timekeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Masaki Matsukawa on 2018/01/27.
 */

public class Alarm_DB_adapter {

    private final static String DB_NAME = "alarm_list.db";  //DB名
    private final static String DB_TABLE = "alarm_list";    //DBのテーブル
    private final static int DB_VERSION = 1;    //DBバージョン

    /**
     * DBのカラム名
     */
    public final static String ALARM_DB_ID = "_id"; //id
    public final static String ALARM_DB_HOUR = "hour"; //時間
    public final static String ALARM_DB_MINUTE = "minute";  //分
    public final static String ALARM_DB_REPEAT = "repeat";  //繰り返し
    public final static String ALARM_DB_COMMENT = "comment";  //コメント
    public final static String ALARM_DB_URI = "uri";  //サウンドuri
    public final static String ALARM_DB_VOLUME = "volume";  //音量
    public final static String ALARM_DB_SNOOZE = "snooze";  //スヌーズ
    public final static String ALARM_DB_VIBRATION = "vibration";  //バイブ

    private SQLiteDatabase db = null;
    private DBHelper dbHelper = null;
    protected Context context;

    public Alarm_DB_adapter(Context context){

        this.context = context;
        dbHelper = new DBHelper(this.context);
    }

    /**
     * DBの読み書き
     *
     * @return this 自身のオブジェクト
     */
    public  Alarm_DB_adapter openDB(){

        db = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * DBの読み込み
     *
     * @return this 自身のオブジェクト
     */
    public Alarm_DB_adapter readDB(){

        db = dbHelper.getReadableDatabase();
        return this;
    }

    /**
     * DBを閉じる
     */
    public  void closeDB(){

        db.close();
        db = null;
    }

    /**
     * DBのレコード登録
     *
     * @param hour 時間
     * @param minute 分
     * @param repeat 繰り返し
     * @param comment コメント
     * @param uri サウンドuri
     * @param volume 音量
     * @param snooze スヌーズ
     * @param vibration バイブ
     */
    public void saveDB(int hour, int minute, int repeat, String comment, String uri, int volume, int snooze, int vibration){

        db.beginTransaction();  //トランザクション開始

        try{
            ContentValues values = new ContentValues(); //データ設定用
            values.put(ALARM_DB_HOUR,hour);
            values.put(ALARM_DB_MINUTE,minute);
            values.put(ALARM_DB_REPEAT,repeat);
            values.put(ALARM_DB_COMMENT,comment);
            values.put(ALARM_DB_URI,uri);
            values.put(ALARM_DB_VOLUME,volume);
            values.put(ALARM_DB_SNOOZE,snooze);
            values.put(ALARM_DB_VIBRATION,vibration);

            //insertメソッド　データ登録
            db.insert(DB_TABLE,null,values);    //レコードへ登録

            db.setTransactionSuccessful();  //トランザクションへコミット
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();    //トランザクション終了
        }
    }

    /**
     * DBのレコードの単一更新
     * @param position String
     * @param hour 時間
     * @param minute 分
     * @param repeat 繰り返し
     * @param comment コメント
     * @param uri サウンドuri
     * @param volume 音量
     * @param snooze スヌーズ
     * @param vibration バイブ
     */
    public void updateDB(String position,int hour, int minute, int repeat, String comment, String uri, int volume, int snooze, int vibration){

        db.beginTransaction();                      // トランザクション開始
        try{
            ContentValues values = new ContentValues(); //データ設定用
            values.put(ALARM_DB_HOUR,hour);
            values.put(ALARM_DB_MINUTE,minute);
            values.put(ALARM_DB_REPEAT,repeat);
            values.put(ALARM_DB_COMMENT,comment);
            values.put(ALARM_DB_URI,uri);
            values.put(ALARM_DB_VOLUME,volume);
            values.put(ALARM_DB_SNOOZE,snooze);
            values.put(ALARM_DB_VIBRATION,vibration);

            db.update(DB_TABLE,values,ALARM_DB_ID + "=?",new String[]{position});    //レコード更新

            db.setTransactionSuccessful();  //トランザクションへコミット
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();    //トランザクション終了
        }
    }

    /**
     * DBのデータ取得
     *
     * @param columns String[] 取得するカラム名 nullの場合は全カラムを取得
     * @return DBデータ
     */
    public Cursor getDB(String[] columns){

        // queryメソッド DBのデータを取得
        // 第1引数：DBのテーブル名
        // 第2引数：取得するカラム名
        // 第3引数：選択条件(WHERE句)
        // 第4引数：第3引数のWHERE句において?を使用した場合に使用
        // 第5引数：集計条件(GROUP BY句)
        // 第6引数：選択条件(HAVING句)
        // 第7引数：ソート条件(ODERBY句)
        return db.query(DB_TABLE,columns,null,null,null,null,null);
    }

    /**
     * DBの検索したデータを取得
     *
     * @param columns String[] 取得するカラム名 nullの場合は全カラムを取得
     * @param column  String 選択条件に使うカラム名
     * @param name    String[]
     * @return DBの検索したデータ
     */
    public Cursor searchDB(String[] columns, String column, String[] name) {

        return db.query(DB_TABLE, columns, column + " like ?", name, null, null, null);
    }

    /**
     * DBのレコードを全削除
     */
    public void allDelete() {

        db.beginTransaction();                      // トランザクション開始
        try {
            // deleteメソッド DBのレコードを削除
            // 第1引数：テーブル名
            // 第2引数：削除する条件式 nullの場合は全レコードを削除
            // 第3引数：第2引数で?を使用した場合に使用
            db.delete(DB_TABLE, null, null);        // DBのレコードを全削除
            db.setTransactionSuccessful();          // トランザクションへコミット

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                    // トランザクションの終了
        }
    }

    /**
     * DBのレコードの単一削除
     * @param position String
     */
    public void selectDelete(String position){

        db.beginTransaction();                      // トランザクション開始
        try {
            db.delete(DB_TABLE, ALARM_DB_ID + "=?", new String[]{position});
            db.setTransactionSuccessful();          // トランザクションへコミット
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();                    // トランザクションの終了
        }

    }

    /**
     * データベースの生成やアップグレードを管理するSQLiteOpenHelperを継承したクラス
     */
    public static class DBHelper extends SQLiteOpenHelper {

        //コンストラクタ
        public DBHelper(Context context) {
            //第一引数:コンテキスト
            //第二引数:DB名
            //第三引数:factory
            //第四引数:DBバージョン
            super(context, DB_NAME, null, DB_VERSION);
        }

        /**
         * DB生成時に呼ばれるonCreate()
         *
         * @param db SQLiteDatabase
         */
        @Override
        public void onCreate(SQLiteDatabase db) {

            //SQL定義文(table作成)
            String createTbl = "CREATE TABLE " + DB_TABLE + " ("
                    + ALARM_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ALARM_DB_HOUR + " INTEGER NOT NULL,"
                    + ALARM_DB_MINUTE + " INTEGER NOT NULL,"
                    + ALARM_DB_REPEAT + " INTEGER NOT NULL,"
                    + ALARM_DB_COMMENT + " TEXT NOT NULL,"
                    + ALARM_DB_URI + " TEXT NOT NULL,"
                    + ALARM_DB_VOLUME + " INTEGER NOT NULL,"
                    + ALARM_DB_SNOOZE + " INTEGER NOT NULL,"
                    + ALARM_DB_VIBRATION + " INTEGER NOT NULL"
                    + ");";

            db.execSQL(createTbl);
        }

        /**
         * DBアップグレード(バージョンアップ)時に呼ばれる
         *
         * @param db
         * @param oldVersion int
         * @param newVersion int
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            //DBからテーブル削除
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            //テーブル作成
            onCreate(db);
        }
    }
}
