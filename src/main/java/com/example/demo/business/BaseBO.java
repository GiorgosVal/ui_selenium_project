package com.example.demo.business;

import com.example.demo.factories.WebDriverFactory;

public class BaseBO {

    public BaseBO navigateTo(String url) {
        WebDriverFactory.getDriver().get(url);
        return this;
    }


}
