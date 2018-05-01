package com.sainstest.app.service.adapters;

public interface Adapter<T1, T2> {

    T1 fromDTO(T2 dto);
}
