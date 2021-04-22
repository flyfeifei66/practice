package com.zhaojufei.practice.web.service;

public interface ICalService {
    default int add(int i, int j) {
        return i + j;
    }
}
