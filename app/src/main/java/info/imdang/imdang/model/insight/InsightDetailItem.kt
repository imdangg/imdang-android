package info.imdang.imdang.model.insight

sealed class InsightDetailItem {

    data class BasicInfo(val basicInfo: InsightDetailBasicInfo) : InsightDetailItem()

    data class Infra(val infra: InsightDetailInfra) : InsightDetailItem()

    data class AptEnvironment(val aptEnvironment: InsightDetailAptEnvironment) : InsightDetailItem()

    data class AptFacility(val aptFacility: InsightDetailAptFacility) : InsightDetailItem()

    data class GoodNews(val goodNews: InsightDetailGoodNews) : InsightDetailItem()
}
