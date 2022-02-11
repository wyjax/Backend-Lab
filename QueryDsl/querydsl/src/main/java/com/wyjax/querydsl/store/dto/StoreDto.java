package com.wyjax.querydsl.store.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString(of = {"name", "menuName"})
public class StoreDto {
    private String name;
    private String menuName;

    @QueryProjection
    public StoreDto(String name, String menuName) {
        this.name = name + " 님";
        this.menuName = menuName + " 님";
    }
}
