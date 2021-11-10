package ar.com.wolox.android.bootstrap.di

import android.content.Context
import android.content.SharedPreferences
import ar.com.wolox.android.bootstrap.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            context.getString(R.string.default_shared_preferences_name, context.packageName),
            Context.MODE_PRIVATE
        )
}
