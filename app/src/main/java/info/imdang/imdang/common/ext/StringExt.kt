package info.imdang.imdang.common.ext

fun String.snakeToCamelCase(): String {
    return "_[a-zA-Z]".toRegex().replace(lowercase()) {
        it.value.replace("_", "")
            .uppercase()
    }
}
