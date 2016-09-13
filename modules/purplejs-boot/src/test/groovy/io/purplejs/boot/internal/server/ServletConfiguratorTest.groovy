package io.purplejs.boot.internal.server

import io.purplejs.core.RunMode
import io.purplejs.core.settings.SettingsBuilder
import spock.lang.Specification

class ServletConfiguratorTest
    extends Specification
{
    private SettingsBuilder settingsBuilder;

    def setup()
    {
        this.settingsBuilder = SettingsBuilder.newBuilder();
    }

    def "configure"()
    {
        when:
        def configurator = new ServletConfigurator();
        configurator.configure( this.settingsBuilder.build() );

        then:
        configurator.getHandler() != null;
    }

    def "configure devMode"()
    {
        setup:
        RunMode.DEV.set();

        when:
        def configurator = new ServletConfigurator();
        configurator.configure( this.settingsBuilder.build() );

        then:
        configurator.getHandler() != null;

        cleanup:
        RunMode.PROD.set();
    }
}
