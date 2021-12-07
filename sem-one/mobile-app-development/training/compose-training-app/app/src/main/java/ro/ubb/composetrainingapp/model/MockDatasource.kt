package ro.ubb.composetrainingapp.model

object MockDatasource {
    var ideas : MutableList<Idea> = initializeMockData()
    private fun initializeMockData(): MutableList<Idea> {
        val result = ArrayList<Idea>()
        for (i in 0..15){
            val idea = Idea(i.toString(), "Idea_$i", "Idea_$i text")
            result.add(idea)
        }
        return result
    }
}