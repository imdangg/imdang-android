# Imdang
-keep class info.imdang.imdang.model.** { *; }
-keep class info.imdang.domain.model.** { *; }
-keep class info.imdang.data.model.** { *; }

# KAKAO
-keep class com.kakao.sdk.**.model.* { <fields>; }

# https://github.com/square/okhttp/pull/6792
-dontwarn org.bouncycastle.jsse.**
-dontwarn org.conscrypt.*
-dontwarn org.openjsse.**
