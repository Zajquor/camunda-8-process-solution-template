package org.example.camunda.process.solution.service;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Convert;
import org.hibersap.annotations.Parameter;
import org.hibersap.conversion.CharConverter;

@BapiStructure
public class BapiRet2 {

    @Parameter("TYPE")
    @Convert(converter = CharConverter.class)
    private char type;

    @Parameter("ID")
    private String id;

    @Parameter("NUMBER")
    private String number;

    @Parameter("MESSAGE")
    private String message;

    public char getType() { return this.type; }

    public String getId() { return this.id; }

    public String getNumber() { return this.number; }

    public String getMessage() { return this.message; }
}
