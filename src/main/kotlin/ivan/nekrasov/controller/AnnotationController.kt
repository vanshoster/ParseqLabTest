package ivan.nekrasov.controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ivan.nekrasov.service.AnnotationService

fun Route.annotationRoutes(service: AnnotationService) {
    get("/info") {
        val rac = call.request.queryParameters["rac"]
        val lap = call.request.queryParameters["lap"]?.toIntOrNull()
        val rap = call.request.queryParameters["rap"]?.toIntOrNull()
        val refkey = call.request.queryParameters["refkey"]

        if (rac == null || lap == null || rap == null || refkey == null) {
            call.respond(HttpStatusCode.BadRequest, "Missing parameters")
            return@get
        }

        val annotation = service.findAnnotation(rac, lap, rap, refkey)
        if (annotation != null) {
            call.respond(annotation)
        } else {
            call.respond(HttpStatusCode.NotFound, "Annotation not found")
        }
    }

    get("/first10") {
        val first10Lines = service.getFirst10Lines()
        call.respond(first10Lines)
    }
}