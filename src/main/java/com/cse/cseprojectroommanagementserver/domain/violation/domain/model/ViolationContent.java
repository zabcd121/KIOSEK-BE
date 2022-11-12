package com.cse.cseprojectroommanagementserver.domain.violation.domain.model;


public enum ViolationContent {
    NO_SHOW("미사용"),
    NO_RETURNED("미반납");

    private final String content;

    private ViolationContent(String content) {
        this.content = content;
    }

    public String getContent() { return content; }

}
