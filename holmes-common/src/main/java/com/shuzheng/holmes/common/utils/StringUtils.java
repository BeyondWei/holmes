/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shuzheng.holmes.common.utils;

import java.lang.reflect.Field;

public class StringUtils {
    public static final String EMPTY = "";

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(String s){
        if (isEmpty(s)) {
            return true;
        }
        return s.trim().length() == 0;
    }

    public static boolean isNotBlank(String s){
        return !isBlank(s);
    }

    /**
     * 通过反射将对象中的空字符串设置成null
     *
     * @param object
     * @return
     */
    public static Object paramToNull(Object object) {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (StringUtils.isEmpty(value.toString())) {
                    field.set(object, null);
                }
            } catch (Exception e) {
            }
        }
        return object;
    }

    /**
     * 将null的字符更改成空
     */
    public static String paramToEmpty(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        return param;
    }

    /**
     * Object 转 String
     */
    public static String objectToString(Object o) {
        return o == null ? null : o.toString();
    }
}
