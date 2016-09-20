package io.purplejs.thymeleaf.internal;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public final class JavascriptVariableDialect
    extends AbstractDialect
    implements IExpressionObjectDialect
{
    private final MapExpressionObjectFactory expressionObjectFactory;

    public JavascriptVariableDialect()
    {
        super( "js" );

        this.expressionObjectFactory = new MapExpressionObjectFactory();
        this.expressionObjectFactory.put( "js", new JavascriptExecutor() );
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory()
    {
        return this.expressionObjectFactory;
    }
}
