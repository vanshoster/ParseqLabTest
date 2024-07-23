package ivan.nekrasov.service

import ivan.nekrasov.model.Annotation
import ivan.nekrasov.repository.AnnotationRepository

class AnnotationService(private val repository: AnnotationRepository) {

    fun findAnnotation(rac: String, lap: Int, rap: Int, refkey: String): Annotation? {
        return repository.getAnnotation(rac, lap, rap, refkey)
    }

    fun getFirst10Lines(): List<List<String>> {
        return repository.getFirst10Lines()
    }
}