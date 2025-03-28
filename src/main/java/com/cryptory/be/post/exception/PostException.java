package com.cryptory.be.post.exception;

public class PostException extends ClassCastException {
    public PostException(PostErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
