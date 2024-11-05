package info.imdang.data.model.user

import com.google.gson.annotations.SerializedName
import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.data.mapper.mapper
import info.imdang.domain.model.UserDto
import info.imdang.domain.model.UserItemDto

data class UserResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val isFailed: Boolean,
    val items: List<UserItemResponse>
) : DataToDomainMapper<UserDto> {

    override fun mapper(): UserDto = UserDto(
        totalCount = totalCount,
        isFailed = isFailed,
        items = items.mapper()
    )
}

data class UserItemResponse(
    val login: String,
    val id: Long,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("gravatar_id") val gravatarId: String,
    val url: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("followers_url") val followersUrl: String,
    @SerializedName("following_url") val followingUrl: String,
    @SerializedName("gists_url") val gistsUrl: String,
    @SerializedName("starred_url") val starredUrl: String,
    @SerializedName("subscriptions_url") val subscriptionsUrl: String,
    @SerializedName("organizations_url") val organizationsUrl: String,
    @SerializedName("repos_url") val reposUrl: String,
    @SerializedName("events_url") val eventsUrl: String,
    @SerializedName("received_events_url") val receivedEventsUrl: String,
    val type: String,
    @SerializedName("site_admin") val siteAdmin: Boolean,
    val score: Float
) : DataToDomainMapper<UserItemDto> {

    override fun mapper(): UserItemDto = UserItemDto(
        login = login,
        id = id,
        nodeId = nodeId,
        avatarUrl = avatarUrl,
        gravatarId = gravatarId,
        url = url,
        htmlUrl = htmlUrl,
        followersUrl = followersUrl,
        followingUrl = followingUrl,
        gistsUrl = gistsUrl,
        starredUrl = starredUrl,
        subscriptionsUrl = subscriptionsUrl,
        organizationsUrl = organizationsUrl,
        reposUrl = reposUrl,
        eventsUrl = eventsUrl,
        receivedEventsUrl = receivedEventsUrl,
        type = type,
        siteAdmin = siteAdmin,
        score = score
    )
}
