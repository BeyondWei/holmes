package com.shuzheng.holmes.common.utils;

import com.shuzheng.holmes.common.dto.FlumeData;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.flume.source.avro.AvroFlumeEvent;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class FlumeAvroUtils {

    private FlumeAvroUtils() {

    }

    /**
     * 得到flume中的
     */
    public static FlumeData getFlumeData(String str) {

        String input = str;
        String dataStr = "";
        if (str.contains("�")) {
            input = str.replaceAll("�", "l");
        }
        FlumeData flumeData = new FlumeData();
        AvroFlumeEvent result = null;
        ByteBuffer data = null;
        Map<CharSequence, CharSequence> map = null;
//        ByteArrayInputStream bytes = new ByteArrayInputStream(input.getBytes());
        //解码还原为flumeEvent
        SpecificDatumReader<AvroFlumeEvent> reader = new SpecificDatumReader<AvroFlumeEvent>(AvroFlumeEvent.class);
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(input.getBytes(), null);
        try {
            result = reader.read(null, decoder);
            map = result.getHeaders();
            data = result.getBody();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, String> hashMap = new HashMap<>();
        for (Map.Entry<CharSequence, CharSequence> entry : map.entrySet()) {
            hashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        flumeData.setHeaderMap(hashMap);
        if (str.contains("�")) {
            flumeData.setMsg(str.substring(str.indexOf("�") + 2));
        } else {
            flumeData.setMsg(new String(data.array()));
        }
        return flumeData;
    }

}
