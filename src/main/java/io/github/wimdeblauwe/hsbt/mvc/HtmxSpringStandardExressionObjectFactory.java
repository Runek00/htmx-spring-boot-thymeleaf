package io.github.wimdeblauwe.hsbt.mvc;

import jakarta.servlet.http.HttpServletRequest;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.IServletWebRequest;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class HtmxSpringStandardExressionObjectFactory implements IExpressionObjectFactory {

    /*
     * Any new objects added here should also be added to the "ALL_EXPRESSION_OBJECT_NAMES" See below.
     */
    public static final String HTMX_REQUEST_EXPRESSION_OBJECT_NAME = "htmxRequest";
    public static final Set<String> ALL_EXPRESSION_OBJECT_NAMES;

    static {
        final Set<String> allExpressionObjectNames = new LinkedHashSet<>();
        allExpressionObjectNames.add(HTMX_REQUEST_EXPRESSION_OBJECT_NAME);

        ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(allExpressionObjectNames);
    }

    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    public Object buildObject(final IExpressionContext context, final String expressionObjectName) {
        if (HTMX_REQUEST_EXPRESSION_OBJECT_NAME.equals(expressionObjectName) && context instanceof IWebContext webContext) {
            IWebExchange exchange = webContext.getExchange();
            IServletWebRequest request = (IServletWebRequest) exchange.getRequest();
            return HtmxHandlerMethodArgumentResolver.createHtmxRequest((HttpServletRequest) request.getNativeRequestObject());
        }

        return null;
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return HTMX_REQUEST_EXPRESSION_OBJECT_NAME.equals(expressionObjectName);
    }

}
