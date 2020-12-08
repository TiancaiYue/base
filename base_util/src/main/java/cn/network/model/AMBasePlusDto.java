package cn.network.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by base on 2020-01-14.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AMBasePlusDto<T> extends AMBaseDto {
    private T data;
    private T object;
    private T dataList;
    private String rights;
}
