package ro.ubb.ideasmanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.ubb.ideasmanager.model.IdeaModel

@Database(entities = [IdeaModel::class], version = 1)
abstract class IdeaDatabase : RoomDatabase(){

    abstract fun ideaDao() : IdeaDao

    companion object{
        @Volatile
        private var INSTANCE : IdeaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): IdeaDatabase {
            val inst = INSTANCE
            if (inst != null) {
                return inst
            }
            val instance =
                Room.databaseBuilder(
                    context.applicationContext,
                    IdeaDatabase::class.java,
                    "todo_db"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
            INSTANCE = instance
            return instance
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

        suspend fun populateDatabase(ideaDao: IdeaDao) {
//            itemDao.deleteAll()
//            val item = Item("1", "Hello")
//            itemDao.insert(item)
        }
    }

}