package com.company.server.model;

import javax.persistence.Entity;
import javax.persistence.LockModeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
        @NamedQuery(
                name = "findFile",
                query = "SELECT fil FROM File fil WHERE fil.files.name LIKE :fileName",
                lockMode = LockModeType.OPTIMISTIC
        )

}
)

@Entity(name = "File")
public class File {
}
