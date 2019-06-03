package com.tetragramato

import spark.Spark.get

/**
 * @author vivienbrissat
 * Date: 2019-05-28
 */
fun main(args: Array<String>) {
    get("/hello") { req, res -> "Hello World" }
}