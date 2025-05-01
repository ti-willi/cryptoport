package com.tiwilli.cryptoport.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TwoDecimalSerializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            BigDecimal rounded = BigDecimal.valueOf(value).setScale(2, RoundingMode.UP);
            jsonGenerator.writeNumber(rounded);
        }
        else {
            jsonGenerator.writeNull();
        }
    }
}
