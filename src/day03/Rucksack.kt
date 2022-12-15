package day03

/**
 * A rucksack containing [content], which is a list of items.
 */
data class Rucksack(val content: String) {

    /**
     * The first half of the content.
     */
    val firstCompartment: String
        get() {
            val length = this.content.length
            return this.content.slice(0 until length / 2)
        }

    /**
     * The second half of the content.
     */
    val secondCompartment: String
        get() {
            val length = this.content.length
            return this.content.slice(length / 2 until length)
        }
}
