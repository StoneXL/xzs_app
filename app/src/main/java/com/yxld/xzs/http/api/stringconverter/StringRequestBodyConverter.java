package com.yxld.xzs.http.api.stringconverter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * 作者：Android on 2017/9/9
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class StringRequestBodyConverter  implements Converter<String, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/html;charset=UTF-8");     //text/html;charset=UTF-8    //application/json; charset=UTF-8
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    StringRequestBodyConverter() {

    }

    @Override public RequestBody convert(String value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        writer.write(value);
        writer.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}