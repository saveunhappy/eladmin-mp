/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.config;

import me.zhengjie.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 */
@Service(value = "el")
public class AuthorityConfig {

    public Boolean check(String ...permissions){
        // 获取当前用户的所有权限
        List<String> elPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限,如果是admin那么所有的都可以查看
        if (elPermissions.contains("admin")) return true;
        //这个就是@PreAuthorize里面的那个值，可以放数组，可以是单个的
        //如果查询到当前用户的权限和@PreAuthorize里面的对应，那么就可以访问
        //之前的开源是通过请求的path来判定的，这个相当于是通过自定义的一个字符串来匹配
        for (String permission : permissions) {
            if (elPermissions.contains(permission)) {
                return true;
            }
        }
        return false;
    }
}
