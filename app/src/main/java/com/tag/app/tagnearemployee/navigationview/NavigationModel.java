package com.tag.app.tagnearemployee.navigationview;


import com.tag.app.tagnearemployee.retrofit.RestClient;

public class NavigationModel implements NavigationContract.Model {
    private final RestClient restClient;

    public NavigationModel(RestClient restClient) {
        this.restClient=restClient;
    }

    @Override
    public void destroy() {

    }
}
