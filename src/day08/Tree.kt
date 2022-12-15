package day08

/**
 * Information about the viewing block height and the viewing distance for a tree
 * toward each of the four cardinal directions.
 */
class Tree(internal val height: Int) {
    internal var viewingBlockHeight: FourDirections<Int> = FourDirections(-1, -1, -1, -1)
    internal var viewingDistance: FourDirections<Int> = FourDirections(-1, -1, -1, -1)

    /**
     * Indicating whether the tree is visible from at least
     * one of the four cardinal directions starting from an edge of the forest.
     */
    val visibleFromOneDirection
        get() = this.height > this.viewingBlockHeight.north ||
                this.height > this.viewingBlockHeight.south ||
                this.height > this.viewingBlockHeight.west ||
                this.height > this.viewingBlockHeight.east

    /**
     * The scenic score which is the product of the non-blocking viewing distance
     * from the tree towards each of the four cardinal directions.
     */
    val scenicScore
        get() = this.viewingDistance.north *
                this.viewingDistance.south *
                this.viewingDistance.west *
                this.viewingDistance.east
}
