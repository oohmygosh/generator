package com.vipicu.maker.generator.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.config.GeneratorAutoConfig;
import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.core.api.ApiController;
import com.vipicu.maker.generator.core.api.PageParam;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.core.validation.Update;
import com.vipicu.maker.generator.entity.SysGeneratorStrategy;
import com.vipicu.maker.generator.service.ISysGeneratorStrategyService;
import com.vipicu.maker.generator.utils.RegexUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代码生成策略 前端控制器
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
@Tag(name = "代码生成策略")
@RestController
@AllArgsConstructor
@RequestMapping(GeneratorAutoConfig.API_PREFIX + "/strategy")
public class SysGeneratorStrategyController extends ApiController {
private ISysGeneratorStrategyService sysGeneratorStrategyService;

    @Operation(summary = "分页列表")
    @PostMapping("/page")
    public Page<SysGeneratorStrategy> getPage(@RequestBody PageParam<SysGeneratorStrategy> dto){
        return sysGeneratorStrategyService.page(dto.page(),dto.getData());
    }

    @Operation(summary = "查询 id 信息")
    @GetMapping("/get")
    public SysGeneratorStrategy get(@RequestParam Long id){
        return sysGeneratorStrategyService.getOne(Wrappers.<SysGeneratorStrategy>lambdaQuery().eq(SysGeneratorStrategy::getPid,id));
    }

    @Operation(summary = "根据 id 修改信息")
    @PostMapping("/update")
    public boolean update(@Validated(Update.class) @RequestBody SysGeneratorStrategy sysGeneratorStrategy){
        ApiAssert.fail(!RegexUtils.isPackagePath(sysGeneratorStrategy.getPackagePath()),"请输入正确包路径!!");
        return sysGeneratorStrategyService.updateById(sysGeneratorStrategy);
    }

    @Operation(summary = "创建添加")
    @PostMapping("/create")
    public boolean create(@Validated(Create.class) @RequestBody SysGeneratorStrategy sysGeneratorStrategy){
        ApiAssert.fail(!RegexUtils.isPackagePath(sysGeneratorStrategy.getPackagePath()),"请输入正确包路径!!");
        return sysGeneratorStrategyService.save(sysGeneratorStrategy);
    }

    @Operation(summary = "根据 ids 删除")
    @PostMapping("/delete")
    public boolean delete(@NotEmpty @RequestBody List<Long> ids){
        return sysGeneratorStrategyService.removeByIds(ids);
    }
}
