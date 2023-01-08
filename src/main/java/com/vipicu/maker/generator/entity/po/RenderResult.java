package com.vipicu.maker.generator.entity.po;


import com.vipicu.maker.generator.enums.RenderType;

public record RenderResult(
        String content,
        String path,
        RenderType type
) {
}