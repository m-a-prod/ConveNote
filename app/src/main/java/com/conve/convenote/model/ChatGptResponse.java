package com.conve.convenote.model;


import java.util.Arrays;

public class ChatGptResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private ResponseChoice[] choices;
    private ResponseUsage[] usages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ResponseChoice[] getChoices() {
        return choices;
    }

    public void setChoices(ResponseChoice[] choices) {
        this.choices = choices;
    }

    public ResponseUsage[] getUsages() {
        return usages;
    }

    public void setUsages(ResponseUsage[] usages) {
        this.usages = usages;
    }

    @Override
    public String toString() {
        return "ChatGptResponse(id=" + id + ", object=" + object + ", created=" + created + ", model=" + model + ", choices=" + Arrays.toString(choices) + ", usages=" + Arrays.toString(usages) + ")";
    }
}
