package ro.ubb.ideamanager.model.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.ubb.ideamanager.model.idea.Idea

@Database(entities = [Idea::class], version = 1)
abstract class IdeaDatabase : RoomDatabase(){
    abstract fun ideaDao(): IdeaDao

    companion object{
        @Volatile
        private var INSTANCE : IdeaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): IdeaDatabase {
            val instance = INSTANCE
            if(instance != null){
                return instance
            }
            val inst = Room.databaseBuilder(context, IdeaDatabase::class.java, "idea_db")
                .addCallback(WordDatabaseCallback(scope))
                .build()
            INSTANCE = inst
            return inst
        }

        private class WordDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.ideaDao())
                    }
                }
            }
        }

        fun populateDatabase(ideaDao: IdeaDao) {
        /*    ideaDao.deleteAll()
            val item = Idea("1", "Hello")
            itemDao.insert(item)
        */}
    }


}