package info.imdang.imdang.model.address

data class RegionVo(
    val name: String,
    val isSelected: Boolean
) {
    companion object {
        fun getSamples(size: Int): List<RegionVo> {
            val samples = mutableListOf<RegionVo>()
            repeat(size) {
                samples.add(
                    RegionVo(
                        name = "강남구 $it",
                        isSelected = it == 0
                    )
                )
            }
            return samples
        }

        fun getDongSamples(size: Int): List<String> {
            val samples = mutableListOf<String>()
            repeat(size) {
                samples.add("논현동")
            }
            return samples
        }
    }
}
