package io.purplejs.boot.internal.server

import io.purplejs.core.RunMode
import io.purplejs.core.settings.SettingsBuilder
import spock.lang.Specification

class EngineConfiguratorTest
    extends Specification
{
    private EngineConfigurator configurator;

    private SettingsBuilder settingsBuilder;

    def setup()
    {
        this.configurator = new EngineConfigurator();
        this.settingsBuilder = SettingsBuilder.newBuilder();
    }

    def "configure"()
    {
        when:
        this.configurator.configure( this.settingsBuilder.build() );

        then:
        this.configurator.engine != null;
    }

    def "configure devMode"()
    {
        setup:
        RunMode.DEV.set();

        when:
        this.settingsBuilder.put( 'devSourceDirs', new File( '.' ).absolutePath );
        this.configurator.configure( this.settingsBuilder.build() );

        then:
        this.configurator.engine != null;

        cleanup:
        RunMode.PROD.set();
    }
}
