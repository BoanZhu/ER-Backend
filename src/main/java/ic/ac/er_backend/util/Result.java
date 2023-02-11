package ic.ac.er_backend.util;

import lombok.Data;
//import ic.ac.er_backend.util.ResultState;

@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result(ResultState state, T data) {
        this.code = state.getCode();
        this.message = state.getMessage();
        this.data = data;
    }

    public static Result error(ResultState state, String message) {
        return new Result(state, message);
    }

    public static Result success() {
        return new Result(ResultState.SUCCESS, "");
    }

    public static Result success(Object data) {
        return new Result(ResultState.SUCCESS, data);
    }
}