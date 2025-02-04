package info.imdang.data.constant

object ResponseCode {

    /**
     * 성공적인 응답.
     */
    const val OK = 200

    /**
     * Authorization 인증 오류
     */
    const val UNAUTHORIZED = 401

    /**
     * 요청 처리 중 서버 오류
     */
    const val INTERNAL_SERVER_ERROR = 500

    /**
     * 네트워크 연결 안됨
     */
    const val NETWORK_NOT_CONNECTED = 600
}

enum class ErrorCode {
    J001, // 예상치 못한 오류, 현재 토큰 만료 시 발생
    J002, // 잘못된 JWT 서명
    J003, // 만료된 토큰
    J004, // 지원되지 않는 토큰
    J005, // 접근 거부
    J006, // 잘못된 JWT 토큰
    J007; // 로그인 후 사용

    companion object {
        fun fromString(code: String): ErrorCode? = entries.firstOrNull {
            it.name == code
        }
    }
}

enum class ErrorMessage(val message: String, val subMessage: String? = null) {
    EXCHANGE_REQUIRED(message = "인사이트 추천은 교환 후 가능해요"),
    ALREADY_ACCUSED(
        message = "이미 신고한 인사이트에요",
        subMessage = "동일한 인사이트를 중복으로\n신고할 수 없어요"
    ),
    INVALID_TOKEN(message = "Invalid Token");

    companion object {
        fun fromString(message: String): ErrorMessage? = entries.firstOrNull {
            it.name == message
        } ?: entries.firstOrNull {
            it.message == message
        }
    }
}
