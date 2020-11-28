package com.example.projectassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyRecipeOpener extends SQLiteOpenHelper
{
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

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_RECIPE + " text,"
                + COL_WEBSITE + " text,"
                + COL_INGREDIENTS  + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );

        onCreate(db);
    }


}
