package dagger.network;

import com.google.gson.*;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;
import java.util.Date;

/**
 * Created by OldMan on 18.06.2017.
 */

@Module(library = true, complete = false)
public class RetrofitModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.baseUrl("http://77.247.172.2:10080/").build();
//        return builder.baseUrl("http://192.168.50.179:8080/").build();
//        return builder.baseUrl("http://localhost:8090/").build();
    }

//    @Provides
//    @Singleton
//    Cache provideHttpCache(Application application) {
//        int cacheSize = 10 * 1024 * 1024;
//        return new Cache(application.getCacheDir(), cacheSize);
//    }

    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofitBuilder(Converter.Factory converterFactory) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory).client(httpClient.build());

    }

    @Provides
    @Singleton
    public Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .setLenient()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {
                    if (json.isJsonNull()) {
                        return null;
                    }
                    if (!json.isJsonPrimitive() || !json.getAsJsonPrimitive().isNumber()) {
                        throw new JsonParseException("Invalid type for Date, must be a numeric timestamp!");
                    }

                    return new Date(json.getAsLong());
                })
                .create();

//             .registerTypeAdapter(Date.class, new DateDeserializer()).create();
    }

    private static class CustomFieldNamingPolicy implements FieldNamingStrategy {

        @Override
        public String translateName(java.lang.reflect.Field f) {
            String name = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(f);
            name = name.substring(2, name.length()).toLowerCase();
            return name;
        }
    }
}
