/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

/**
 *
 * @author fabzamgri
 */
public class ConfigData {
    
    private static ConfigData configData = null;
    private String name;
    
    private ConfigData() {
    name = "";
    }
    
    public static ConfigData instance() {
        if(configData == null) {
            configData = new ConfigData();
        }
        return configData;
    }
}


