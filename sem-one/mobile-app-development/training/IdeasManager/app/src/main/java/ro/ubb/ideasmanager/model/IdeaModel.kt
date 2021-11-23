package ro.ubb.ideasmanager.model

data class IdeaModel(val id: String, var text: String, var neededBudget: Int, var currentBudget: Int, var rating: Int)
