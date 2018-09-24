package com.beuwa.redwine.sensor.utils;

import com.beuwa.redwine.core.config.PropertiesFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignerTest {
    @InjectMocks
    Signer signer;

    @Mock
    PropertiesFacade propertiesFacade;

    @Test
    void encode() throws Exception {
        when(propertiesFacade.getApiSecret()).thenReturn("SECRET");

        String hex = signer.encode("Hello", " ",  10000000,"World");
        assertEquals(
                "A11F0A6C3908F30438BBE69A6EFBA61AC0F9C2F8F09789F76CD81C576E29027B",
                hex);
    }

    @Test
    void bytesToHex() {
        byte[] bytes = "Hello world".getBytes(StandardCharsets.UTF_8);
        String hex = signer.bytesToHex(bytes);

        assertEquals("48656C6C6F20776F726C64", hex);
    }


}