package info.imdang.imdang.model.insight

// todo : Sample data, 실제 서버 데이터 나오면 변경 예정
data class InsightAptVo(
    val aptName: String,
    val aptCount: Int,
    val isSelected: Boolean
) {
    companion object {
        fun getSamples(size: Int): List<InsightAptVo> {
            val samples = mutableListOf<InsightAptVo>()
            repeat(size) {
                samples.add(
                    InsightAptVo(
                        aptName = "신논현 더 센트럴 푸르지오",
                        aptCount = 12,
                        isSelected = it == 0
                    )
                )
            }
            return samples
        }
    }
}
