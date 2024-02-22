package io.glory.docs.common.response;

import static io.glory.mcore.code.SuccessCode.SUCCESS;

import jakarta.servlet.http.HttpServletResponse;

import io.glory.coreweb.WebAppConst;
import io.glory.coreweb.response.ApiResponseEntity;
import io.glory.coreweb.response.PageResObj;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiResponseEntityController {

    @GetMapping("/docs/response")
    public ResponseEntity<ApiResponseEntity<Object>> response(HttpServletResponse response) {
        response.setHeader(WebAppConst.REQUEST_ID_HEADER, "1173504442884562944");
        return ApiResponseEntity.of(SUCCESS);
    }

    @GetMapping("/docs/response/page")
    public ResponseEntity<ApiResponseEntity<PageResObj<Object>>> responsePage() {

        var pageResObj = new PageResObj<>(Page.empty());
        return ApiResponseEntity.of(SUCCESS, pageResObj);
    }

}
