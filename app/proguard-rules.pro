# Keep this file empty for now if you are not using any specific libraries
# that require ProGuard rules. However, it's good practice to have it.

# Rules for Room (Database)
-keep class androidx.room.paging.LimitOffsetDataSource

# Rules for GSON (JSON serialization)
-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken

# Keep your app's data models (so GSON and Room can find them)
-keep class com.achievemeaalk.freedjf.data.model.** { *; }

# Keep this rule if you use Coroutines
-keepclassmembers class kotlinx.coroutines.internal.MainDispatcherFactory {
    boolean performsPreDispatch;
}