package ivan.nekrasov.model

data class Annotation(
    val rac: String,
    val lap: Int,
    val rap: Int,
    val refkey: String,
    val vcfId: String,
    val clnsig: String,
    val clnrevstat: String,
    val clnvc: String
)