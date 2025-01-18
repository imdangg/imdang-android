package info.imdang.imdang.model.insight

sealed class InsightDetailItem {

    data class BasicInfo(
        val mainImage: String,
        val title: String,
        val address: String,
        val latitude: Double,
        val longitude: Double,
        val visitAt: String,
        val visitTimes: String,
        val visitMethods: String,
        val access: String,
        val summary: String
    ) : InsightDetailItem()

    data class Infra(val infra: InfraVo) : InsightDetailItem()

    data class AptEnvironment(val complexEnvironment: ComplexEnvironmentVo) : InsightDetailItem()

    data class AptFacility(val complexFacility: ComplexFacilityVo) : InsightDetailItem()

    data class GoodNews(val goodNews: GoodNewsVo) : InsightDetailItem()

    data class Invisible(val insightDetailState: InsightDetailState) : InsightDetailItem()
}
