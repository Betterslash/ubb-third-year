package ro.ubb.myapp.model

data class Idea(val id: String,
                val title: String,
                val description: String,
                val budgetNeeded: Int,
                val currentBudget: Int,
                val rating: Int)
