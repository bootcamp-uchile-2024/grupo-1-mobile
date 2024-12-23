data class ParentItem(
    val title: String,
    val iconRes: Int, // Resource ID for the image
    val children: List<ChildItem>
)