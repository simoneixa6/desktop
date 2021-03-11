package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.lib.domain.Client;

/*
 * @author Eixa6
 */
public interface DesktopListener {
    void close();
    void returnClient(Client client);
}
