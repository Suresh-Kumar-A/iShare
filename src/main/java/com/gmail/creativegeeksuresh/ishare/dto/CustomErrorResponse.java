package com.gmail.creativegeeksuresh.ishare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {
  private int errorCode;
  private String errorMessage;
  private String errorDescription;

}
