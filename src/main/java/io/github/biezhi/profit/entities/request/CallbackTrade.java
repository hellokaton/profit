package io.github.biezhi.profit.entities.request;

import lombok.Data;

@Data
public class CallbackTrade {

    private String  client_id;
    private String  id;
    private Long    kdt_id;
    private String  kdt_name;
    private Integer mode;
    private String  msg;
    private Integer sendCount;
    private String  status;
    private Boolean test;
    private String  type;

}
