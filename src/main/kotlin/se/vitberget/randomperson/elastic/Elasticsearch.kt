package se.vitberget.randomperson.elastic

import org.apache.http.HttpHost
import org.apache.http.entity.ContentType.APPLICATION_JSON
import org.apache.http.entity.ContentType.parse
import org.apache.http.nio.entity.NStringEntity
import org.elasticsearch.client.Request
import org.elasticsearch.client.RestClient
import se.vitberget.randomperson.domain.Person
import java.io.IOException
import java.util.*

fun getElasticClient(props: Properties): RestClient =
    RestClient.builder(
        HttpHost(
            props.getProperty("elastic.host"),
            props.getProperty("elastic.port").toInt()
        )
    ).build()

fun dropAndCreateElasticIdx(client: RestClient, idx: String) {
    dropElasticIdx(client, idx)
    createElasticIdx(client,idx)
}

fun dropElasticIdx(client: RestClient, idx: String) {
    try {
        client.performRequest(Request("DELETE", "/$idx"))
    }catch (e: IOException) {
        print("faild to remove idx (might not exist?)")
    }
}

fun createElasticIdx(client: RestClient, idx: String) {
    val payload = """{
                        "settings" : {
                             "index" : {
                                 "number_of_shards" : 1,
                                 "number_of_replicas" : 0
                             }
                         }
                     }""".trimIndent()

    val request = Request("PUT", "/$idx")
    request.entity = NStringEntity(payload, APPLICATION_JSON)

    client.performRequest(request)
}

fun insertIntoElastic(client: RestClient, idx: String, persons: List<Person>) {
    val payload = persons
        .map { jsonIfy(it) }
        .map { "{\"index\": {\"_index\": \"idx\"}}\n$it\n" }
        .joinToString("")
//        .joinToString(separator = "\n")

    println("---payload")
    print(payload)

    val request = Request("POST", "/$idx/_bulk")
    request.entity = NStringEntity(payload, parse("application/x-ndjson"))

    val response = client.performRequest(request)
}
