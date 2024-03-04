package org.green.web.handler;

import javax.servlet.http.HttpServletRequest;

public abstract interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;

}
