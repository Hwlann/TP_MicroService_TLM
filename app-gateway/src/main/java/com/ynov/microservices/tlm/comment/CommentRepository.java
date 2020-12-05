package com.ynov.microservices.tlm.comment;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "comment-service")
public interface CommentRepository{


}
