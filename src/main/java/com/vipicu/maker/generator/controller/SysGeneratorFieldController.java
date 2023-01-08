package com.vipicu.maker.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.config.GeneratorAutoConfig;
import com.vipicu.maker.generator.core.api.ApiController;
import com.vipicu.maker.generator.core.api.PageParam;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.core.validation.Update;
import com.vipicu.maker.generator.entity.SysGeneratorField;
import com.vipicu.maker.generator.service.ISysGeneratorFieldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代码生成表字段 前端控制器
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
@Tag(name = "代码生成表字段")
@RestController
@AllArgsConstructor
@RequestMapping(GeneratorAutoConfig.API_PREFIX + "/field")
public class SysGeneratorFieldController extends ApiController {
    private ISysGeneratorFieldService sysGeneratorFieldService;

    @Operation(summary = "分页列表")
    @PostMapping("/page")
    public Page<SysGeneratorField> getPage(@RequestBody PageParam<SysGeneratorField> dto) {
        return sysGeneratorFieldService.page(dto.page(), dto.getData());
    }

    @Operation(summary = "查询 id 信息")
    @GetMapping("/get")
    public SysGeneratorField get(@RequestParam Long id) {
        return sysGeneratorFieldService.getById(id);
    }

    @Operation(summary = "查询表字段")
    @GetMapping("/fetchTableField")
    public List<SysGeneratorField> fetchTableField(@RequestParam Long id) {
        return sysGeneratorFieldService.fetchTableField(id);
    }

    @Operation(summary = "根据 id 修改信息")
    @PostMapping("/update")
    public boolean update(@Validated(Update.class) @RequestBody SysGeneratorField sysGeneratorField) {
        return sysGeneratorFieldService.updateById(sysGeneratorField);
    }

    @Operation(summary = "创建添加")
    @PostMapping("/create")
    public boolean create(@Validated(Create.class) @RequestBody SysGeneratorField sysGeneratorField) {
        return sysGeneratorFieldService.save(sysGeneratorField);
    }

    @Operation(summary = "根据 ids 删除")
    @PostMapping("/delete")
    public boolean delete(@NotEmpty @RequestBody List<Long> ids) {
        return sysGeneratorFieldService.removeByIds(ids);
    }
}
