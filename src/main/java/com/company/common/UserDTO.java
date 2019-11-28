package com.company.common;

import java.io.Serializable;

public interface UserDTO extends Serializable {
    public String getUsername();
    public int getUid();
    public String getPassword();

}
