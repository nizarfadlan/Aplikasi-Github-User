-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-obfuscationdictionary dictionary.txt
-classobfuscationdictionary dictionary.txt
-packageobfuscationdictionary dictionary.txt

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

-keep class com.nizarfadlan.core.data.datasource.** { *; }
-keepclassmembers class com.nizarfadlan.core.data.datasource.** { *; }

-keep class com.google.android.play.core.splitcompat.** { *; }
-keep class com.google.android.play.core.splitinstall.** { *; }

-keep class androidx.** { *; }
-dontwarn androidx.**
-keep class com.google.android.** { *; }
-dontwarn com.google.android.**

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class com.nizarfadlan.core.domain.model.** { *; }
