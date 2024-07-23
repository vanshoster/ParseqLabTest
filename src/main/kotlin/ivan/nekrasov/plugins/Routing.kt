package ivan.nekrasov.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ivan.nekrasov.controller.annotationRoutes
import ivan.nekrasov.repository.AnnotationRepository
import ivan.nekrasov.service.AnnotationService

fun Application.configureRouting() {
//    val dataFilePath = "./src/main/resources/data/clinvar_20220430.aka.gz"
//    val indexFilePath = "./src/main/resources/data/clinvar_20220430.aka.gz.tbi"

    val dataFilePath = System.getenv("DATA_FILE_PATH") ?: "./src/main/resources/data/clinvar_20220430.aka.gz"
    val indexFilePath = System.getenv("INDEX_FILE_PATH") ?: "./src/main/resources/data/clinvar_20220430.aka.gz.tbi"

    val repository = AnnotationRepository(dataFilePath, indexFilePath)
    val service = AnnotationService(repository)

    routing {
        annotationRoutes(service)
    }
}
