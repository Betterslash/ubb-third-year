package ro.ubb.ideamanager.model.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ro.ubb.ideamanager.model.idea.Idea

@Dao
interface IdeaDao {
    @Query("SELECT * FROM ideas ORDER BY title ASC")
    fun getAll() : LiveData<List<Idea>>

    @Query("SELECT * FROM ideas WHERE _id=:id ")
    fun getById(id: String) : LiveData<Idea>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(idea: Idea)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(idea: Idea)

    @Query("DELETE FROM ideas")
    suspend fun deleteAll()
}