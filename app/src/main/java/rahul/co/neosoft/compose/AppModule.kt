package rahul.co.neosoft.compose

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rahul.co.neosoft.compose.data.datasources.room.RoomContactDataSource
import rahul.co.neosoft.compose.data.interfaces.ContactDataSource
import rahul.co.neosoft.compose.data.interfaces.ContactDatabase
import rahul.co.neosoft.compose.data.webService.ApiHelper
import rahul.co.neosoft.compose.data.webService.ApiService
import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.interfaces.usecases.CreateContactUseCase
import rahul.co.neosoft.compose.domain.interfaces.usecases.GetAllContactsUseCase
import rahul.co.neosoft.compose.domain.repositories.ContactRepositoryImpl
import rahul.co.neosoft.compose.domain.usecases.contact.ApiHelperImpl
import rahul.co.neosoft.compose.domain.usecases.contact.CreateContact
import rahul.co.neosoft.compose.domain.usecases.contact.GetContacts
import rahul.co.neosoft.compose.other.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun providesContactDatasource(@ApplicationContext context: Context): ContactDataSource {
        return RoomContactDataSource(
            dao = Room.databaseBuilder(
                context,
                ContactDatabase::class.java,
                ContactDatabase.DATABASE_NAME
            ).build().contactDao
        )
    }

    @Provides
    @Singleton
    fun providesContactRepository(dataSource: ContactDataSource): ContactRepository {
        return ContactRepositoryImpl(contactDataSource = dataSource)
    }

    @Provides
    @Singleton
    fun providesGetContactsUseCase(repository: ContactRepository): GetAllContactsUseCase {
        return GetContacts(contactRepository = repository)
    }

    @Provides
    @Singleton
    fun providesCreateContactUseCase(repository: ContactRepository): CreateContactUseCase {
        return CreateContact(contactRepository = repository)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addNetworkInterceptor(EncryptionInterceptor())
            .build()
    }
    else{
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper


}