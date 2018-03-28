package io.ermdev.classify.domain;

public class Client {

    private String clientId;
    private String secret;
    private String redirectUri;

    public Client() {
        clientId = "";
        secret = "";
        redirectUri = "";
    }

    public Client(String clientId, String secret, String redirectUri) {
        this.clientId = clientId;
        this.secret = secret;
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
