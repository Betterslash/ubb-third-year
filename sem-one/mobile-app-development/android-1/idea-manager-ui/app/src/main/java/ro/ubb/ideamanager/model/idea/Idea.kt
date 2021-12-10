package ro.ubb.ideamanager.model.idea

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ideas")
data class Idea(@PrimaryKey @ColumnInfo(name="_id")val _id : String,

                @ColumnInfo(name="title")
                var title: String,

                @ColumnInfo(name="text")
                var text: String,

                @ColumnInfo(name="needed_budget")
                var neededBudget : Int,

                @ColumnInfo(name="current_budget")
                var currentBudget : Int,

                @ColumnInfo(name="rating")
                var rating: Int
){
    override fun toString(): String = text
}