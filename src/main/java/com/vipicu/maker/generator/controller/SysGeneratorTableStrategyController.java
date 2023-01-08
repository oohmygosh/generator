package com.vipicu.maker.generator.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.config.GeneratorAutoConfig;
import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.core.api.ApiController;
import com.vipicu.maker.generator.core.api.PageParam;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.core.validation.Update;
import com.vipicu.maker.generator.entity.TableStrategy;
import com.vipicu.maker.generator.service.ISysGeneratorTableStrategyService;
import com.vipicu.maker.generator.utils.RegexUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

/**
 * 表生成策略 前端控制器
 *
 * @author oohmygosh
 * @since 2022-12-19
 */
@Tag(name = "表生成策略")
@RestController
@AllArgsConstructor
@RequestMapping(GeneratorAutoConfig.API_PREFIX + "/table-strategy")
public class SysGeneratorTableStrategyController extends ApiController {
    private ISysGeneratorTableStrategyService sysGeneratorTableStrategyService;

    @Operation(summary = "分页列表")
    @PostMapping("/page")
    public Page<TableStrategy> getPage(@RequestBody PageParam<TableStrategy> dto) {
        return sysGeneratorTableStrategyService.page(dto.page(), dto.getData());
    }

    @Operation(summary = "查询 id 信息")
    @GetMapping("/get")
    public TableStrategy get(@RequestParam Long id) {
        return Optional.ofNullable(sysGeneratorTableStrategyService.getOne(Wrappers.<TableStrategy>lambdaQuery().eq(TableStrategy::getTableId, id))).orElseGet(() -> {
            TableStrategy strategy = TableStrategy.getDefaultStrategy(id);
            sysGeneratorTableStrategyService.save(strategy);
            return strategy;
        });
    }

    @Operation(summary = "根据 id 修改信息")
    @PostMapping("/update")
    public boolean update(@Validated(Update.class) @RequestBody TableStrategy sysGeneratorTableStrategy) {
        if (StringUtils.hasLength(sysGeneratorTableStrategy.getPackagePath()))
            ApiAssert.fail(!RegexUtils.isPackagePath(sysGeneratorTableStrategy.getPackagePath()),"请输入正确的包名!");
        return sysGeneratorTableStrategyService.updateById(sysGeneratorTableStrategy);
    }

    @Operation(summary = "创建添加")
    @PostMapping("/create")
    public boolean create(@Validated(Create.class) @RequestBody TableStrategy sysGeneratorTableStrategy) {
        if (StringUtils.hasLength(sysGeneratorTableStrategy.getPackagePath()))
            ApiAssert.fail(!RegexUtils.isPackagePath(sysGeneratorTableStrategy.getPackagePath()),"请输入正确的包名!");
        return sysGeneratorTableStrategyService.save(sysGeneratorTableStrategy);
    }

    @Operation(summary = "根据 ids 删除")
    @PostMapping("/delete")
    public boolean delete(@NotEmpty @RequestBody List<Long> ids) {
        return sysGeneratorTableStrategyService.removeByIds(ids);
    }
}
