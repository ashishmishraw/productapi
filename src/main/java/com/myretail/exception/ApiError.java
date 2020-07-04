package com.myretail.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;


@Validated
@NoArgsConstructor
public class ApiError {

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("message")
    private String message = null;


    public ApiError(String description, String message) {
        this.description = description;
        this.message = message;
    }


    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getDescription() {
        return this.description ;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Error {\n");

        sb.append("    code: ").append(toIndentedString(description)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("}");
        return sb.toString();
    }


    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
