package blargh.warhammer.android; 

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public interface DbAdapter {

	static final String[] createStatements = new String[]{
		"create table if not exists person(uid integer primary key autoincrement, name text);", 
		"create table if not exists spec(spec text primary key);", 
		"create table if not exists stat(stat text primary key);", 
		"create table if not exists skill(skill text primary key, spec boolean, stat references stat(stat));", 
		"create table if not exists skills(person_uid references person(uid), skill references skill(skill), spec references spec(spec), training integer);", 
		"create table if not exists skill_spec(skill references skill(skill), spec references spec(spec));", 
		"create table if not exists talent(talent text primary key, spec boolean);", 
		"create table if not exists talents(person_uid references person(uid), talent references talent(talent), spec references spec(spec));", 
		"create table if not exists talent_spec(talent references talent(talent), spec references spec(spec));", 
		"create table if not exists trait(trait text primary key);", 
		"create table if not exists traits(person_uid references person(uid), talent references talent(talent), value integer);", 
		"create table if not exists weapon_special(special text);", 
		"create table if not exists ranged_weapon(name text, class references ranged_class(class), range text, single_shot boolean, semi_auto integer, full_auto integer, damage_dice integer, damage_modifier integer, damage_type references damage(type), penetration integer, clip integer, reload integer, weight integer);", 
		"create table if not exists ranged_specials (ranged_name references ranged_weapon(name), special references weapon_special(special));", 
		"create table if not exists melee_weapon(name text, class references melee_class(class), damage_dice integer, damage_modifier integer, damage_type references damage(type), penetration integer, weight integer);", 
		"create table if not exists melee_specials (melee_name references melee_weapon(name), special references weapon_special(special));", 
		"create table if not exists armor(name text, head integer, arms integer, body integer, legs integer, weight integer);"
	};

	static final String[] destroyStatements = new String[]{
		"drop table if exists person;", 
		"drop table if exists skill;", 
		"drop table if exists skills;", 
		"drop table if exists skill_spec;", 
		"drop table if exists talents;", 
		"drop table if exists talent;", 
		"drop table if exists talent_spec;", 
		"drop table if exists traits;", 
		"drop table if exists trait;", 
		"drop table if exists spec;", 
		"drop table if exists ranged_weapon;", 
		"drop table if exists ranged_specials;", 
		"drop table if exists melee_weapon;", 
		"drop table if exists melee_specials;", 
		"drop table if exists weapon_special;", 
		"drop table if exists armor;"
	};

	static final String[] talentsStatements = new String[]{
		"insert into talent(talent) values ('Abhor the Witch)');",
		"insert into talent(talent) values ('Acolyte Network)');",
		"insert into talent(talent) values ('Air of Authority)');",
		"insert into talent(talent) values ('Ally of the Departmento Munitorum)');",
		"insert into talent(talent) values ('Ambidextrous)');",
		"insert into talent(talent) values ('Armour of Contempt)');",
		"insert into talent(talent) values ('Armour-monger)');",
		"insert into talent(talent) values ('Arms Master)');",
		"insert into talent(talent) values ('Ascended Psychic Power)');",
		"insert into talent(talent) values ('Assassin Strike)');",
		"insert into talent(talent) values ('Astartes Weapon Specialization)');",
		"insert into talent(talent) values ('Astartes Weapon Training)');",
		"insert into talent(talent) values ('Autosanguine)');",
		"insert into talent(talent) values ('Basic Weapons Expertise)');",
		"insert into talent(talent) values ('Basic Weapon Training(bolt)');",
		"insert into talent(talent) values ('Basic Weapon Training(flame)');",
		"insert into talent(talent) values ('Basic Weapon Training(las)');",
		"insert into talent(talent) values ('Basic Weapon Training(launcher)');",
		"insert into talent(talent) values ('Basic Weapon Training(melta)');",
		"insert into talent(talent) values ('Basic Weapon Training(plasma)');",
		"insert into talent(talent) values ('Basic Weapon Training(primitive)');",
		"insert into talent(talent) values ('Basic Weapon Training(sp)');",
		"insert into talent(talent) values ('Basic Weapon Training(universal)');",
		"insert into talent(talent) values ('Bastion of Iron Will)');",
		"insert into talent(talent) values ('Battle Rage)');",
		"insert into talent(talent) values ('Berserk Charge)');",
		"insert into talent(talent) values ('Berserker)');",
		"insert into talent(talent) values ('Binary Chatter)');",
		"insert into talent(talent) values ('Blade Dancer)');",
		"insert into talent(talent) values ('Blademaster)');",
		"insert into talent(talent) values ('Blessed Radiance)');",
		"insert into talent(talent) values ('Blind Fighting)');",
		"insert into talent(talent) values ('Bloodtracker)');",
		"insert into talent(talent) values ('Bolter Drill)');",
		"insert into talent(talent) values ('Bulging Biceps)');",
		"insert into talent(talent) values ('Call of Iron)');",
		"insert into talent(talent) values ('Call To Vengeance)');",
		"insert into talent(talent) values ('Catfall)');",
		"insert into talent(talent) values ('Chem Geld)');",
		"insert into talent(talent) values ('Cleanse and Purify)');",
		"insert into talent(talent) values ('Combat Formation)');",
		"insert into talent(talent) values ('Combat Master)');",
		"insert into talent(talent) values ('Combat Sense)');",
		"insert into talent(talent) values ('Committed Xanthite)');",
		"insert into talent(talent) values ('Concealed Cavity)');",
		"insert into talent(talent) values ('Conciliator)');",
		"insert into talent(talent) values ('Conditioned Intellect)');",
		"insert into talent(talent) values ('Corpus Conversion)');",
		"insert into talent(talent) values ('Counter-attack)');",
		"insert into talent(talent) values ('Crack Shot)');",
		"insert into talent(talent) values ('Crimelord)');",
		"insert into talent(talent) values ('Crippling Strike)');",
		"insert into talent(talent) values ('Crushing Blow)');",
		"insert into talent(talent) values ('Dark Soul)');",
		"insert into talent(talent) values ('Deadeye Shot)');",
		"insert into talent(talent) values ('Death Blow)');",
		"insert into talent(talent) values ('Death From Above)');",
		"insert into talent(talent) values ('Deathwatch Training)');",
		"insert into talent(talent) values ('Decadence)');",
		"insert into talent(talent) values ('Deflect Shot)');",
		"insert into talent(talent) values ('Denouncer)');",
		"insert into talent(talent) values ('Die Hard)');",
		"insert into talent(talent) values ('Disarm)');",
		"insert into talent(talent) values ('Discipline Focus(biomancy)');",
		"insert into talent(talent) values ('Discipline Focus(divination)');",
		"insert into talent(talent) values ('Discipline Focus(pyromancy)');",
		"insert into talent(talent) values ('Discipline Focus(telekinetics)');",
		"insert into talent(talent) values ('Discipline Focus(telepathy)');",
		"insert into talent(talent) values ('Disturbing Voice)');",
		"insert into talent(talent) values ('Divine Ministration)');",
		"insert into talent(talent) values ('Double Team)');",
		"insert into talent(talent) values ('Dual Shot)');",
		"insert into talent(talent) values ('Dual Strike)');",
		"insert into talent(talent) values ('Duty Unto Death)');",
		"insert into talent(talent) values ('Electrical Succour)');",
		"insert into talent(talent) values ('Electro Graft Use)');",
		"insert into talent(talent) values ('Electro Illumination)');",
		"insert into talent(talent) values ('Enemy(specific)');",
		"insert into talent(talent) values ('Energy Cache)');",
		"insert into talent(talent) values ('Enhanced Bionic Frame)');",
		"insert into talent(talent) values ('Exemplar of Honour)');",
		"insert into talent(talent) values ('Exemplar of Metal)');",
		"insert into talent(talent) values ('Exotic Weapon Training(specific)');",
		"insert into talent(talent) values ('Eye of Vengeance)');",
		"insert into talent(talent) values ('Favoured By Fate)');",
		"insert into talent(talent) values ('Favoured By the Warp)');",
		"insert into talent(talent) values ('Fearless)');",
		"insert into talent(talent) values ('Feedback Screech)');",
		"insert into talent(talent) values ('Ferric Lure)');",
		"insert into talent(talent) values ('Ferric Summons)');",
		"insert into talent(talent) values ('Flagellant)');",
		"insert into talent(talent) values ('Flame Weapon Training(universal)');",
		"insert into talent(talent) values ('Flesh Render)');",
		"insert into talent(talent) values ('Foresight)');",
		"insert into talent(talent) values ('Frenzy)');",
		"insert into talent(talent) values ('Furious Assault)');",
		"insert into talent(talent) values ('Good Reputation(specific)');",
		"insert into talent(talent) values ('Gravitic Levitation)');",
		"insert into talent(talent) values ('Guardian)');",
		"insert into talent(talent) values ('Gun Blessing)');",
		"insert into talent(talent) values ('Gunfighter Saint)');",
		"insert into talent(talent) values ('Gunslinger)');",
		"insert into talent(talent) values ('Hammer Blow)');",
		"insert into talent(talent) values ('Hard Bargain)');",
		"insert into talent(talent) values ('Hard Target)');",
		"insert into talent(talent) values ('Hardy)');",
		"insert into talent(talent) values ('Hatred(chaos Space Marines)');",
		"insert into talent(talent) values ('Hatred(criminals)');",
		"insert into talent(talent) values ('Hatred(cult(specific)');",
		"insert into talent(talent) values ('Hatred(daemons)');",
		"insert into talent(talent) values ('Hatred(heretics)');",
		"insert into talent(talent) values ('Hatred(mutants)');",
		"insert into talent(talent) values ('Hatred(rogue Trader(specific)');",
		"insert into talent(talent) values ('Hatred(pirates)');",
		"insert into talent(talent) values ('Hatred(psykers)');",
		"insert into talent(talent) values ('Hatred(xeno(specific)');",
		"insert into talent(talent) values ('Heavy Weapon Training(bolt)');",
		"insert into talent(talent) values ('Heavy Weapon Training(flame)');",
		"insert into talent(talent) values ('Heavy Weapon Training(las)');",
		"insert into talent(talent) values ('Heavy Weapon Training(launcher)');",
		"insert into talent(talent) values ('Heavy Weapon Training(melta)');",
		"insert into talent(talent) values ('Heavy Weapon Training(plasma)');",
		"insert into talent(talent) values ('Heavy Weapon Training(primitive)');",
		"insert into talent(talent) values ('Heavy Weapon Training(sp)');",
		"insert into talent(talent) values ('Heavy Weapon Training(universal)');",
		"insert into talent(talent) values ('Heavy Weapons Expertise)');",
		"insert into talent(talent) values ('Heightened Reactions)');",
		"insert into talent(talent) values ('Heightened Senses(sight)');",
		"insert into talent(talent) values ('Heightened Senses(sound)');",
		"insert into talent(talent) values ('Heightened Senses(smell)');",
		"insert into talent(talent) values ('Heightened Senses(taste)');",
		"insert into talent(talent) values ('Heightened Senses(touch)');",
		"insert into talent(talent) values ('Hidden Cultist)');",
		"insert into talent(talent) values ('Hip Shooting)');",
		"insert into talent(talent) values ('Hotshot Pilot)');",
		"insert into talent(talent) values ('Hunter of Aliens)');",
		"insert into talent(talent) values ('Improved Warp Sense)');",
		"insert into talent(talent) values ('Independent Targeting)');",
		"insert into talent(talent) values ('Indomitable Fortitude)');",
		"insert into talent(talent) values ('Infused Knowledge)');",
		"insert into talent(talent) values ('Insanely Faithful)');",
		"insert into talent(talent) values ('Inspire Wrath)');",
		"insert into talent(talent) values ('Into the Jaws of Hell)');",
		"insert into talent(talent) values ('Iron Discipline)');",
		"insert into talent(talent) values ('Iron Jaw)');",
		"insert into talent(talent) values ('Jaded)');",
		"insert into talent(talent) values ('Killing Strike)');",
		"insert into talent(talent) values ('Leap Up)');",
		"insert into talent(talent) values ('Last Man Standing)');",
		"insert into talent(talent) values ('Light Sleeper)');",
		"insert into talent(talent) values ('Lightning Attack)');",
		"insert into talent(talent) values ('Lightning Reflexes)');",
		"insert into talent(talent) values ('Litany of Hate)');",
		"insert into talent(talent) values ('Logis Implant)');",
		"insert into talent(talent) values ('Lord of Domains)');",
		"insert into talent(talent) values ('Luminen Blast)');",
		"insert into talent(talent) values ('Luminen Charge)');",
		"insert into talent(talent) values ('Luminen Shock)');",
		"insert into talent(talent) values ('Machinator Array)');",
		"insert into talent(talent) values ('Machine Spirit Empathy)');",
		"insert into talent(talent) values ('Maglev Grace)');",
		"insert into talent(talent) values ('Maglev Transcendence)');",
		"insert into talent(talent) values ('Marksman)');",
		"insert into talent(talent) values ('Master&commander)');",
		"insert into talent(talent) values ('Master Chirurgeon)');",
		"insert into talent(talent) values ('Master Enginseer)');",
		"insert into talent(talent) values ('Master Orator)');",
		"insert into talent(talent) values ('Mechadendrite Use(gun)');",
		"insert into talent(talent) values ('Mechadendrite Use(machine Spirit Interface)');",
		"insert into talent(talent) values ('Mechadendrite Use(manipulator)');",
		"insert into talent(talent) values ('Mechadendrite Use(medicae)');",
		"insert into talent(talent) values ('Mechadendrite Use(optical)');",
		"insert into talent(talent) values ('Mechadendrite Use(servo-arm)');",
		"insert into talent(talent) values ('Mechadendrite Use(utility)');",
		"insert into talent(talent) values ('Mechadendrite Use(weapon)');",
		"insert into talent(talent) values ('Meditation)');",
		"insert into talent(talent) values ('Melee Weapon Expertise)');",
		"insert into talent(talent) values ('Melee Weapon Training(primitive)');",
		"insert into talent(talent) values ('Melee Weapon Training(chain)');",
		"insert into talent(talent) values ('Melee Weapon Training(shock)');",
		"insert into talent(talent) values ('Melee Weapon Training(power)');",
		"insert into talent(talent) values ('Melee Weapon Training(universal)');",
		"insert into talent(talent) values ('Member of the Tyrantine Cabal)');",
		"insert into talent(talent) values ('Mental Aegis)');",
		"insert into talent(talent) values ('Mental Fortress)');",
		"insert into talent(talent) values ('Mental Rage)');",
		"insert into talent(talent) values ('Mighty Shot)');",
		"insert into talent(talent) values ('Mimic)');",
		"insert into talent(talent) values ('Minor Psychic Power(specific)');",
		"insert into talent(talent) values ('Mnemonic Purging)');",
		"insert into talent(talent) values ('Navigator)');",
		"insert into talent(talent) values ('Navigator Power)');",
		"insert into talent(talent) values ('Nerves of Steel)');",
		"insert into talent(talent) values ('Oath Bonded To the Angels of Death)');",
		"insert into talent(talent) values ('Orthoproxy)');",
		"insert into talent(talent) values ('Outspoken Monodominant)');",
		"insert into talent(talent) values ('Paranoia)');",
		"insert into talent(talent) values ('Peer(specific)');",
		"insert into talent(talent) values ('Peerless Marksman)');",
		"insert into talent(talent) values ('Perfect Shot)');",
		"insert into talent(talent) values ('Pious Observation)');",
		"insert into talent(talent) values ('Pistol Expertise)');",
		"insert into talent(talent) values ('Pistol Training(bolt)');",
		"insert into talent(talent) values ('Pistol Training(flame)');",
		"insert into talent(talent) values ('Pistol Training(las)');",
		"insert into talent(talent) values ('Pistol Training(launcher)');",
		"insert into talent(talent) values ('Pistol Training(melta)');",
		"insert into talent(talent) values ('Pistol Training(plasma)');",
		"insert into talent(talent) values ('Pistol Training(primitive)');",
		"insert into talent(talent) values ('Pistol Training(sp)');",
		"insert into talent(talent) values ('Pistol Training(universal)');",
		"insert into talent(talent) values ('Polyglot)');",
		"insert into talent(talent) values ('Power Well)');",
		"insert into talent(talent) values ('Precise Blow)');",
		"insert into talent(talent) values ('Preternatural Speed)');",
		"insert into talent(talent) values ('Prosanguine)');",
		"insert into talent(talent) values ('Psy Rating)');",
		"insert into talent(talent) values ('Psychic Discipline)');",
		"insert into talent(talent) values ('Psychic Power(specific)');",
		"insert into talent(talent) values ('Psychic Technique(specific)');",
		"insert into talent(talent) values ('Pure Faith)');",
		"insert into talent(talent) values ('Purge the Unclean)');",
		"insert into talent(talent) values ('Purity of the Machine)');",
		"insert into talent(talent) values ('Quick Draw)');",
		"insert into talent(talent) values ('Rapid Reaction)');",
		"insert into talent(talent) values ('Rapid Reload)');",
		"insert into talent(talent) values ('Renowned Warrant)');",
		"insert into talent(talent) values ('Renowned Warrior)');",
		"insert into talent(talent) values ('Resistance(cold)');",
		"insert into talent(talent) values ('Resistance(fear)');",
		"insert into talent(talent) values ('Resistance(heat)');",
		"insert into talent(talent) values ('Resistance(poisons)');",
		"insert into talent(talent) values ('Resistance(psychic Powers)');",
		"insert into talent(talent) values ('Revolutionary)');",
		"insert into talent(talent) values ('Rite of Awe)');",
		"insert into talent(talent) values ('Rite of Fear)');",
		"insert into talent(talent) values ('Rite of Protection)');",
		"insert into talent(talent) values ('Rite of Pure Thought)');",
		"insert into talent(talent) values ('Rite of Sanctioning)');",
		"insert into talent(talent) values ('Rival(specific)');",
		"insert into talent(talent) values ('Scourge of Heretics)');",
		"insert into talent(talent) values ('Servo-harness Integration)');",
		"insert into talent(talent) values ('Shadowlord)');",
		"insert into talent(talent) values ('Sharpshooter)');",
		"insert into talent(talent) values ('Signature Wargear)');",
		"insert into talent(talent) values ('Signature Wargear(master)');",
		"insert into talent(talent) values ('Signature Wargear(hero)');",
		"insert into talent(talent) values ('Slayer of Daemons)');",
		"insert into talent(talent) values ('Sleeper Agent)');",
		"insert into talent(talent) values ('Soul of Stone)');",
		"insert into talent(talent) values ('Sound Constitution)');",
		"insert into talent(talent) values ('Sprint)');",
		"insert into talent(talent) values ('Stalwart Defence)');",
		"insert into talent(talent) values ('Step Aside)');",
		"insert into talent(talent) values ('Storm of Blows)');",
		"insert into talent(talent) values ('Storm of Iron)');",
		"insert into talent(talent) values ('Street Fighting)');",
		"insert into talent(talent) values ('Strong Minded)');",
		"insert into talent(talent) values ('Sure Strike)');",
		"insert into talent(talent) values ('Swift Attack)');",
		"insert into talent(talent) values ('Takedown)');",
		"insert into talent(talent) values ('Talented(specific)');",
		"insert into talent(talent) values ('Target Selection)');",
		"insert into talent(talent) values ('Technical Knock)');",
		"insert into talent(talent) values ('The Ear of the Lord Sector)');",
		"insert into talent(talent) values ('The Emperor Protects)');",
		"insert into talent(talent) values ('The Flesh Is Weak)');",
		"insert into talent(talent) values ('Thrown Weapon Expertise)');",
		"insert into talent(talent) values ('Thrown Weapon Training(chain)');",
		"insert into talent(talent) values ('Thrown Weapon Training(power)');",
		"insert into talent(talent) values ('Thrown Weapon Training(primitive)');",
		"insert into talent(talent) values ('Thrown Weapon Training(shock)');",
		"insert into talent(talent) values ('Thrown Weapon Training(universal)');",
		"insert into talent(talent) values ('Thunder Charge)');",
		"insert into talent(talent) values ('Total Recall)');",
		"insert into talent(talent) values ('Transcendent Hate)');",
		"insert into talent(talent) values ('True Grit)');",
		"insert into talent(talent) values ('Two-weapon Wielder(ballistic)');",
		"insert into talent(talent) values ('Two-weapon Wielder(melee)');",
		"insert into talent(talent) values ('Unarmed Ascendant Mastery)');",
		"insert into talent(talent) values ('Unarmed Master)');",
		"insert into talent(talent) values ('Unarmed Warrior)');",
		"insert into talent(talent) values ('Unassailable Grace)');",
		"insert into talent(talent) values ('Unbowed and Unbroken)');",
		"insert into talent(talent) values ('Unremarkable)');",
		"insert into talent(talent) values ('Unshakeable Faith)');",
		"insert into talent(talent) values ('Voice of the Masses)');",
		"insert into talent(talent) values ('Voice of the Omnissiah)');",
		"insert into talent(talent) values ('Void Tactician)');",
		"insert into talent(talent) values ('Wall of Steel)');",
		"insert into talent(talent) values ('Warmonger)');",
		"insert into talent(talent) values ('Warp Affinity)');",
		"insert into talent(talent) values ('Warp Conduit)');",
		"insert into talent(talent) values ('Warp Sense)');",
		"insert into talent(talent) values ('Watched From On High)');",
		"insert into talent(talent) values ('Weapon-tech)');",
		"insert into talent(talent) values ('Whirlwind of Death)');",
		"insert into talent(talent) values ('Whispers)');",
		"insert into talent(talent) values ('Wide Correspondence)');",
		"insert into talent(talent) values ('Wisdom of the Ancients)');",
		"insert into talent(talent) values ('Worthy of the Calixian Elite)');",
		"insert into talent(talent) values ('Wrath of the Righteous)');"
	};
	
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
						for (String destroyStatement : destroyStatements) {
							db.execSQL(destroyStatement);
						}
						
					}

					@Override
					public void onCreate(SQLiteDatabase db) {
						for (String createStatement : createStatements) {
							db.execSQL(createStatement);
						}
						
						for(String talentStatement : talentsStatements){
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
