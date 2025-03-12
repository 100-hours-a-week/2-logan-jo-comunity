package KBT2.comunity.back.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private List<String> details;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.details = Collections.emptyList();
    }
}
