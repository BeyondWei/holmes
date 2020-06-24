package com.shuzheng.holmes.common.utils;

import com.shuzheng.holmes.common.dto.FlumeData;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.flume.source.avro.AvroFlumeEvent;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class FlumeAvroUtils {

    private FlumeAvroUtils() {

    }

    /**
     * 得到flume中的
     */
    public static FlumeData getFlumeData(String input) {
        FlumeData flumeData = new FlumeData();
        AvroFlumeEvent result = null;
        ByteBuffer data = null;
        Map<CharSequence, CharSequence> map = null;
        byte[] bytes = input.getBytes();
        //解码还原为flumeEvent
        SpecificDatumReader<AvroFlumeEvent> reader = new SpecificDatumReader<AvroFlumeEvent>(AvroFlumeEvent.class);
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
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
        flumeData.setMsg(new String(data.array()));
        return flumeData;
    }
}
