package com.zzu.staff.achievement.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadReturn {
    private String url;
    private int code;
}

