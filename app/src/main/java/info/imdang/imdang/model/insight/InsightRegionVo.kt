package info.imdang.imdang.model.insight

// todo : Sample data, 실제 서버 데이터 나오면 변경 예정
data class InsightRegionVo(
    val region: String,
    val aptCount: Int,
    val insightCount: Int,
    val isSelected: Boolean = false
) {
    companion object {
        fun getSamples(size: Int): List<InsightRegionVo> {
            val samples = mutableListOf<InsightRegionVo>()
            repeat(size) {
                samples.add(
                    InsightRegionVo(
                        region = "서울시 강남구 신논현동",
                        aptCount = 3,
                        insightCount = 3
                    )
                )
            }
            return samples
        }
    }
}
