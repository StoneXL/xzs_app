package com.yxld.xzs.http.api.stringconverter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 作者：Android on 2017/9/9
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}