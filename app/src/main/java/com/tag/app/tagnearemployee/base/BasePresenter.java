package com.tag.app.tagnearemployee.base;

public interface BasePresenter<T> {
    void setView(T view);

    void clearView();
}
