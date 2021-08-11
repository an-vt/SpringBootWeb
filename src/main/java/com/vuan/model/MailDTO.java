package com.vuan.model;

import java.util.List;

import lombok.Data;

@Data
public class MailDTO {
	private String mailFrom;
	private String mailTo;
	private String mailCc;
	private String mailBcc;
	private String mailSubject;
	private String mailContent;
	private String contentType;
	private List<Object> attachments;
 
    public MailDTO() {
        contentType = "text/plain";
    }
}
