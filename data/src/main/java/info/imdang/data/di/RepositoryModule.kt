package info.imdang.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.data.repository.AptComplexRepositoryImpl
import info.imdang.data.repository.AuthRepositoryImpl
import info.imdang.data.repository.CouponRepositoryImpl
import info.imdang.data.repository.DistrictRepositoryImpl
import info.imdang.data.repository.ExchangeRepositoryImpl
import info.imdang.data.repository.GoogleRepositoryImpl
import info.imdang.data.repository.HomeRepositoryImpl
import info.imdang.data.repository.InsightRepositoryImpl
import info.imdang.data.repository.MyCouponRepositoryImpl
import info.imdang.data.repository.MyInsightRepositoryImpl
import info.imdang.data.repository.MyExchangeRepositoryImpl
import info.imdang.data.repository.MyPageRepositoryImpl
import info.imdang.data.repository.NotificationRepositoryImpl
import info.imdang.domain.repository.AptComplexRepository
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.repository.CouponRepository
import info.imdang.domain.repository.DistrictRepository
import info.imdang.domain.repository.ExchangeRepository
import info.imdang.domain.repository.GoogleRepository
import info.imdang.domain.repository.HomeRepository
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.MyCouponRepository
import info.imdang.domain.repository.MyInsightRepository
import info.imdang.domain.repository.MyExchangeRepository
import info.imdang.domain.repository.MyPageRepository
import info.imdang.domain.repository.NotificationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGoogleRepository(
        googleRepositoryImpl: GoogleRepositoryImpl
    ): GoogleRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindInsightRepository(
        insightRepositoryImpl: InsightRepositoryImpl
    ): InsightRepository

    @Binds
    @Singleton
    abstract fun bindAptComplexRepository(
        aptComplexRepositoryImpl: AptComplexRepositoryImpl
    ): AptComplexRepository

    @Binds
    @Singleton
    abstract fun bindMyInsightRepository(
        myInsightRepositoryImpl: MyInsightRepositoryImpl
    ): MyInsightRepository

    @Binds
    @Singleton
    abstract fun bindCouponRepository(
        couponRepositoryImpl: CouponRepositoryImpl
    ): CouponRepository

    @Binds
    @Singleton
    abstract fun bindMyCouponRepository(
        myCouponRepositoryImpl: MyCouponRepositoryImpl
    ): MyCouponRepository

    @Binds
    @Singleton
    abstract fun bindExchangeRepository(
        exchangeRepositoryImpl: ExchangeRepositoryImpl
    ): ExchangeRepository

    @Binds
    @Singleton
    abstract fun bindMyExchangeRepository(
        myExchangeRepositoryImpl: MyExchangeRepositoryImpl
    ): MyExchangeRepository

    @Binds
    @Singleton
    abstract fun bindDistrictRepository(
        districtRepositoryImpl: DistrictRepositoryImpl
    ): DistrictRepository

    @Binds
    @Singleton
    abstract fun bindMyPageRepository(
        myPageRepositoryImpl: MyPageRepositoryImpl
    ): MyPageRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}
