package dagger.network;

import dagger.Module;
import dagger.Provides;
import data.remote.ServerApi;
import retrofit2.Retrofit;

import javax.inject.Singleton;

/**
 * Created by OldMan on 18.06.2017.
 */
@Module(library = true, complete = false,includes = {RetrofitModule.class})
public class ServerApiModule {
    @Provides
    @Singleton
    public ServerApi provideServerApi(Retrofit retrofit) {
        return retrofit.create(ServerApi.class);
    }
}