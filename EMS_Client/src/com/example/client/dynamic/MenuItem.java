package com.example.client.dynamic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuItem {

    private int id;

    private String name;

    private String url;

    private String method;

    private String menu;

    private String successMenu;

	private boolean autoExecute;

	private List<InputField> fields;

    public MenuItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<InputField> getFields() {
        return fields;
    }

    public void setFields(List<InputField> fields) {
        this.fields = fields;
    }
    public String getSuccessMenu() {
        return successMenu;
    }

    public void setSuccessMenu(String successMenu) {
        this.successMenu = successMenu;
    }

    public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public boolean isAutoExecute() {
		return autoExecute;
	}

	public void setAutoExecute(boolean autoExecute) {
		this.autoExecute = autoExecute;
	}

}