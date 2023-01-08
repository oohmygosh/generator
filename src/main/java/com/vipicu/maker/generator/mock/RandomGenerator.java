package com.vipicu.maker.generator.mock;

import com.apifan.common.random.entity.DataField;
import com.apifan.common.random.source.*;
import com.vipicu.maker.generator.entity.SysGeneratorField;
import com.vipicu.maker.generator.enums.MockParam;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;

/**
 * 随机数据生成
 *
 * @author oohmygosh
 * @since 2022/12/26
 */
public class RandomGenerator implements MockDataGenerator {
    @Override
    public DataField doGenerator(SysGeneratorField field) {
        try {
            return switch (MockParam.valueOf(field.getMockParam())) {
                case EMAIL -> new DataField(field.getName(), () -> InternetSource.getInstance().randomEmail(10));
                case PHONE_NUM ->
                        new DataField(field.getName(), () -> PersonInfoSource.getInstance().randomChineseMobile());
                case DOMAIN -> new DataField(field.getName(), () -> InternetSource.getInstance().randomDomain(16));
                case PRIVATE_IPV4 ->
                        new DataField(field.getName(), () -> InternetSource.getInstance().randomPrivateIpv4());
                case PUBLIC_IPV4 ->
                        new DataField(field.getName(), () -> InternetSource.getInstance().randomPublicIpv4());
                case IPV6 -> new DataField(field.getName(), () -> InternetSource.getInstance().randomIpV6());
                case CHINESE_NAME ->
                        new DataField(field.getName(), () -> PersonInfoSource.getInstance().randomChineseName());
                case ENGLISH_NAME ->
                        new DataField(field.getName(), () -> PersonInfoSource.getInstance().randomEnglishName());
                case DATE ->
                        new DataField(field.getName(), () -> DateTimeSource.getInstance().randomPastDate(LocalDate.now(), 3650, "yyyy-MM-dd"));
                case ADDRESS -> new DataField(field.getName(), () -> AreaSource.getInstance().randomAddress());
                case PASSWORD ->
                        new DataField(field.getName(), () -> PersonInfoSource.getInstance().randomStrongPassword(16, true));
                case PLATE_NUM -> new DataField(field.getName(), () -> {
                    try {
                        return OtherSource.getInstance().randomPlateNumber(SecureRandom.getInstanceStrong().nextBoolean());
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                });
                case ID_CARD ->
                        new DataField(field.getName(), () -> PersonInfoSource.getInstance().randomMaleIdCard(AreaSource.getInstance().randomProvince(), LocalDate.of(1980, 1, 11), LocalDate.now()));
            };
        }catch (Exception e){
            return null;
        }
    }
}
