package com.junyangcompany.demo.service;

import com.junyangcompany.demo.bean.response.Response;
import com.junyangcompany.demo.entity.enumeration.ScienceAndArt;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ExportRxcelService {
    void exportExcel(HttpServletResponse response, List<Response> responses, Integer scienceAndArt) throws IOException;
}
