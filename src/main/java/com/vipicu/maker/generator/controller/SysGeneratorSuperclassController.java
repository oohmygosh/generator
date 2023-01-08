package com.vipicu.maker.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.config.GeneratorAutoConfig;
import com.vipicu.maker.generator.core.api.ApiController;
import com.vipicu.maker.generator.core.api.PageParam;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.core.validation.Update;
import com.vipicu.maker.generator.entity.SysGeneratorSuperclass;
import com.vipicu.maker.generator.service.ISysGeneratorSuperclassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 父类 前端控制器
 *
 * @author oohmygosh
 * @since 2022-12-20
 */
@Tag(name = "父类")
@RestController
@AllArgsConstructor
@RequestMapping(GeneratorAutoConfig.API_PREFIX + "/superclass")
public class SysGeneratorSuperclassController extends ApiController {
private ISysGeneratorSuperclassService sysGeneratorSuperclassService;

    @Operation(summary = "分页列表")
    @PostMapping("/page")
    public Page<SysGeneratorSuperclass> getPage(@RequestBody PageParam<SysGeneratorSuperclass> dto){
        return sysGeneratorSuperclassService.page(dto.page(),dto.getData());
    }

    @Operation(summary = "获取所有")
    @GetMapping("/getAll")
    public List<SysGeneratorSuperclass> getAll(){
        return sysGeneratorSuperclassService.list();
    }

    @Operation(summary = "查询 id 信息")
    @GetMapping("/get")
    public SysGeneratorSuperclass get(@RequestParam Long id){
        return sysGeneratorSuperclassService.getById(id);
    }

    @Operation(summary = "根据 id 修改信息")
    @PostMapping("/update")
    public boolean update(@Validated(Update.class) @RequestBody SysGeneratorSuperclass sysGeneratorSuperclass){
        return sysGeneratorSuperclassService.updateById(sysGeneratorSuperclass);
    }

    @Operation(summary = "创建添加")
    @PostMapping("/create")
    public boolean create(@Validated(Create.class) @RequestBody SysGeneratorSuperclass sysGeneratorSuperclass){
        return sysGeneratorSuperclassService.save(sysGeneratorSuperclass);
    }

    @Operation(summary = "根据 ids 删除")
    @PostMapping("/delete")
    public boolean delete(@NotEmpty @RequestBody List<Long> ids){
        return sysGeneratorSuperclassService.removeByIds(ids);
    }
}
