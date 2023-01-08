package com.vipicu.maker.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.config.GeneratorAutoConfig;
import com.vipicu.maker.generator.core.api.ApiController;
import com.vipicu.maker.generator.core.api.PageParam;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.core.validation.Update;
import com.vipicu.maker.generator.entity.SysGeneratorTemplate;
import com.vipicu.maker.generator.service.ISysGeneratorTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 生成器模板 前端控制器
 *
 * @author oohmygosh
 * @since 2022-12-19
 */
@Tag(name = "生成器模板")
@RestController
@AllArgsConstructor
@RequestMapping(GeneratorAutoConfig.API_PREFIX + "/template")
public class SysGeneratorTemplateController extends ApiController {
    private ISysGeneratorTemplateService sysGeneratorTemplateService;

    @Operation(summary = "分页列表")
    @PostMapping("/page")
    public Page<SysGeneratorTemplate> getPage(@RequestBody PageParam<SysGeneratorTemplate> dto) {
        return sysGeneratorTemplateService.page(dto.page(), dto.getData());
    }


    @Operation(summary = "查询 id 信息")
    @GetMapping("/get")
    public SysGeneratorTemplate get(@RequestParam Long id) {
        return sysGeneratorTemplateService.getById(id);
    }

    @Operation(summary = "查询所有")
    @GetMapping("/getAll")
    public List<SysGeneratorTemplate> getAll() {
        return sysGeneratorTemplateService.list();
    }

    @Operation(summary = "根据 id 修改信息")
    @PostMapping("/update")
    public boolean update(@Validated(Update.class) @RequestBody SysGeneratorTemplate sysGeneratorTemplate) {
        return sysGeneratorTemplateService.updateById(sysGeneratorTemplate);
    }

    @Operation(summary = "创建添加")
    @PostMapping("/create")
    public boolean create(@Validated(Create.class) @RequestBody SysGeneratorTemplate sysGeneratorTemplate) {
        return sysGeneratorTemplateService.save(sysGeneratorTemplate);
    }

    @Operation(summary = "根据 ids 删除")
    @PostMapping("/delete")
    public boolean delete(@NotEmpty @RequestBody List<Long> ids) {
        return sysGeneratorTemplateService.removeByIds(ids);
    }
}
