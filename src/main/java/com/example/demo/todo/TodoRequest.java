package com.example.demo.todo;

public class TodoRequest {
	private String title;
    private String start; // "yyyy-MM-dd" 형식
    private String end;   // 현재는 사용하지 않지만, 추후 확장을 위해 추가 가능

    // getters & setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
}
