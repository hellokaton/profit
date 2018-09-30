package io.github.biezhi.profit.entities.model;

import io.github.biezhi.anima.Model;
import io.github.biezhi.anima.annotation.Table;
import lombok.Data;

/**
 * 配置
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Data
@Table(pk = "key")
public class Option extends Model {

    private String  key;
    private String  value;

}
