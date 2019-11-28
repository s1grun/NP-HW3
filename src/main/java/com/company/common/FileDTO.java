package com.company.common;

import com.company.server.model.FilesEntity;
import com.company.server.model.UserEntity;

import java.io.Serializable;

public interface FileDTO extends Serializable {
    public String getOwner();
    public String getName();
}
