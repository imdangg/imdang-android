package info.imdang.data.datasource.remote

interface CouponRemoteDataSource {

    suspend fun getCouponCount(): Int
}
