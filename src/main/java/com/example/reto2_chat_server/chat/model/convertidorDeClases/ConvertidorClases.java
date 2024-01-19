package com.example.reto2_chat_server.chat.model.convertidorDeClases;

import org.springframework.beans.BeanUtils;

public class ConvertidorClases {

    public static <T, U> U convertirClases(T origen, Class<U> claseDestino) {
        try {
            U destino = claseDestino.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(origen, destino);
            return destino;
        } catch (Exception e) {
            // Manejar la excepción según tus necesidades
            throw new RuntimeException(
                "Error al convertir de " + origen.getClass().getSimpleName() +
                " a " + claseDestino.getSimpleName(), e
            );
        }
    }
}

