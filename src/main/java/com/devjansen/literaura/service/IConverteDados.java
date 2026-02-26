package com.devjansen.literaura.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
