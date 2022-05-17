package com.example.demo.unitOfWork;

public interface IUnitOfWork<T> {
    String INSERT ="INSERT";
    String DELETE="DELETE";
    String MODIFY="MODIFY";

    void registerNew(T entity);

    void registerModified(T entity);

    void registerDelete(T entity);

    void commit();
}
