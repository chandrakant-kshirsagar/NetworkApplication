package com.devrex.sharedtest.repository

enum class HttpStatusCodes(val type: Int) {
    ERROR_200(200),
    ERROR_301(301),
    ERROR_302(302),
    ERROR_304(304),
    ERROR_400(400),
    ERROR_401(401),
    ERROR_403(403),
    ERROR_404(404),
    ERROR_500(500),
    ERROR_501(501);

    companion object {
        fun getHttpStatusCodes(type: Int) = values().first { it.type == type }
    }
}