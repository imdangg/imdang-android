package info.imdang.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.data.datasource.remote.AptComplexRemoteDataSource
import info.imdang.data.datasource.remote.AuthRemoteDataSource
import info.imdang.data.datasource.remote.CouponRemoteDataSource
import info.imdang.data.datasource.remote.DistrictRemoteDataSource
import info.imdang.data.datasource.remote.ExchangeRemoteDataSource
import info.imdang.data.datasource.remote.GoogleRemoteDataSource
import info.imdang.data.datasource.remote.InsightRemoteDataSource
import info.imdang.data.datasource.remote.MyInsightRemoteDataSource
import info.imdang.data.datasource.remote.MyExchangeRemoteDataSource
import info.imdang.data.datasource.remote.MyPageRemoteDataSource
import info.imdang.data.datasource.remote.NotificationRemoteDataSource
import info.imdang.remote.datasource.AptComplexRemoteDataSourceImpl
import info.imdang.remote.datasource.AuthRemoteDataSourceImpl
import info.imdang.remote.datasource.CouponRemoteDataSourceImpl
import info.imdang.remote.datasource.DistrictRemoteDataSourceImpl
import info.imdang.remote.datasource.ExchangeRemoteDataSourceImpl
import info.imdang.remote.datasource.GoogleRemoteDataSourceImpl
import info.imdang.remote.datasource.InsightRemoteDataSourceImpl
import info.imdang.remote.datasource.MyInsightRemoteDataSourceImpl
import info.imdang.remote.datasource.MyExchangeRemoteDataSourceImpl
import info.imdang.remote.datasource.MyPageRemoteDataSourceImpl
import info.imdang.remote.datasource.NotificationRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDatasource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindGoogleRemoteDatasource(
        googleRemoteDataSourceImpl: GoogleRemoteDataSourceImpl
    ): GoogleRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindInsightRemoteDatasource(
        insightRemoteDataSourceImpl: InsightRemoteDataSourceImpl
    ): InsightRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAptComplexRemoteDatasource(
        aptComplexRemoteDataSourceImpl: AptComplexRemoteDataSourceImpl
    ): AptComplexRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMyInsightRemoteDatasource(
        myInsightRemoteDataSourceImpl: MyInsightRemoteDataSourceImpl
    ): MyInsightRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCouponRemoteDatasource(
        couponRemoteDataSourceImpl: CouponRemoteDataSourceImpl
    ): CouponRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindExchangeRemoteDatasource(
        exchangeRemoteDataSourceImpl: ExchangeRemoteDataSourceImpl
    ): ExchangeRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMyExchangeRemoteDatasource(
        myExchangeRemoteDataSourceImpl: MyExchangeRemoteDataSourceImpl
    ): MyExchangeRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindDistrictRemoteDatasource(
        districtRemoteDataSourceImpl: DistrictRemoteDataSourceImpl
    ): DistrictRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMyPageRemoteDatasource(
        myPageRemoteDataSourceImpl: MyPageRemoteDataSourceImpl
    ): MyPageRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindNotificationRemoteDatasource(
        notificationRemoteDataSourceImpl: NotificationRemoteDataSourceImpl
    ): NotificationRemoteDataSource
}
