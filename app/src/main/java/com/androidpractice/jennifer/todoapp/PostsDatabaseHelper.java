package com.androidpractice.jennifer.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

@SuppressWarnings("SpellCheckingInspection")
public class PostsDatabaseHelper extends SQLiteOpenHelper {

    private static PostsDatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "ToDoDatabase";
    private static final int DATABASE_VERSION = 5;

    // Table Names
    private static final String TABLE_TODO = "ToDoTable";

    // Post Table Columns
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_CONTENT = "toDoItemContent";
    private static final String KEY_ITEM_DUEDATE = "toDoItemDueDate";
    private static final String KEY_ITEM_IMPORTANCE = "toDoItemImportance";

    public static synchronized PostsDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PostsDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private PostsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Executed when the database connection is being configured.
     */
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * Executed when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO +
                "(" +
                KEY_ITEM_ID + " INTEGER PRIMARY KEY, " +
                KEY_ITEM_CONTENT + " TEXT, " +
                KEY_ITEM_DUEDATE + " TEXT,  " +
                KEY_ITEM_IMPORTANCE + " TEXT " +
                ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    /**
     * Executed when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            onCreate(db);
        }
    }

    /**
     * Insert an item into the database
     */
    public void addToDoItem(ToDoItems addtodoitem) {

        SQLiteDatabase db = getWritableDatabase(); // Create and/or open the database for writing
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_CONTENT, addtodoitem._toDoItem);
            values.put(KEY_ITEM_DUEDATE, addtodoitem._dueDate);
            values.put(KEY_ITEM_IMPORTANCE, addtodoitem._importance);

            db.insertOrThrow(TABLE_TODO, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }

    }

    /**
     * Edit item in the database
     */
    public long editToDo(ToDoItems oldtodoitem, ToDoItems edittodoitem) {

        SQLiteDatabase db = getWritableDatabase();
        long cursorId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_CONTENT, edittodoitem._toDoItem);
            values.put(KEY_ITEM_DUEDATE, edittodoitem._dueDate);
            values.put(KEY_ITEM_IMPORTANCE, edittodoitem._importance);
            int rows = db.update(TABLE_TODO, values, KEY_ITEM_CONTENT + " = ? AND " + KEY_ITEM_DUEDATE + " = ? ", new String[]{oldtodoitem._toDoItem, oldtodoitem._dueDate});

            String primaryKeyQuery = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?",
                    KEY_ITEM_ID, TABLE_TODO, KEY_ITEM_CONTENT, KEY_ITEM_DUEDATE);
            Cursor cursor = db.rawQuery(primaryKeyQuery, new String[]{edittodoitem._toDoItem, edittodoitem._dueDate});
            try {
                if (cursor.moveToFirst()) {
                    cursorId = cursor.getInt(0);
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to edit item");
        } finally {
            db.endTransaction();
        }
        return cursorId;

    }

    /**
     * Delete item from the database
     */
    public void deleteToDo(ToDoItems toDoItems) {

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_TODO, KEY_ITEM_CONTENT + " = ? AND " + KEY_ITEM_DUEDATE + " = ? ", new String[]{toDoItems._toDoItem, toDoItems._dueDate});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete item");
        } finally {
            db.endTransaction();
        }

    }

    /**
     * Get all items from the database
     */
    public ArrayList<ToDoItems> getAllToDoItems() {

        ArrayList<ToDoItems> todolist = new ArrayList<>();
        String SELECT_QUERY = String.format("SELECT * FROM %s ", TABLE_TODO);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String cursor_content = cursor.getString(cursor.getColumnIndex(KEY_ITEM_CONTENT));
                    String cursor_duedate = cursor.getString(cursor.getColumnIndex(KEY_ITEM_DUEDATE));
                    String cursor_importance = cursor.getString(cursor.getColumnIndex(KEY_ITEM_IMPORTANCE));
                    ToDoItems toDoItem = new ToDoItems(cursor_content, cursor_duedate, cursor_importance);
                    todolist.add(toDoItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return todolist;

    }

}


