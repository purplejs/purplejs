package io.purplejs.boot.internal

import spock.lang.Specification

class BannerPrinterTest
    extends Specification
{
    def "print banner"()
    {
        when:
        def printer = new BannerPrinter();

        then:
        printer.printBanner();
    }
}
