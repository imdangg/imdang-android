package info.imdang.domain.model

data class UserDto(
    val totalCount: Int,
    val isFailed: Boolean,
    val items: List<UserItemDto>
)

data class UserItemDto(
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
