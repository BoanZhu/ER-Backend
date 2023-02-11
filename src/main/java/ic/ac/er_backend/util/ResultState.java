package ic.ac.er_backend.util;

import lombok.Getter;

@Getter
public enum ResultState {
    SUCCESS(0, "success"),
    INTERNAL_SERVER_ERROR(1, "internal server error"),
    INVALID_ARGUMENT(2, "validation error"),
    ER_API_ERROR(3, "er api error");

    private int code;
    private String message;

    ResultState(int code, String message) {
        this.code = code;
        this.message = message;
    }
}