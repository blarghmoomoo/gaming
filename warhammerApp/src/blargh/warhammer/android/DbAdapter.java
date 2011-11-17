package blargh.warhammer.android; 

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public interface DbAdapter {
	
	public DbAdapter open();
	public DbAdapter close();
	public DbAdapter createPerson(String name);
	public DbAdapter addTalent(int personUid, String talent, int value);
	public Cursor listAllPersons();
	public Cursor listSkills(int personUid);
	public Cursor listTalents(int personUid);
	public Cursor listTraits(int personUid);
	public Cursor listWeapons(int personUid);
	public Cursor listArmor(int personUid);

	public class Factory{
		public static DbAdapter createCharacterDb(final Context context, final String dbName, final int version){
			return new DbAdapter(){
				private SQLiteDatabase db;
				private SQLiteOpenHelper dbHelper = new SQLiteOpenHelper(context, dbName, null, version) {

					@Override
					public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
						for (String destroyStatement : DbStatments.destroyStatements) {
							db.execSQL(destroyStatement);
						}
						
					}

					@Override
					public void onCreate(SQLiteDatabase db) {
						for (String createStatement : DbStatments.createStatements) {
							db.execSQL(createStatement);
						}
						
						for(String skillStatement : DbStatments.skillsStatements){
							db.execSQL(skillStatement);
						}

						for(String talentStatement : DbStatments.talentsStatements){
							db.execSQL(talentStatement);
						}
					}
				};

				@Override
				public DbAdapter open() {

					db = dbHelper.getWritableDatabase();

					return this;
				}

				@Override
				public DbAdapter close() {

					dbHelper.close();

					return this;
				}

				@Override
				public DbAdapter createPerson(String name) {

					ContentValues contentValues = new ContentValues();
					contentValues.put("name", name);
					db.insert("person", null, contentValues);

					return this;
				}

				@Override
				public Cursor listAllPersons() {
					return db.query("person", new String[]{"uid", "name"}, null, null, null, null, null);
				}

				@Override
				public Cursor listSkills(int personUid) {
					return db.query("skills", new String[]{"skill", "spec"}, "person_uid=" + personUid, null, null, null, "skill");
				}

				@Override
				public Cursor listTalents(int personUid) {
					return db.query("talents", new String[]{"talent", "spec"}, "person_uid=" + personUid, null, null, null, "talent");
				}

				@Override
				public Cursor listTraits(int personUid) {
					return db.query("traits", new String[]{"trait", "value"}, "person_uid=" + personUid, null, null, null, "trait");
				}

				@Override
				public Cursor listWeapons(int personUid) {
					return null;
				}

				@Override
				public Cursor listArmor(int personUid) {
					return null;
				}

				@Override
				public DbAdapter addTalent(int personUid, String talent,
						int value) {
					// TODO Auto-generated method stub
					return null;
				}

				
			};
		}
	}
}
