package day11

/**
 * Monkey algorithm about dealing with each item,
 * how it manipulates the worry level for each item,
 * and how it throws to other monkey fitting a certain criterion.
 */
data class MonkeyAlgorithm(val modifyOperation: ModifyOperation, val throwOperation: ThrowOperation) {

    /**
     * The result from applying the monkey algorithm to an item.
     * Specifically it determines the final [worryLevel] as well as
     * telling which [nextMonkeyNo] will have the chance to play with the item
     * in the next round.
     */
    data class Result(val worryLevel: Int, val nextMonkeyNo: Int)

    /**
     * Applies the monkey algorithm to the item with the given initial [worryLevel].
     */
    fun apply(worryLevel: Int): Result {
        TODO()
    }
}
