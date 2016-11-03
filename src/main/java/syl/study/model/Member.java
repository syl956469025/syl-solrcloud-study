package syl.study.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mtime on 2016/10/16.
 */
public class Member {

    long id;

    String username;

    int age;

    LocalDateTime birthday;

    List<FilmType> likeFilmTypes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public List<FilmType> getLikeFilmTypes() {
        return likeFilmTypes;
    }

    public void setLikeFilmTypes(List<FilmType> likeFilmTypes) {
        this.likeFilmTypes = likeFilmTypes;
    }
}
