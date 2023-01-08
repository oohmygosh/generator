package com.vipicu.maker.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.config.GeneratorAutoConfig;
import com.vipicu.maker.generator.core.api.ApiController;
import com.vipicu.maker.generator.core.api.PageParam;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.core.validation.Update;
import com.vipicu.maker.generator.entity.SysGeneratorDb;
import com.vipicu.maker.generator.service.ISysGeneratorDbService;
import com.vipicu.maker.generator.service.ISysGeneratorStrategyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代码生成目标数据库 前端控制器
 *
 * @author oohmygosh
 * @since 2022-12-14
 */
@Tag(name = "代码生成数据源")
@RestController
@AllArgsConstructor
@RequestMapping(GeneratorAutoConfig.API_PREFIX + "/db")
public class SysGeneratorDbController extends ApiController {
    private ISysGeneratorDbService sysGeneratorDbService;

    @Operation(summary = "分页列表")
    @PostMapping("/page")
    public Page<SysGeneratorDb> getPage(@RequestBody PageParam<SysGeneratorDb> dto) {
        return sysGeneratorDbService.page(dto.page(), dto.getData());
    }

    @Operation(summary = "查询 id 信息")
    @GetMapping("/get")
    public SysGeneratorDb get(@RequestParam Long id) {
        return sysGeneratorDbService.getById(id);
    }

    @Operation(summary = "根据 id 修改信息")
    @PostMapping("/update")
    public boolean update(@Validated(Update.class) @RequestBody SysGeneratorDb sysGeneratorDb) {
        return sysGeneratorDbService.updateById(sysGeneratorDb);
    }

    @Operation(summary = "创建添加")
    @PostMapping("/create")
    public boolean create(@Validated(Create.class) @RequestBody SysGeneratorDb sysGeneratorDb) {
        return sysGeneratorDbService.save(sysGeneratorDb);
    }

    @Operation(summary = "根据 ids 删除")
    @PostMapping("/delete")
    public boolean delete(@NotEmpty @RequestBody List<Long> ids) {
        return sysGeneratorDbService.removeByIds(ids);
    }

}
