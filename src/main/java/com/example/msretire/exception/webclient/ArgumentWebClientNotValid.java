package com.example.msretire.exception.webclient;

import com.example.msretire.util.I18AbleException;

/**
 * The type Argument web client not valid.
 */
public class ArgumentWebClientNotValid extends I18AbleException {
    /**
     * Instantiates a new Argument web client not valid.
     *
     * @param key  the key
     * @param args the args
     */
    public ArgumentWebClientNotValid(String key, Object... args) {
        super(key, args);
    }
}