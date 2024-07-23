package ivan.nekrasov.repository

import htsjdk.tribble.readers.TabixReader
import ivan.nekrasov.model.Annotation
import java.io.File
import java.io.InputStreamReader
import java.util.zip.GZIPInputStream


class AnnotationRepository(dataFilePath: String, indexFilePath: String) {
    private val tabixReader = TabixReader(dataFilePath, indexFilePath)
    private val dataFile = dataFilePath

    fun getAnnotation(rac: String, lap: Int, rap: Int, refkey: String): Annotation? {
        val query = "$rac:$lap-$rap"
        val iterator = tabixReader.query(query)

        while (true) {
            val line = iterator.next() ?: break
            val parts = line.split("\t")
            if (parts.size == 8 && parts[0] == rac && parts[1].toInt() == lap && parts[2].toInt() == rap && parts[3] == refkey) {
                return Annotation(
                    rac = parts[0],
                    lap = parts[1].toInt(),
                    rap = parts[2].toInt(),
                    refkey = parts[3],
                    vcfId = parts[4],
                    clnsig = parts[5],
                    clnrevstat = parts[6],
                    clnvc = parts[7]
                )
            }
        }
        return null
    }

    fun getFirst10Lines(): List<List<String>> {
        val first10Lines = mutableListOf<List<String>>()
        GZIPInputStream(File(dataFile).inputStream()).use { inputStream ->
            InputStreamReader(inputStream).use { reader ->
                reader.forEachLine { line ->
                    if (first10Lines.size >= 10) return@forEachLine
                    val parts = line.split("\t")
                    first10Lines.add(parts)
                }
            }
        }
        return first10Lines
    }
}