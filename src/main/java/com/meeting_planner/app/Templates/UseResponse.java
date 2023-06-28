package com.meeting_planner.app.Templates;

import lombok.Data;

@Data
public class UseResponse<T> {

  private T payload;
  private String error;
  private T message;
}
