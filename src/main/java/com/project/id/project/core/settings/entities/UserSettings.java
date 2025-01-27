package com.project.id.project.core.settings.entities;

import com.project.id.project.core.Id;
import com.project.id.project.core.utils.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class UserSettings {
    private Id id;
    private String avatar;
    private String invocation;
    private String name;
    private String surname;
    private String locality;
    private String timeZone;

    public UserSettings() {
    }

    public UserSettings(Id id, String avatar,
                        String invocation,
                        String name, String surname,
                        String locality, String timeZone) {
        this.id = id;
        this.avatar = avatar;
        this.invocation = invocation;
        this.name = name;
        this.surname = surname;
        this.locality = locality;
        this.timeZone = timeZone;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInvocation() {
        return invocation;
    }

    public void setInvocation(String invocation) {
        this.invocation = invocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
