package com.cupk.snapshot.service;

import com.cupk.snapshot.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Guo Tianyou on 2023/6/9.
 */
@FeignClient(value = "snapshot-oauth2-server")
public interface Oauth2Service {

    @GetMapping("/oauth/token_key")
    R getKey();


}
