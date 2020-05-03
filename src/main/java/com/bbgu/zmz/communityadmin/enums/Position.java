package com.bbgu.zmz.communityadmin.enums;

public enum Position {
    LEFT("LEFT","正文左侧"),
    RIGHT("RIGHT","正文右侧");
    private String pos;
    private String message;
    Position(String pos, String message) {
        this.message = message;
        this.pos = pos;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
