package com.company.error;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {
    @ApiModelProperty("Error level")
    private String level;
    @ApiModelProperty("Error property path - payment.amount.currency")
    private String path;
    @ApiModelProperty("Error message")
    private String message;
}