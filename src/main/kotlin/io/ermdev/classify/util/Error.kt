package io.ermdev.classify.util

import java.sql.Timestamp

class Error(var timestamp: Timestamp = Timestamp(System.currentTimeMillis()),
            var status: Int = 0,
            var error: String = "",
            var message: String = "") {

    override fun toString(): String {
        return "{\n" +
                "    \"timestamp\": \"$timestamp\",\n" +
                "    \"status\": $status,\n" +
                "    \"error\": \"$error\",\n" +
                "    \"message\": \"$message\"\n" +
                "}"
    }

}