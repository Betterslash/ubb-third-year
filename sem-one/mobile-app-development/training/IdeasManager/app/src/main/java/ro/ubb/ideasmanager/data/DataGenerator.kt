package ro.ubb.ideasmanager.data

import android.util.Log
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.model.IdeaModel
import java.util.*
import kotlin.collections.ArrayList

class DataGenerator {

    private fun initializeAlphabet(): MutableList<Char> {
        val alphabet : MutableList<Char> = ArrayList()
        for (i in 'a'..'z'){
            alphabet.add(i)
            alphabet.add(i.uppercaseChar())
        }
        for (i in 0..9){
            alphabet.add('0' + i)
        }
        return alphabet
    }

    private fun generateTitle(alphabet : MutableList<Char>) : String{
        var result = ""
        val random = Random()
        for (i in 0..10){
            result += alphabet[random.nextInt(50)].toString()
        }
        return result
    }

    private fun generateDescription(alphabet: MutableList<Char>): String{
        var result = ""
        val random = Random()
        for (i in 0..100){
            result += alphabet[random.nextInt(50)].toString()
            if(i == 10){
                result += " "
            }
        }
        return result
    }

    private fun generateNeededBudget() : Int{
        return Random().nextInt(1000)
    }

    fun createObject() : IdeaModel{
        val alphabet = initializeAlphabet()
        val title = generateTitle(alphabet)
        val neededBudget = generateNeededBudget()
        val currentBudget = 0
        val description = generateDescription(alphabet)
        val rating = 0
        return IdeaModel(title, description, neededBudget, currentBudget, rating)
    }

    fun generateObjects() : List<IdeaModel>{
        val results : MutableList<IdeaModel> = ArrayList()
        for (i in 1..100){
            results.add(this.createObject())
        }
        Log.i(TAG, results.toString())
        return results
    }
}