package com.company.server.model;

import com.company.common.FileDTO;

import javax.persistence.*;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//@NamedQueries({
//        @NamedQuery(
//                name = "findAccountByName",
//                query = "SELECT fm FROM FilesEntity fm WHERE fm.name LIKE :name"
////                lockMode = LockModeType.OPTIMISTIC
//        )
//}
//)



@NamedQueries(
        {       @NamedQuery(
                    name = "findFile",
                    query = "SELECT fn FROM FilesEntity fn WHERE fn.name LIKE :fileName"
    //                lockMode = LockModeType.OPTIMISTIC
                ),
                @NamedQuery(
                        name = "getAllFiles",
                        query = "SELECT allfiles FROM FilesEntity allfiles"
//                        lockMode = LockModeType.OPTIMISTIC
                ),
                @NamedQuery(
                        name = "deleteFile",
                        query = "DELETE FROM FilesEntity fn WHERE fn.name LIKE :name"
                )
        }
)





@Entity
@Table(name = "files", schema = "filesys")
public class FilesEntity implements FileDTO{
    private String name;
    private String owner;
    private long size;
    private int idFile;
    private int permission;

    public FilesEntity(String name, String owner, long size, int permission) {
        this.name = name;
        this.owner = owner;
        this.size = size;
        this.permission = permission;
    }

    public FilesEntity(){

    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "size")
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Basic
    @Column(name = "permission")
    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    @Id
    @Column(name = "idFile")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getIdFile() {
        return idFile;
    }

    public void setIdFile(int idFile) {
        this.idFile = idFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilesEntity that = (FilesEntity) o;
        return idFile == that.idFile &&
                Objects.equals(name, that.name) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, size, idFile);
    }
}
