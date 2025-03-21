package com.example.repo_midterm.DTO.Response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//Đặng Bá Hiền 22110320
public class APIResponse<T> {
    private int code = 1000;
    private String message = "Success";
    private HttpStatus httpStatus = HttpStatus.OK;
    private T result;
}