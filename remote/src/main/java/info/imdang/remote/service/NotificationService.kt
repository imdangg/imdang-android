package info.imdang.remote.service

import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.notification.NotificationResponse
import info.imdang.domain.model.notification.NotificationDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface NotificationService {

    /** 알림 목록 조회 */
    @GET("notifications/{checked}")
    suspend fun getNotifications(
        @Path("checked") checked: String,
        @Query("pageNumber") page: Int?,
        @Query("pageSize") size: Int?,
        @Query("direction") direction: String?,
        @Query("properties") properties: List<String>?
    ): PagingResponse<NotificationResponse, NotificationDto>
}
