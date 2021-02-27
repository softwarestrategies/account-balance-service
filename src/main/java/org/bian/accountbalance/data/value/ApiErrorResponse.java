package org.bian.accountbalance.data.value;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiErrorResponse implements Serializable {

    private int errorCode = 0;
    private String errorMessage;

    public ApiErrorResponse() { }

    public ApiErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ApiErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
