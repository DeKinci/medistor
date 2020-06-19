package com.dekinci.filestor.commons

import java.util.stream.Stream
import java.util.stream.StreamSupport

fun <T> Iterable<T>.stream(parallel: Boolean = false): Stream<T> {
    return StreamSupport.stream(this.spliterator(), parallel)
}