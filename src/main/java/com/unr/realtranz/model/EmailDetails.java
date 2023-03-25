package com.unr.realtranz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:27-01-2023 01:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}