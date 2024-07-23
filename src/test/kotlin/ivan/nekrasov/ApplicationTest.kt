package ivan.nekrasov

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ivan.nekrasov.plugins.*
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testInfoEndpoint() = testApplication {
        environment {
            System.setProperty("DATA_FILE_PATH", "src/test/resources/data/clinvar_20220430.aka.gz")
            System.setProperty("INDEX_FILE_PATH", "src/test/resources/data/clinvar_20220430.aka.gz.tbi")
        }

        application {
            configureRouting()
        }

        client.get("/info?rac=NC_000001.11&lap=926024&rap=926026&refkey=A").apply {
            assertEquals(HttpStatusCode.OK, status)
            val expectedJson = """
                {
                    "rac": "NC_000001.11",
                    "lap": 926024,
                    "rap": 926026,
                    "refkey": "A",
                    "vcfId": "1555362",
                    "clnsig": "Likely_benign",
                    "clnrevstat": "criteria_provided,_single_submitter",
                    "clnvc": "single_nucleotide_variant"
                }
            """.trimIndent()
            JSONAssert.assertEquals(expectedJson, bodyAsText(), true)
        }
    }

    @Test
    fun testMissingParameter() = testApplication {
        environment {
            System.setProperty("DATA_FILE_PATH", "src/test/resources/data/clinvar_20220430.aka.gz")
            System.setProperty("INDEX_FILE_PATH", "src/test/resources/data/clinvar_20220430.aka.gz.tbi")
        }

        application {
            configureRouting()
        }

        client.get("/info?rac=NC_000001.11&lap=926024&rap=926026").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
            assertEquals("Missing or invalid parameters", bodyAsText())
        }
    }

    @Test
    fun testAnnotationNotFound() = testApplication {
        environment {
            System.setProperty("DATA_FILE_PATH", "src/test/resources/data/clinvar_20220430.aka.gz")
            System.setProperty("INDEX_FILE_PATH", "src/test/resources/data/clinvar_20220430.aka.gz.tbi")
        }

        application {
            configureRouting()
        }

        client.get("/info?rac=NC_000001.11&lap=999999&rap=999999&refkey=A").apply {
            assertEquals(HttpStatusCode.NotFound, status)
            assertEquals("Annotation not found", bodyAsText())
        }
    }
}
