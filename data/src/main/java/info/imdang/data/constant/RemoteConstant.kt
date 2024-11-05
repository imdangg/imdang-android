package info.imdang.data.constant

import info.imdang.data.BuildConfig

val API_SERVER: String =
    if (BuildConfig.DEBUG) "https://api.github.com" else "https://api.github.com"
