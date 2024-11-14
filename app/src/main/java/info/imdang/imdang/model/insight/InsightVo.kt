package info.imdang.imdang.model.insight

// todo : Sample data, 실제 서버 데이터 나오면 변경 예정
data class InsightVo(
    val thumbnail: String,
    val region: String,
    val recommendCount: Int,
    val title: String,
    val profileImage: String,
    val nickname: String
) {
    companion object {
        fun getSamples(size: Int): List<InsightVo> {
            val samples = mutableListOf<InsightVo>()
            repeat(size) {
                samples.add(
                    InsightVo(
                        thumbnail = "https://img.notionusercontent.com/s3/" +
                            "prod-files-secure%2Fa3d1f8de-8b5a-4645-a4b2-e19d21a4ce4b%2F5247bfc2-" +
                            "0741-4b22-b241-bd17091f44f8%2Fimg_apt_sample.png/size/w=2000?" +
                            "exp=1731585482&sig=7p4bIc4x4SE8DR7N7XAehylkFx46OMBmyEMO3nyXehQ",
                        region = "강남구 신논현동",
                        recommendCount = 24,
                        title = "초역세권 대단지 아파트 후기",
                        profileImage = "",
                        nickname = "홍길동"
                    )
                )
            }
            return samples
        }
    }
}
