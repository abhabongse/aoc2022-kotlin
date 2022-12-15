package day05

/** A view representing the crate stacking configuration. */
typealias View = HashMap<Char, ArrayDeque<Char>>

/** Creates a deep copy of the view. */
fun View.copy(): View {
    val view: View = hashMapOf()
    this.entries.forEach {
        view[it.key] = ArrayDeque(it.value)
    }
    return view
}
