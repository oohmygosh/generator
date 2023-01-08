package com.vipicu.maker.generator.controller;

import com.vipicu.maker.generator.config.GeneratorAutoConfig;
import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.core.api.ApiController;
import com.vipicu.maker.generator.engine.GlobalGeneratorConfig;
import com.vipicu.maker.generator.engine.VelocityTemplate;
import com.vipicu.maker.generator.entity.param.GeneratorParam;
import com.vipicu.maker.generator.entity.vo.PreviewVO;
import com.vipicu.maker.generator.service.ISysGeneratorTableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成控制器
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
@Tag(name = "代码生成执行器")
@RestController
@AllArgsConstructor
@RequestMapping(GeneratorAutoConfig.API_PREFIX + "/exec")
public class SysGeneratorController extends ApiController {
    private final ISysGeneratorTableService tableService;

    @Operation(summary = "生成代码")
    @PostMapping("/getZip")
    public String generator(@RequestBody GeneratorParam param) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=maker-generator.zip");
        ApiAssert.fail(param.getId() == null, "生成失败!!");
        Optional.ofNullable(param.getTables()).ifPresentOrElse(x -> ApiAssert.fail(x.isEmpty(),"生成失败!!"), () -> param.setTables(tableService.fetchAllTable(param.getId())));
        GlobalGeneratorConfig globalGeneratorConfig = new GlobalGeneratorConfig.Builder()
                .tables(param.getTables())
                .build();
        VelocityTemplate template = new VelocityTemplate(globalGeneratorConfig);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            template.batchOutput((results) -> results.forEach(result -> {
                try {
                    zipOutputStream.putNextEntry(new ZipEntry(result.path().replaceAll("\\.", "/")));
                    zipOutputStream.write(result.content().getBytes(StandardCharsets.UTF_8));
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
            ApiAssert.fail("生成失败!");
        }
        return "success";
    }

    @Operation(summary = "代码预览")
    @PostMapping("/preview")
    public PreviewVO preview(@RequestBody GeneratorParam param) {
        ApiAssert.fail(param.getTables() == null || param.getTables().size() > 1 || param.getTables().get(0).getId() == null, "缺失参数!!");
        AtomicReference<PreviewVO> previewVO = new AtomicReference<>();
        previewVO.set(new PreviewVO(new ArrayList<>()));
        new GlobalGeneratorConfig.Builder()
                .tables(param.getTables())
                .mockData(param.getTables().get(0),results -> previewVO.get().renderResults().addAll(results))
                .templateRender((results) -> previewVO.get().renderResults().addAll(0,results));
        return previewVO.get();
    }

}
