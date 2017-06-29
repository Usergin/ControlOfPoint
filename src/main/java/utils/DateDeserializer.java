package utils;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by oldman on 28.06.17.
 */
public class DateDeserializer implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        Long date = element.getAsJsonPrimitive().getAsLong();

        SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date1 = new Date(date);
        newFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            return newFormatter.parse(String.valueOf(date));
        } catch (ParseException exp) {
            System.err.println("Failed to parse Date:" + exp);
            return null;
        }
//        System.out.println(newFormatter.format(date1));
//        return new Date(date);

    }
}