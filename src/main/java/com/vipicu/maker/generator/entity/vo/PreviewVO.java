package com.vipicu.maker.generator.entity.vo;

import com.vipicu.maker.generator.entity.po.RenderResult;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 预览视图
 *
 * @author oohmygosh
 * @since 2022/12/22
 */
@Schema(description = "生成预览")
public record PreviewVO(List<RenderResult> renderResults) {
}
