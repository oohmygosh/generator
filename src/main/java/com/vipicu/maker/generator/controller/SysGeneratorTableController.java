package com.vipicu.maker.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.config.GeneratorAutoConfig;
import com.vipicu.maker.generator.core.api.ApiController;
import com.vipicu.maker.generator.core.api.PageParam;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.core.validation.Update;
import com.vipicu.maker.generator.entity.SysGeneratorTable;
import com.vipicu.maker.generator.service.ISysGeneratorTableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代码生成器表控制器
 *
 * @author oohmygosh
 * @since 2022/12/15
 */
@Tag(name = "代码生成表管理")
@RestController
@AllArgsConstructor
@RequestMapping(GeneratorAutoConfig.API_PREFIX + "/table")
public class SysGeneratorTableController extends ApiController {

    private final ISysGeneratorTableService sysGeneratorTableService;

    @Operation(summary = "分页列表")
    @PostMapping("/page")
    public Page<SysGeneratorTable> getPage(@RequestBody PageParam<SysGeneratorTable> dto){
        return sysGeneratorTableService.page(dto.page(),dto.getData());
    }

    @Operation(summary = "根据 id 修改信息")
    @PostMapping("/update")
    public boolean update(@Validated(Update.class) @RequestBody SysGeneratorTable sysGeneratorTable){
        return sysGeneratorTableService.updateById(sysGeneratorTable);
    }

    @Operation(summary = "创建添加")
    @PostMapping("/create")
    public boolean create(@Validated(Create.class) @RequestBody SysGeneratorTable sysGeneratorTable){
        return sysGeneratorTableService.save(sysGeneratorTable);
    }

    @Operation(summary = "根据 ids 删除")
    @PostMapping("/delete")
    public boolean delete(@NotEmpty @RequestBody List<Long> ids){
        return sysGeneratorTableService.removeByIds(ids);
    }

    @Operation(summary = "获取数据源表")
    @GetMapping("/get")
    public List<SysGeneratorTable> get(@RequestParam Long id) {
        return sysGeneratorTableService.fetchAllTable(id);
    }

}
