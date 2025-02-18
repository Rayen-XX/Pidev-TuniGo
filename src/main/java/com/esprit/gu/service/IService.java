package com.esprit.gu.service;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.util.List;

public interface IService<T> {
    void ajouter(T var1);

    void modifier(T var1);

    void supprimer(int var1);

    List<T> getAll(T var1);

    T getOne(int var1);
}
