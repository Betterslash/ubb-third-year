package ro.ubb.ideasmanager.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ro.ubb.ideasmanager.model.IdeaModel

@Dao
interface IdeaDao {
    @Query("SELECT * FROM ideas ORDER BY text ASC")
    fun getAll() : LiveData<List<IdeaModel>>

    @Query("SELECT * FROM ideas WHERE _id=:id")
    fun getById(id:String) : LiveData<IdeaModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(idea: IdeaModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(idea: IdeaModel)

    @Query("DELETE FROM ideas WHERE _id =:id")
    suspend fun deleteById(id:String)
}