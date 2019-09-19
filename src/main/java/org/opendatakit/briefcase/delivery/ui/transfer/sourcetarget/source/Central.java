/*
 * Copyright (C) 2019 Nafundi
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.opendatakit.briefcase.delivery.ui.transfer.sourcetarget.source;

import static org.opendatakit.briefcase.delivery.ui.reused.UI.makeClickable;
import static org.opendatakit.briefcase.delivery.ui.reused.UI.uncheckedBrowse;

import java.util.List;
import java.util.function.Consumer;
import javax.swing.JLabel;
import org.bushe.swing.event.EventBus;
import org.opendatakit.briefcase.delivery.ui.transfer.sourcetarget.CentralServerDialog;
import org.opendatakit.briefcase.operations.transfer.SourceOrTarget;
import org.opendatakit.briefcase.operations.transfer.TransferForms;
import org.opendatakit.briefcase.operations.transfer.pull.PullEvent;
import org.opendatakit.briefcase.operations.transfer.pull.central.PullFromCentral;
import org.opendatakit.briefcase.reused.BriefcaseException;
import org.opendatakit.briefcase.reused.Container;
import org.opendatakit.briefcase.reused.Workspace;
import org.opendatakit.briefcase.reused.job.JobsRunner;
import org.opendatakit.briefcase.reused.model.form.FormMetadata;
import org.opendatakit.briefcase.reused.model.transfer.CentralServer;
import org.opendatakit.briefcase.reused.model.transfer.RemoteServer.Test;

/**
 * Represents an ODK Central server as a source of forms for the Pull UI Panel.
 */
public class Central implements SourcePanelValueContainer {
  private final Workspace workspace;
  private final Container container;
  private final Test<CentralServer> serverTester;
  private final Consumer<SourcePanelValueContainer> onSourceCallback;
  private CentralServer server;

  Central(Container container, Test<CentralServer> serverTester, Consumer<SourcePanelValueContainer> onSourceCallback) {
    this.workspace = container.workspace;
    this.container = container;
    this.serverTester = serverTester;
    this.onSourceCallback = onSourceCallback;
  }

  @Override
  public List<FormMetadata> getFormList() {
    String token = container.http.execute(server.getSessionTokenRequest())
        .orElseThrow(() -> new BriefcaseException("Can't authenticate with ODK Central"));

    return container.http.execute(server.getFormsListRequest(token))
        .orElseThrow(() -> new BriefcaseException("Can't get forms list from server"));
  }

  @Override
  public JobsRunner pull(TransferForms forms, boolean startFromLast) {
    String token = container.http.execute(server.getSessionTokenRequest()).orElseThrow(() -> new BriefcaseException("Can't authenticate with ODK Central"));
    PullFromCentral pullOp = new PullFromCentral(container, server, token, EventBus::publish);
    return JobsRunner
        .launchAsync(forms.map(formMetadata -> pullOp.pull(formMetadata, workspace.buildFormFile(formMetadata))))
        .onComplete(() -> EventBus.publish(new PullEvent.PullComplete()));
  }

  @Override
  public String getDescription() {
    return server.getBaseUrl().toString();
  }

  @Override
  public void onSelect(java.awt.Container container) {
    CentralServerDialog dialog = CentralServerDialog.empty(serverTester);
    dialog.onConnect(this::set);
    dialog.getForm().setVisible(true);
  }

  @Override
  public void decorate(JLabel label) {
    label.setText("" +
        "<html>" +
        "URL: <a href=\"" + server.getBaseUrl().toString() + "\">" + getDescription() + "</a><br/>" +
        "Project ID: " + server.getProjectId() + "" +
        "</html>");
    makeClickable(label, () -> uncheckedBrowse(server.getBaseUrl()));
  }

  @Override
  public boolean canBeReloaded() {
    return true;
  }

  @Override
  public boolean accepts(Object o) {
    return o instanceof CentralServer;
  }

  @Override
  public void set(SourceOrTarget server) {
    this.server = (CentralServer) server;
    onSourceCallback.accept(this);
  }

  @Override
  public SourceOrTarget get() {
    return server;
  }

  @Override
  public String toString() {
    return "Central server";
  }

}