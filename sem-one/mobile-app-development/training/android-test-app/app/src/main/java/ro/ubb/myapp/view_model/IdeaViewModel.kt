package ro.ubb.myapp.view_model

import ro.ubb.myapp.model.Idea
import kotlin.random.Random

object IdeaViewModel {
    private val _ideas : MutableList<Idea> = ArrayList()
    private val random : Random = Random(1)
    private val alphabet : List<Char> = getAlphabet()
    val ideas get() = _ideas
    private fun getAlphabet() : List<Char>{
        val result: MutableList<Char> = ArrayList()
        for(i in 'a'..'z'){
            result.add(i)
        }
        return result
    }

    private fun generateIdea(id: String) : Idea{
        val title : String = generateString()
        val description = generateString(100)
        val budgetNeeded : Int = generateInt()
        val currentBudget : Int = generateInt(budgetNeeded)
        val rating : Int = random.nextInt(0, 5)
        return Idea(id, title, description, budgetNeeded, currentBudget, rating)
    }

    private fun generateString(upperBound:Int = 10): String {
        val size : Int = random.nextInt(1, upperBound)
        var result = ""
        for (i in 1..size){
            result += alphabet[random.nextInt(0,25)]
        }
        return result
    }

    private fun generateInt(upperBound: Int = 1000): Int{
        return random.nextInt(5, upperBound)
    }

    init {
        for (i in 0..100){
            addIdea(generateIdea(i.toString()))
        }
    }

    private fun addIdea(idea : Idea){
        ideas.add(idea)
    }
}