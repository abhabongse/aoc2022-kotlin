package day03

/**
 * Priority based on the given item type.
 */
object ItemPriority {
    private const val itemTypes = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    /**
     * Looks up the priority value for the given item type.
     */
    fun lookup(itemType: Char): Int {
        val index = itemTypes.indexOf(itemType)
        return if (index > 0) {
            index
        } else {
            throw IllegalArgumentException("invalid item type: $itemType")
        }
    }
}
