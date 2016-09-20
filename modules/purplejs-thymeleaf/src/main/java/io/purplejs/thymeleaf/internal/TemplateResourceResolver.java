package io.purplejs.thymeleaf.internal;

import org.thymeleaf.templateresource.ITemplateResource;

import io.purplejs.core.resource.ResourcePath;

interface TemplateResourceResolver
{
    ITemplateResource resolve( ResourcePath base, String location );
}
