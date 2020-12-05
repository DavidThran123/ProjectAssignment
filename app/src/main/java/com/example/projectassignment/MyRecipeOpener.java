package com.example.projectassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyRecipeOpener extends SQLiteOpenHelper
{
    /**
     * Sqlite Database query parameters
     * @param DATABASE_NAME The name of database schema for recipes
     * @param TABLE_NAME The recipe table name query parameter
     * @param COL_RECIPE The recipe title query parameter
     * @param COL_INGREDIENTS The ingredients list query parameter
     * @param COL_WEBSITE The website query parameter
     * @param COL_ID the row id of the database record
     *
     */
    protected final static String DATABASE_NAME = "RecipeDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "RECIPES";
    public final static String COL_RECIPE = "RECIPE";
    public final static String COL_INGREDIENTS = "INGREDIENTS";
    public final static String COL_WEBSITE = "WEBSITE";
    public final static String COL_ID = "_id";

    public MyRecipeOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME,null, VERSION_NUM);
    }

    /**
     * Creates a table inside of the database for Recipes
     *
     * @param db The SQLite Database object to create a table in.
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_RECIPE + " text,"
                + COL_WEBSITE + " text,"
                + COL_INGREDIENTS  + " text);");
    }


    /**
     * If the current database version is greater than the previous
     * version of the database, then a new table must be created.
     *
     * @param db The SQLite Database object to create a table in.
     * @param oldVersion The previous version of the database
     * @param newVersion The current version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );

        onCreate(db);
    }


}
