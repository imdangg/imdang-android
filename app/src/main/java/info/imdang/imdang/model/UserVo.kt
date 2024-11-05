package info.imdang.imdang.model

import info.imdang.domain.model.UserDto
import info.imdang.domain.model.UserItemDto

data class UserVo(
    val totalCount: Int,
    val isFailed: Boolean,
    val items: List<UserItemVo>
) {
    companion object {
        fun init(): UserVo = UserVo(
            totalCount = 0,
            isFailed = false,
            items = emptyList()
        )
    }
}

data class UserItemVo(
    val login: String,
    val id: Long,
    val nodeId: String,
    val avatarUrl: String,
    val gravatarId: String,
    val url: String,
    val htmlUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val organizationsUrl: String,
    val reposUrl: String,
    val eventsUrl: String,
    val receivedEventsUrl: String,
    val type: String,
    val siteAdmin: Boolean,
    val score: Float
)

fun UserDto.mapper(): UserVo = UserVo(
    totalCount = totalCount,
    isFailed = isFailed,
    items = items.mapper()
)

fun UserItemDto.mapper(): UserItemVo = UserItemVo(
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

fun List<UserItemDto>.mapper(): List<UserItemVo> = map { it.mapper() }
